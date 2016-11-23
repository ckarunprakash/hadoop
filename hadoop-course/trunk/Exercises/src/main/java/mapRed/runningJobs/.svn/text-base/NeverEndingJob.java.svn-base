package mapRed.runningJobs;

import java.io.IOException;
import java.util.UUID;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ThreadUtil;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

public class NeverEndingJob extends Configured implements Tool {

	private final static Logger log = Logger.getLogger(NeverEndingJob.class);
	
	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), this.getClass().getSimpleName());
		job.setJarByClass(getClass());
		
		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(args[0]));
		job.setMapperClass(NeverEndingJobMapper.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setNumReduceTasks(0);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class NeverEndingJobMapper extends Mapper<LongWritable, Text, Text, Text> {
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException,
				InterruptedException {
			while (true){
				log.info("Ha ha! I will never end! Enjoy this UUID: " + UUID.randomUUID());
				ThreadUtil.sleepAtLeastIgnoreInterrupts(2000);
				
				// report "progress" so the framework doesn't kill this task
				context.progress();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new NeverEndingJob(), args);
		System.exit(exitCode);
	}
}
