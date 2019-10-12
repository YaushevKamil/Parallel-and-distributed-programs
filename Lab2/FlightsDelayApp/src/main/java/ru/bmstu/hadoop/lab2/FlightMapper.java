package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightMapper extends Mapper<LongWritable, Text, FlightWritableComparable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() > 0) {
            CSVUtils.parseFlightData(value.toString());
            String delayTime = CSVUtils.getDelayTime();
            if (CSVUtils.strToFloat(delayTime) > CSVUtils.FLOAT_ZERO) {
                String airportId = CSVUtils.getOriginAirportId();
                context.write(new FlightWritableComparable(CSVUtils.strToInt(airportId), CSVUtils.TYPE_FLIGHT),
                              new Text(delayTime));
            }
        }
    }
}