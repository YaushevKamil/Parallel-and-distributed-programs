package ru.bmstu.hadoop.spark.lab3;

import java.io.Serializable;

public class Statistic implements Serializable {
    private int flightsCount;
    private int delayedFlightsCount;
    private int cancelledFlightsCount;
    private float maxDelay;

    public Statistic() {}

    Statistic(int flightsCount, int delayedFlightsCount, int cancelledFlightsCount, float maxDelay) {
        this.flightsCount = flightsCount;
        this.delayedFlightsCount = delayedFlightsCount;
        this.cancelledFlightsCount = cancelledFlightsCount;
        this.maxDelay = maxDelay;
    }

    public int getFlightsCount() {
        return flightsCount;
    }

    public int getDelayedFlightsCount() {
        return delayedFlightsCount;
    }

    public int getCancelledFlightsCount() {
        return cancelledFlightsCount;
    }

    public float getMaxDelay() {
        return maxDelay;
    }

    public void setFlightsCount(int flightsCount) {
        this.flightsCount = flightsCount;
    }

    public void setDelayedFlightsCount(int delayedFlightsCount) {
        this.delayedFlightsCount = delayedFlightsCount;
    }

    public void setCancelledFlightsCount(int cancelledFlightsCount) {
        this.cancelledFlightsCount = cancelledFlightsCount;
    }

    public void setMaxDelay(float maxDelay) {
        this.maxDelay = maxDelay;
    }
}
