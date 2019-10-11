package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlightWrightableComparable implements WritableComparable {
    private int airportId;
    private float delayTime;
    
    public FlightWrightableComparable() {}
    
    public FlightWrightableComparable(String raw, ) {
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

    private static int boolToInt(Boolean b) {
        return Boolean.compare(b, false);
    }

    @Override
    public int compareTo(Object o) {
        FlightWrightableComparable other = (FlightWrightableComparable)o;
        if (this.airportId != other.airportId) {
            return Integer.compare(this.airportId, other.airportId);
        } else if (this.airTime != other.airTime) {
            return Float.compare(this.airTime, other.airTime);
        } else if (this.delayTime != other.delayTime) {
            return Float.compare(this.delayTime, other.delayTime);
        } else if (this.isCancelled ^ other.isCancelled) {
            return boolToInt(this.isCancelled) - boolToInt(other.isCancelled);
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