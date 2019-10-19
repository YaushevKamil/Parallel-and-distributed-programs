package ru.bmstu.hadoop.spark.lab3;

import java.io.Serializable;

import static ru.bmstu.hadoop.spark.lab3.CSVUtils.*;

public class Statistic implements Serializable {
    private int flightsCount;
    private int delayedFlightsCount;
    private int cancelledFlightsCount;
    private float maxDelay;

    public Statistic() {}

    Statistic(int flightsCount, int delayedFlightsCount, int cancelledFlightsCount, float maxDelay) {
        this.flightsCount          = flightsCount;
        this.delayedFlightsCount   = delayedFlightsCount;
        this.cancelledFlightsCount = cancelledFlightsCount;
        this.maxDelay              = maxDelay;
    }

    private int getFlightsCount() {
        return flightsCount;
    }

    private int getDelayedFlightsCount() {
        return delayedFlightsCount;
    }

    private int getCancelledFlightsCount() {
        return cancelledFlightsCount;
    }

    private float getMaxDelay() {
        return maxDelay;
    }

    static Statistic addValue(Statistic stat, boolean isDelayed, boolean isCancelled, float delayTime) {
        return new Statistic(
                stat.getFlightsCount() + 1,
                isDelayed   ? stat.getDelayedFlightsCount()   + 1 : stat.getDelayedFlightsCount(),
                isCancelled ? stat.getCancelledFlightsCount() + 1 : stat.getDelayedFlightsCount(),
                Math.max(delayTime, stat.getMaxDelay()));
    }

    static Statistic add(Statistic statA, Statistic statB) {
        return new Statistic(statA.getFlightsCount()          + statB.getFlightsCount(),
                       statA.getDelayedFlightsCount()   + statB.getDelayedFlightsCount(),
                      statA.getCancelledFlightsCount() + statB.getCancelledFlightsCount(),
                               statA.getMaxDelay()              + statB.getMaxDelay());
    }

    private static float getPercent(int value, int maxValue) {
        return (float)value / (float)maxValue * FLOAT_HUNDRED_PERCENT;
    }

    static String outputString(Statistic stat) {
        float delayPercent     = getPercent(stat.getDelayedFlightsCount(),   stat.getFlightsCount());
        float cancelledPercent = getPercent(stat.getCancelledFlightsCount(), stat.getFlightsCount());
        return "{ " +
                   "max_delay: " + String.format("%.2f", stat.getMaxDelay()) + "m, " +
                   "delayed: "   + String.format("%.2f", delayPercent)       + "%, " +
                   "cancelled: " + String.format("%.2f", cancelledPercent)   + "%"   +
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