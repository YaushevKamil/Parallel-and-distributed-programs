package ru.bmstu.hadoop.spark.lab3;

import java.io.Serializable;

public class Statistic implements Serializable {
    private int flightsCount;
    private int delayedFlightsCount;
    private int cancelledFlightsCount;
    private float maxDelay;

    
}
