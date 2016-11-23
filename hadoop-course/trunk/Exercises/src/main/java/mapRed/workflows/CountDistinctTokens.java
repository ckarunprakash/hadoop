package mapRed.workflows;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class CountDistinctTokens extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), this.getClass().getSimpleName());
		job.setJarByClass(getClass());
		
		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(args[0]));
		job.setMapperClass(CountTokensMapper.class);
		job.setCombinerClass(CountTokensReducer.class);
		job.setReducerClass(CountTokensReducer.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class CountTokensMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable countOne = new IntWritable(1);
		private final Text text = new Text();
		@Override	
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			
			StringTokenizer tokenizer = new StringTokenizer(value.toString());
			while (tokenizer.hasMoreTokens()) {
				text.set(tokenizer.nextToken());
				context.write(text, countOne);
			}
		}
	}
	
	public static class CountTokensReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		@Override
		protected void reduce(Text token, Iterable<IntWritable> counts, Context context)
				throws IOException, InterruptedException {
			int sum = 0;

			for (IntWritable count : counts) {
				sum += count.get();
			}
			context.write(token, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new CountDistinctTokens(), args);
		System.exit(exitCode);
	}
}
