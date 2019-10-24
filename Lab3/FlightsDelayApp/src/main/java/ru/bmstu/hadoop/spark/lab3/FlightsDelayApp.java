package ru.bmstu.hadoop.spark.lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

import static ru.bmstu.hadoop.spark.lab3.CSVUtils.*;

public class FlightsDelayApp {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> airportsTable = sc.textFile(AIRPORTS_CSV);
        JavaRDD<String> flightsTable = sc.textFile(FLIGHTS_CSV);

        JavaPairRDD<Integer, String> airportsData = airportsTable
                .mapToPair(str -> {
                    CSVUtils csvData = new CSVUtils(str);
                    int airportId = csvData.getAirportId();
                    String airportName = csvData.getAirportName();
                    return new Tuple2<>(airportId, airportName);
                });
        /* .filter(s -> !s.contains(AIRPORTS_FIRST_COLUMN)) */

        final Broadcast<Map<Integer, String>> airportsBroadcast = sc.broadcast(airportsData.collectAsMap());

        JavaPairRDD<Tuple2<Integer, Integer>, FlightSerializable> flightData = flightsTable
                .mapToPair(str -> {
                    CSVUtils csvData = new CSVUtils(str);
                    int originAirportId = csvData.getOriginAirportId();
                    int destAirportId = csvData.getDestAirportId();
                    float delayTime = csvData.getDelayTime();
                    boolean cancelled = csvData.getCancelled();
                    return new Tuple2<>(new Tuple2<>(
                                    originAirportId,
                                    destAirportId),
                                    new FlightSerializable(
                                            originAirportId,
                                            destAirportId,
                                            delayTime,
                                            cancelled));
                });
        /* .filter(s -> !s.contains(FLIGHTS_FIRST_COLUMN)) */

        JavaPairRDD<Tuple2<Integer, Integer>, String> flightDataStat = flightData
                .combineByKey(
                        p -> new Statistic(
                                1,
                                p.getDelayTime() > FLOAT_ZERO ? INT_COUNT_ONE : INT_COUNT_ZERO,
                                p.getCancelled() ? INT_COUNT_ONE : INT_COUNT_ZERO,
                                p.getDelayTime()),
                        (count, p) -> Statistic.addValue(
                                count,
                                p.getDelayTime() > FLOAT_ZERO,
                                p.getCancelled(),
                                p.getDelayTime()),
                        Statistic::add)
                .mapToPair(s -> new Tuple2<>(s._1(), Statistic.outputString(s._2())));

        JavaRDD<String> result = flightDataStat
                .map(k -> {
                    Map<Integer, String> airportID = airportsBroadcast.value();
                    Tuple2<Integer, Integer> key = k._1();
                    String value = k._2();
                    String originAirportID = airportID.get(key._1());
                    String destAirportID = airportID.get(key._2());
                    return "{ " +
                            originAirportID + " -> " + destAirportID + "\t" + value +
                            "} ";
                });

        result.saveAsTextFile(OUTPUT_FILE);
    }
}