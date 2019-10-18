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
        SparkConf conf = new SparkConf().setAppName("lab5");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flightsTable  = sc.textFile(FLIGHTS_CSV);
        JavaRDD<String> airportsTable = sc.textFile(AIRPORTS_CSV);

        JavaPairRDD<Integer, String> airportsData = airportsTable
                .filter(s -> !s.contains(AIRPORTS_FIRST_COLUMN))
                .mapToPair(s -> {
                    parseAirportData(s);
                    int airportID = getAirportId();
                    String airportName = getAirportName();
                    return new Tuple2<>(airportID, airportName);
                });

        final Broadcast<Map<Integer, String>> airportsBroadcasted = sc.broadcast(airportsData.collectAsMap());

        JavaPairRDD<Tuple2<Integer, Integer>, FlightSerializable> flightData = flightsTable
                .filter(s -> !s.contains(FLIGHTS_FIRST_COLUMN))
                .mapToPair(s -> {
                    parseFlightData(s);
                    return new Tuple2<>(new Tuple2<>(getOriginAirportId(), getDestAirportId()),
                            new FlightSerializable(getOriginAirportId(), getDestAirportId(), getFloatDelayTime(), getCancelled()));
                });

        JavaPairRDD<Tuple2<Integer, Integer>, String> flightDataStat = flightData
                .combineByKey(
                        p -> new Statistic(1,
                                    p.getDelayTime() > FLOAT_ZERO ? 1 : 0,
                                p.getCancelled() ? 1 : 0,
                                p.getDelayTime()),
                        (count, p) -> Statistic.addValue(count,
                                p -> p.getDelayTime() > FLOAT_ZERO,
                                p -> p.getCancelled(),
                                p -> p.getDelayTime()),
                        Statistic::add).mapToPair(map -> new Tuple2<>(map._1(), Statistic.resString(map._2()))
                );

        JavaRDD<String> result = flightDataStat
                .map(k -> {
                    Map<Integer, String> airportID = airportsBroadcasted.value();
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
