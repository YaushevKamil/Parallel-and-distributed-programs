package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.WritableComparator;

public class FlightsComparator extends WritableComparator {
    public FlightsComparator() {
        super(FlightWritableComparable);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {

    }
}
