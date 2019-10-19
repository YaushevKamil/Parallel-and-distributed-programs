package ru.bmstu.hadoop.lab2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

import static ru.bmstu.hadoop.lab2.CSVUtils.*;

public class FlightsReducer extends Reducer<FlightWritableComparable, Text, Text, Text> {
    @Override
    public void reduce(FlightWritableComparable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Iterator<Text> iter = values.iterator();
        if (iter.hasNext()) {
            String airportName = "Airport: " + iter.next().toString();
            if (iter.hasNext()) {
                int count = INT_ZERO;
                float min = FLOAT_ZERO;
                float max = FLOAT_ZERO;
                float sum = FLOAT_ZERO;
                while (iter.hasNext()) {
                    float curr = strToFloat(iter.next().toString());
                    if (count == 0) min = curr;
                    if (curr < min) min = curr;
                    else if (curr > max) max = curr;
                    sum += curr;
                    count++;
                }
                String average = String.format("%.2f", sum / count);
                String delayStat = "Delay time: { " +
                                       "Min: "     + min     + ", " +
                                       "Average: " + average + ", " +
                                       "Max: "     + max +
                                    " }";
                context.write(new Text(airportName), new Text(delayStat));
            }
        }
    }
}