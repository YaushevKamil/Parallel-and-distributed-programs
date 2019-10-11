package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportMapper extends Mapper<LongWritable, Text, FlightWrightableComparable, Text> {
    private static final int TYPE_AIRPORT = 0;
    private static final int AIRPORT_ID = 0;
    private static final int AIRPORT_NAME = 1;

    private static final int INT_ZERO = 0;

    private static int strToInt(String numString) {
        int number = INT_ZERO;
        try {
            number = Integer.parseInt(numString);
        } catch (Exception ignored) {}

        return number;
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() > 0) {
            String[] data = value.toString().replaceAll("\"", "").split(",");

            String airportId = data[AIRPORT_ID];
            String airportName = data[AIRPORT_NAME];

            context.write(new FlightWrightableComparable(strToInt(airportId), TYPE_AIRPORT), new Text(airportName));
        }
    }
}
