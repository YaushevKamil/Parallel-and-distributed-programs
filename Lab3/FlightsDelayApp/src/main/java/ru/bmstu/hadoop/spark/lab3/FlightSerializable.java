package ru.bmstu.hadoop.spark.lab3;

import java.io.Serializable;

public class FlightSerializable implements Serializable {
    private int originAirportID;
    private int destAirportID;
    private float delayTime;
    private boolean isCancelled;
    
    public FlightSerializable(int originAirportID, int destAirportID, float delayTime, boolean isCancelled) {
    }
}
