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
    SparkConf conf = new SparkConf().setAppName("lab5");
    JavaSparkContext sc = new JavaSparkContext(conf);

    JavaRDD<String> flightsTable = sc.textFile("664600583_T_ONTIME_sample.csv");
    JavaRDD<String> airportsTable = sc.textFile("L_AIRPORT_ID.csv");

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
                int originAirportID = getOriginAirportId();
                int destAirportID   = getDestAirportId();
                float delayTime     = getFloatDelayTime();
                boolean isCancelled = getCancelled();
                return new Tuple2<>(new Tuple2<>(originAirportID, destAirportID),
                        new FlightSerializable(originAirportID, destAirportID, delayTime, isCancelled));
            });

    JavaPairRDD<Tuple2<Integer, Integer>, String> flightDataStat = flightData
            .combineByKey(
                    p -> new Statistic(1,
                                       p.getDelayTime() > FLOAT_ZERO ? 1 : 0,
                                       p.getCancelled() ? 1 : 0,
                                       p.getDelayTime()),
                    (count, p) -> Statistic.addValue(count,
                                                     p -> p.getDelayTime(),
                                                     p -> p.getCancelled(),
                                                     p -> p.getDelayTime()),
                    Statistic::add).mapToPair(map -> new Tuple2<>(map._1(), Statistic.resString(map._2()))
            );

    JavaRDD<String> result = getap
}
