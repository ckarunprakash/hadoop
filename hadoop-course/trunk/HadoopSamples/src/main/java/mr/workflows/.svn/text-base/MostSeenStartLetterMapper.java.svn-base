package mr.workflows;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MostSeenStartLetterMapper extends Mapper<Text, Text, Text, IntWritable> {

	private int currentMaxCount = -1;
	private Text currentValue = new Text("");
	@Override
	protected void map(Text letter, Text countAsText, Context context)
			throws IOException, InterruptedException {
		int count = Integer.parseInt(countAsText.toString());
		if (count > currentMaxCount){
			currentValue.set(letter);
			currentMaxCount = count;
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
