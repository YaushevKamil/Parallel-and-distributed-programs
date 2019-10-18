package ru.bmstu.hadoop.spark.lab3;

import java.io.Serializable;

public class Statistic implements Serializable {
    private static final float HUNDRED_PERCENT = 100.0f;
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

    static Statistic add(Statistic statA, Statistic statB) {
        return new Statistic(statA.getFlightsCount()          + statB.getFlightsCount(),
                       statA.getDelayedFlightsCount()   + statB.getDelayedFlightsCount(),
                      statA.getCancelledFlightsCount() + statB.getCancelledFlightsCount(),
                              statA.getMaxDelay()               + statB.getMaxDelay());
    }

    static float getPercent(int value, int maxValue) {
        return (float)value / (float)maxValue * HUNDRED_PERCENT;
    }

    static String resString(Statistic stat) {
        float delayPercent = (float)stat.getDelayedFlightsCount() / (float)stat.getFlightsCount() * 100.f;
        float cancelledPercent = (float)stat.getCancelledFlightsCount() / (float)stat.getFlightsCount() * 100.f;
        return "{ " +
                   "Max delay time: "           + stat.getMaxDelay() + ", " +
                   "Delayed flights percent: "  + delayPercent       + ", " +
                   "Cancelled flights percent:" + cancelledPercent   +
               " }";
    }

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
