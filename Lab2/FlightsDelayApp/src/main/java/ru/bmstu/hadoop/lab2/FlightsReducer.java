package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FlightsReducer extends Reducer<FlightWritableComparable, Text, Text, Text> {

    public void reduce() {

    }
}
