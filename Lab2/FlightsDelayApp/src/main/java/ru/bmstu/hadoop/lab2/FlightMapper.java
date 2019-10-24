package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

import static ru.bmstu.hadoop.lab2.CSVUtils.*;

public class FlightMapper extends Mapper<LongWritable, Text, FlightWritableComparable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() == 0) {
            return;
        }
        CSVUtils 
        parseFlightData(value.toString());
        float floatDelayTime   = getFloatDelayTime();
        String stringDelayTime = getStringDelayTime();
        if (floatDelayTime > FLOAT_ZERO) {
            int airportId = getOriginAirportId();
            context.write(new FlightWritableComparable(airportId, TYPE_FLIGHT),
                    new Text(stringDelayTime));
        }
    }
}