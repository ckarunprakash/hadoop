package mapRed.firstJob;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LengthDividerCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable countOne = new IntWritable(1);
	private final Text fiveCharsOrMore = new Text("greaterOrEqualsToFiveChars");
	private final Text lessThanFiveChars = new Text("lessThanFiveChars");

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException,
			InterruptedException {

		StringTokenizer tokenizer = new StringTokenizer(value.toString());
		while (tokenizer.hasMoreTokens()) {
			if (tokenizer.nextToken().length() >= 5){
				context.write(fiveCharsOrMore, countOne);
			} else {
				context.write(lessThanFiveChars, countOne);
			}
		}
	}
}