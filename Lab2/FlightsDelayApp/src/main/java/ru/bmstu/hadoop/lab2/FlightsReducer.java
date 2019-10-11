package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class FlightsReducer extends Reducer<FlightWritableComparable, Text, Text, Text> {

    private static final int INT_ZERO = 0;
    private static final float FLOAT_ZERO = 0.0f;

    private static float strToFloat(String numString) {
        float number = FLOAT_ZERO;
        try {
            number = Float.parseFloat(numString);
        } catch (Exception ignored) {}

        return number;
    }

    @Override
    public void reduce(FlightWritableComparable key, Iterable<Text> values, Context context) throws IOException {
        Iterator<Text> iter = values.iterator();
        if (iter.hasNext()) {
            String airportName = "Airport: " + iter.next().toString();
            if (iter.hasNext()) {
                int count = INT_ZERO;
                float min = FLOAT_ZERO;
                float max = FLOAT_ZERO;
                float sum = FLOAT_ZERO;

                for (; iter.hasNext(); count++) {
                    float curr = strToFloat(iter.next().toString());

                    if (count == 0) min = curr;

                    if (curr < min) {
                        min = curr;
                    } else if (curr > max) {
                        max = curr;
                    }
                    sum += curr;
                }
                sum /= count;
                String delayStat
            }
        }

    }
}
