package ru.bmstu.hadoop.spark.lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class FlightsDelayApp {
    SparkConf conf = new SparkConf().setAppName("lab5");
    JavaSparkContext sc = new JavaSparkContext(conf);

    JavaRDD<String> flightsTable = sc.textFile("664600583_T_ONTIME_sample.csv");
    JavaRDD<String> airportsTable = sc.textFile("L_AIRPORT_ID.csv");

    JavaPairRDD<Tuple2<Integer, String>, FlightSerializable> flightData = flightsTable
            .filter(s -> !s.contains("YEAR"))
            .mapToPair(s -> {
                CSVUtils.parseFlightData(s);
                int originAirportID = CSVUtils.getOriginAirportId();
                int destAirportID   = CSVUtils.getDestAirportId();
                float delayTime     = CSVUtils.getFloatDelayTime();
                float isCancelled   = CSVUtils.getCancelled();
                return new Tuple2<>(new Tuple2<>(originAirportID,destAirportID),
                        new FlightSerializable(originAirportID,destAirportID,delayTime,isCancelled));
            });

    final Broadcast<Map<String, AirportData>> airportsBroadcasted = sc.broadcast(stringAirportDataMap);

    JavaRDD<String> result = CSVUtils.getap
}
