package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportMapper extends Mapper<LongWritable, Text, FlightWritableComparable, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() > 0) {
            String[] data = CSVUtils.parseFlightData(value.toString());
            String airportId = data[CSVUtils.AIRPORT_ID_COLUMN];
            String airportName = data[CSVUtils.AIRPORT_NAME_COLUMN];
            context.write(new FlightWritableComparable(CSVUtils.strToInt(airportId), CSVUtils.TYPE_AIRPORT),
                          new Text(airportName));
        }
    }
}