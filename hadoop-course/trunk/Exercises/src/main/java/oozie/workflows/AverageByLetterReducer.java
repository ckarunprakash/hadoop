package oozie.workflows;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AverageByLetterReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	@Override
	protected void reduce(Text token, Iterable<IntWritable> counts, Context context)
			throws IOException, InterruptedException {
		int sum = 0;
		int numOfItems = 0;

		for (IntWritable count : counts) {
			sum += count.get();
			numOfItems++;
		}
		double avg = (double)sum/numOfItems;
		context.write(token, new IntWritable((int)avg));
	}
}