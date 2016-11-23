package mr.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

public class StartsWithCountReducer extends
		Reducer<Text, IntWritable, Text, IntWritable> {
	Logger log = Logger.getLogger(StartsWithCountMapper.class);
	@Override
	protected void reduce(Text token, Iterable<IntWritable> counts,
			Context context) throws IOException, InterruptedException {
		int sum = 0;
		
		for (IntWritable count : counts) {
			sum+= count.get();
		}
		context.write(token, new IntWritable(sum));
	}
}
