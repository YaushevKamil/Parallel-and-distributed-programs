package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlightsPartitioner extends Partitioner<FlightWritableComparable> {
    public int getPartition(JoinWritableComparable key, Text value, int numReduceTasks) {

    }
}
