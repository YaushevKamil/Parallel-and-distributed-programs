package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlightWritableComparable implements WritableComparable {
    private int airportId;
    private int dataType;

    FlightWritableComparable(int airportId, int dataType) {
        this.airportId = airportId;
        this.dataType = dataType;
    }


//    public void setAirportId(int airportId) {
//        this.airportId = airportId;
//    }

//    public void setDataType(int dataType) {
//        this.dataType = dataType;
//    }

    int getAirportId() {
        return this.airportId;
    }

//    public int getDataType() {
//        return this.dataType;
//    }


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
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        } else if (this == o) {
            return true;
        }
        FlightWritableComparable other = (FlightWritableComparable)o;

        /*return (this == o) ||
               ((o != null) && (this.getClass() == o.getClass()) &&
                (this.airportId == other.airportId) && (this.dataType == other.dataType));*/

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
                "dataType:" + dataType +
                "}";
    }
}