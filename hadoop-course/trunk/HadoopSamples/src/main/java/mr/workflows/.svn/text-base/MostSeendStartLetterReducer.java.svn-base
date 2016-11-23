package mr.workflows;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class MostSeendStartLetterReducer extends
		Reducer<Text, IntWritable, Text, IntWritable> {
	private int currentMaxCount = -1;
	private Text currentValue = new Text("");

	@Override
	protected void reduce(Text token, Iterable<IntWritable> counts,
			Context context) throws IOException, InterruptedException {

		Iterator<IntWritable> it = counts.iterator();
		IntWritable count = it.next();
		if (count.get() > currentMaxCount) {
			currentValue.set(token);
			currentMaxCount = count.get();
		}
		if (it.hasNext()) {
			throw new IllegalArgumentException(
					"There shouldn't ever be more than one value per key. key=["
							+ token + "]");
		}
	}

	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		if (currentMaxCount>0){
			context.write(currentValue, new IntWritable(currentMaxCount));
		}
	}
	
}
