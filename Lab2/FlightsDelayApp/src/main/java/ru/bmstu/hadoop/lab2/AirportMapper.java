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
            String[] data = value.toString().split(",");
            float delayTime = !(data[DELAY_TIME].equals("")) ? Float.parseFloat(data[DELAY_TIME]) : 0.0f;
            if (delayTime > 0.0f) {
                int airportId = Integer.parseInt(data[AIRPORT_ID]);
                context.write(new FlightWrightableComparable(airportId, 0), new Text(data[DELAY_TIME]));
            }
        }
    }
}
