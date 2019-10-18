package ru.bmstu.hadoop.spark.lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class FlightsDelayApp {
    SparkConf conf = new SparkConf().setAppName("lab5");
    JavaSparkContext sc = new JavaSparkContext(conf);

    JavaRDD<String> flightsTable = sc.textFile("664600583_T_ONTIME_sample.csv");
    JavaRDD<String> airportsTable = sc.textFile("L_AIRPORT_ID.csv");

    JavaPairRDD<Integer, String> flightData = 
}
