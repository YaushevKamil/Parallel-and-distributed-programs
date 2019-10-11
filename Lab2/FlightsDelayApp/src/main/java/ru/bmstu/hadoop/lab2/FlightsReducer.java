package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlightsReducer extends Reducer<FlightWritableComparable, Text, Text, Text> {

    public void reduce(FlightWritableComparable key, Iterable<Text> values, Context context) throws IOException {
        values.iterator();
    }
}
