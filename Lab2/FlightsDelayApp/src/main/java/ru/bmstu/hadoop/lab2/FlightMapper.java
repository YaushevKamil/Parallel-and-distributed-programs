package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

import static ru.bmstu.hadoop.lab2.CSVUtils.*;

public class FlightMapper extends Mapper<LongWritable, Text, FlightWritableComparable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() > 0){
            CSVUtils csvData = new CSVUtils(value.toString());
            int airportId = csvData.getOriginAirportId();
            float floatDelayTime = csvData.getFloatDelayTime();
            String stringDelayTime = csvData.getStringDelayTime();
            if (floatDelayTime > FLOAT_ZERO) {
                context.write(new FlightWritableComparable(airportId, TYPE_FLIGHT), new Text(stringDelayTime));
            }
        }
    }
}