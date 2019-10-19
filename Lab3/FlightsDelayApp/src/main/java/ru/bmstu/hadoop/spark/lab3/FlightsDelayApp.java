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
        JavaRDD<String> flightsTable  = sc.textFile(FLIGHTS_CSV);

        JavaPairRDD<Integer, String> airportsData = airportsTable
                .filter(s -> !s.contains(AIRPORTS_FIRST_COLUMN))
                .mapToPair(s -> new Tuple2<>(getAirportId(s), getAirportName(s)));

        final Broadcast<Map<Integer, String>> airportsBroadcast = sc.broadcast(airportsData.collectAsMap());

        JavaPairRDD<Tuple2<Integer, Integer>, FlightSerializable> flightData = flightsTable
                .mapToPair(s ->
                        new Tuple2<>(new Tuple2<>(
                                getOriginAirportId(s),
                                getDestAirportId(s)),
                            new FlightSerializable(
                                    getOriginAirportId(s),
                                    getDestAirportId(s),
                                    getFloatDelayTime(s),
                                    getCancelled(s))));

        /* .filter(s -> !s.contains(FLIGHTS_FIRST_COLUMN)) */

        JavaPairRDD<Tuple2<Integer, Integer>, String> flightDataStat = flightData
                .combineByKey(
                        p -> new Statistic(
                                1,
                                p.getDelayTime() > FLOAT_ZERO ? 1 : 0,
                                p.getCancelled() ? 1 : 0,
                                p.getDelayTime()),
                        (count, p) -> Statistic.addValue(
                                count,
                                p.getDelayTime() > FLOAT_ZERO,
                                p.getCancelled(),
                                p.getDelayTime()),
                        Statistic::add)
                .mapToPair(s -> new Tuple2<>(s._1(), Statistic.outputString(s._2())));

        JavaRDD<String> result = flightDataStat
                .map(k -> "{ " +
                                airportsBroadcast.value().get(k._1()._1()) + " -> " +
                                airportsBroadcast.value().get(k._1()._2()) + ":\t"  +
                                k._2() +
                           " }");
        /*{
                    Map<Integer, String> airportID = airportsBroadcast.value();
                    Tuple2<Integer, Integer> key = k._1();
                    String value = k._2();
                    String originAirportID = airportID.get(key._1());
                    String destAirportID = airportID.get(key._2());
                    return "{ " +
                            originAirportID + " -> " + destAirportID + "\t" + value +
                            "} ";
                }*/

        result.saveAsTextFile(OUTPUT_FILE);
    }
}