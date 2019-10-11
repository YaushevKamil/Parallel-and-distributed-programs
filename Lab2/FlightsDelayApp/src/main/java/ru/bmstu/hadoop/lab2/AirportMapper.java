package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportMapper extends Mapper<LongWritable, Text, FlightWrightableComparable, Text> {
    private static final int AIRPORT_ID = 0;
    private static final int AIRPORT_NAME = 1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() > 0) {
            String[] data = value.toString().replaceAll("\"", "").split(",");
            int airportId = Integer.parseInt(data[AIRPORT_ID]);
            String airportName = data[AIRPORT_NAME];
            context.write(new FlightWrightableComparable(airportId, 0), new Text(airportName));
        }
    }
}
