package ru.bmstu.hadoop.spark.lab3;

import java.io.Serializable;

public class FlightSerializable implements Serializable {
    private int originAirportID;
    private int destAirportID;
    private float delayTime;
    private boolean isCancelled;

    public FlightSerializable() {}

    FlightSerializable(int originAirportID, int destAirportID, float delayTime, boolean isCancelled) {
        this.originAirportID = originAirportID;
        this.destAirportID = destAirportID;
        this.delayTime = delayTime;
        this.isCancelled = isCancelled;
    }

    public int getOriginAirportID() {
        return originAirportID;
    }

    public int getDestAirportID() {
        return destAirportID;
    }

    public float getDelayTime() {
        return delayTime;
    }

    public  boolean getCancelled() {
        return isCancelled;
    }

    void setOriginAirportID(int originAirportID) {
        this.originAirportID = originAirportID;
    }

    void setDestAirportID(int destAirportID) {
        this.destAirportID = destAirportID;
    }

    void setDelayTime(float delayTime) {
        this.delayTime = delayTime;
    }

    void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public String toString() {
        return "FlightSerializable" + "{" +
                    "originAirportID:" + originAirportID + "," +
                    "destAirportID:"   + destAirportID   + "," +
                    "delayTime:"       + delayTime       + "," +
                    "isCancelled:"     + isCancelled     +
               "}";
    }
}