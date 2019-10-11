package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlightWrightableComparable implements WritableComparable {
    private static final int AIRPORT_ID = 14;
    private static final int DELAY_TIME = 18;
    private static final int CANCELLED  = 19;
    private static final int AIR_TIME   = 21;
    
    private int airportId;
    private float airTime;
    private float delayTime;
    private boolean isCancelled;
    
    public FlightWrightableComparable() {}
    
    public FlightWrightableComparable(String raw) {
        String[] data = raw.split(",");
        
        airportId = Integer.parseInt(data[AIRPORT_ID]);
        airTime = !(data[AIR_TIME].equals("")) ? Float.parseFloat(data[AIR_TIME]) : 0.0f;
        delayTime = !(data[DELAY_TIME].equals("")) ? Float.parseFloat(data[DELAY_TIME]) : 0.0f;
        isCancelled = Integer.parseInt(data[CANCELLED]) == 1;
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(airportId);
        out.writeFloat(airTime);
        out.writeFloat(delayTime);
        out.writeBoolean(isCancelled);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        airportId = in.readInt();
        airTime = in.readFloat();
        delayTime = in.readFloat();
        isCancelled = in.readBoolean();
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

    @Override
    public String toString() {
        return "{" +
                "airportId:" + airportId +
                ",airTime:" + airTime +
                ",delayTime:" + delayTime +
                ",isCancelled:" + isCancelled + ",";
    }
}