package mapRed.runningJobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import common.PropPrinter;

public class ExpectClassOnTask extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), this.getClass().getSimpleName());
		job.setJarByClass(getClass());
		
		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(args[0]));
		job.setMapperClass(ExpectClassOnTaskMapper.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setNumReduceTasks(0);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class ExpectClassOnTaskMapper extends Mapper<LongWritable, Text, Text, Text> {
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException,
				InterruptedException {
			PropPrinter classFromSampleJar = new PropPrinter();
			System.out.println("Class [" + PropPrinter.class + "] was on CLASSPATH");
		}
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ExpectClassOnTask(), args);
		System.exit(exitCode);
	}
}
