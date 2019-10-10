package ru.bmstu.hadoop.lab1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordReducer extends Reducer<Text, IntWritable, Text, LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        long wordCount = 0;
        for (IntWritable value : values) {
            wordCount += value.get();
        }
        
        context.write(key, new LongWritable(wordCount));
    }
}
