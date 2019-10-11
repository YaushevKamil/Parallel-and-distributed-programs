package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlightWrightableComparable implements WritableComparable {
    private int airportId;
    private int dataType;
    
    public FlightWrightableComparable() {}
    
    public FlightWrightableComparable(int airportId, int dataType) {
        this.airportId = airportId;
        this.dataType = dataType;
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

    private static int boolToInt(Boolean b) {
        return Boolean.compare(b, false);
    }

    @Override
    public int compareTo(Object o) {
        FlightWrightableComparable other = (FlightWrightableComparable)o;
        if (this.airportId != other.airportId) {
            return Integer.compare(this.airportId, other.airportId);
        } else if (this.dataType != other.dataType) {
            return Float.compare(this.dataType, other.dataType);
        } else return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + counter;
        result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                    "airportId:" + airportId + "," +
                    "airTime:" + airTime + "," +
                    "delayTime:" + delayTime + "," +
                    "isCancelled:" + isCancelled +
                "}";
    }
}