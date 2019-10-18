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

    int getFlightsCount() {
        return flightsCount;
    }

    int getDelayedFlightsCount() {
        return delayedFlightsCount;
    }

    int getCancelledFlightsCount() {
        return cancelledFlightsCount;
    }

    float getMaxDelay() {
        return maxDelay;
    }

    void setFlightsCount(int flightsCount) {
        this.flightsCount = flightsCount;
    }

    void setDelayedFlightsCount(int delayedFlightsCount) {
        this.delayedFlightsCount = delayedFlightsCount;
    }

    void setCancelledFlightsCount(int cancelledFlightsCount) {
        this.cancelledFlightsCount = cancelledFlightsCount;
    }

    void setMaxDelay(float maxDelay) {
        this.maxDelay = maxDelay;
    }

    static Statistic addValue(Statistic stat, boolean isDelayed, boolean isCancelled, float delayTime) {
        return new Statistic(stat.getFlightsCount() + 1,
                             isDelayed ? stat.getDelayedFlightsCount() + 1 : stat.getDelayedFlightsCount(),
                             isCancelled ? stat.getCancelledFlightsCount() + 1 : stat.getDelayedFlightsCount(),
                             delayTime > stat.getMaxDelay() ? delayTime : stat.getMaxDelay());
    }

    static String out

    @Override
    public String toString() {
        return "Statistics" + "{" +
                   "flightsCount:"          + flightsCount          + "," +
                   "delayedFlightsCount:"   + delayedFlightsCount   + "," +
                   "cancelledFlightsCount:" + cancelledFlightsCount + "," +
                   "maxDelay:"              + maxDelay              +
               "}";
    }
}
