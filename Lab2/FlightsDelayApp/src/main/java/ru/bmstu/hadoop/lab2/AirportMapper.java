package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

import static ru.bmstu.hadoop.lab2.CSVUtils.*;

public class AirportMapper extends Mapper<LongWritable, Text, FlightWritableComparable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() > 0) {
            parseAirportData(value.toString());
            int airportId = getAirportId();
            String airportName = getAirportName();
            context.write(new FlightWritableComparable(airportId, TYPE_AIRPORT),
                          new Text(airportName));
        }
    }
}