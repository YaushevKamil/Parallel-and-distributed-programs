package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlightWrightableComparable implements WritableComparable {
    private static final int AIRPORT_ID = 14;
    private static final int DELAY_TIME = 18;
    private static final int CANCELLED  = 19;
    private static final int AIR_TIME   = 18;
    
    private int airportId;
    private float airTime;
    private float delayTime;
    private boolean isCancelled;
    
    public FlightWrightableComparable() {}
    
    public FlightWrightableComparable(String raw) {
        String[] data = raw.split(",");
        
        airportId = Integer.parseInt(data[AIRPORT_ID]);
        airTime = !(raw.equals(NULL_STRING)) ? Float.parseFloat(column) : 0.0f;
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(counter);
        out.writeLong(timestamp);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        counter = in.readInt();
        timestamp = in.readLong();
    }

    @Override
    public int compareTo(Object o) {
        int thisValue = this.value;
        int thatValue = o.value;
        return (thisValue < thatValue ? -1 : (thisValue==thatValue ? 0 : 1));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + counter;
        result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
