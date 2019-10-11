package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlightsPartitioner extends Partitioner<FlightWritableComparable, Text> {
    @Override
    public int getPartition(FlightWritableComparable key, Text text, int numReduceTasks) {
        // !!! implement getter
        return key.airportId % numReduceTasks;
    }
}
