package mapRed.jobOnYARN;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ThreadUtil;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

public class JobWithFailures extends Configured implements Tool {

	private final static Logger log = Logger.getLogger(JobWithFailures.class);
	
	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), this.getClass().getSimpleName());
		job.setJarByClass(getClass());
		
		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(args[0]));
		job.setMapperClass(NeverEndingJobMapper.class);
		job.setInputFormatClass(NLineInputFormat.class);
		NLineInputFormat.setNumLinesPerSplit(job, 2000);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setNumReduceTasks(0);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class NeverEndingJobMapper extends Mapper<LongWritable, Text, Text, Text> {
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException,
				InterruptedException {
			// run for 5 minutes, fail 25% of the time
			long start = System.currentTimeMillis();
			while ((System.currentTimeMillis()-start) < 1000*60*5){
				ThreadUtil.sleepAtLeastIgnoreInterrupts(25000);
				log.info("Running for [" + (System.currentTimeMillis()-start)/1000 + "] seconds");
				
				if ( Math.random()*100<25){
					log.info("Throwing exception.");
					context.getCounter("Failure Stats", "Failed Tasks").increment(1);
					throw new IllegalStateException("Failing... Let's see how framework handles this one!");
				}
				
				// report "progress" so the framework doesn't kill this task
				context.progress();
			}
			
			context.getCounter("Failure Stats", "Success Tasks").increment(1);
		}
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new JobWithFailures(), args);
		System.exit(exitCode);
	}
}
