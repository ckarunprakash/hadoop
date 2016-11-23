package mapRed.workflows;

import java.io.IOException;

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

public class AverageByLetter extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), this.getClass().getSimpleName());
		job.setJarByClass(getClass());
		
		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(args[0]));
		job.setMapperClass(AverageByLetterMapper.class);
		job.setCombinerClass(AverageByLetterReducer.class);
		job.setReducerClass(AverageByLetterReducer.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class AverageByLetterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable intW = new IntWritable();
		private final Text text = new Text();
		@Override	
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			
			String [] split = value.toString().split("\t");
			String letter = split[0].substring(0,1);
			text.set(letter);
			int count = Integer.parseInt(split[1]);
			intW.set(count);
			context.write(text, intW);
		}
	}
	
	public static class AverageByLetterReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
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

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new AverageByLetter(), args);
		System.exit(exitCode);
	}
}
