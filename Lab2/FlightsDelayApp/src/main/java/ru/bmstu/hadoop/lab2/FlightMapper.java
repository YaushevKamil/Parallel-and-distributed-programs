package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightMapper extends Mapper<LongWritable, Text, FlightWrightableComparable, Text> {
    private static final int TYPE_FLIGHT = 1;
    private static final int AIRPORT_ID = 14;
    private static final int DELAY_TIME = 18;

    private static int strToInt(String numString) {
        int number = 0;
        try {
            number = Integer.parseInt(numString);
        } catch (Exception ignored) {}

        return number;
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() > 0) {
            String[] data = value.toString().split(",");
            float delayTime = !(data[DELAY_TIME].equals("")) ? Float.parseFloat(data[DELAY_TIME]) : 0.0f;
            if (delayTime > 0.0f) {
                int airportId = Integer.parseInt(data[AIRPORT_ID]);
                context.write(new FlightWrightableComparable(airportId, TYPE_FLIGHT), new Text(data[DELAY_TIME]));
            }
        }
    }
}
