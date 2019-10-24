package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlightWritableComparable implements WritableComparable {
    private int airportId;
    private int dataType;

    FlightWritableComparable() {}

    FlightWritableComparable(int airportId, int dataType) {
        this.airportId = airportId;
        this.dataType = dataType;
    }

    int getAirportId() {
        return this.airportId;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(airportId);
        out.writeInt(dataType);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        airportId = in.readInt();
        dataType = in.readInt();
    }

    @Override
    public int compareTo(Object o) {
        FlightWritableComparable other = (FlightWritableComparable)o;
        if (this.airportId != other.airportId) {
            return Integer.compare(this.airportId, other.airportId);
        } else if (this.dataType != other.dataType) {
            return Float.compare(this.dataType, other.dataType);
        } else return 0;
    }

    int compareToAirportId(Object o) {
        FlightWritableComparable other = (FlightWritableComparable)o;
        return Integer.compare(this.airportId, other.airportId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightWritableComparable)) {
            return false;
        }
        FlightWritableComparable other = (FlightWritableComparable)o;
        return (this.airportId == other.airportId) && (this.dataType == other.dataType);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                   "airportId:" + airportId + "," +
                   "dataType:"  + dataType  +
               "}";
    }
}