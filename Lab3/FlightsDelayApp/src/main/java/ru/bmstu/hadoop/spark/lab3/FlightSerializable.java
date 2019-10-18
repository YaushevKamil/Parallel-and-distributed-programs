package ru.bmstu.hadoop.spark.lab3;

import java.io.Serializable;

public class FlightSerializable implements Serializable {
    private int originAirportID;
    private int destAirportID;
    private ;
    public FlightSerializable(int originAirportID, int destAirportID, float delayTime, boolean isCancelled) {
    }
}
