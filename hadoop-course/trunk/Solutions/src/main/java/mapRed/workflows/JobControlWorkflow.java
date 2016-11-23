package mapRed.workflows;

import java.io.IOException;
import java.util.Arrays;

import mapRed.workflows.AverageByLetter.AverageByLetterMapper;
import mapRed.workflows.AverageByLetter.AverageByLetterReducer;
import mapRed.workflows.CountDistinctTokens.CountTokensMapper;
import mapRed.workflows.CountDistinctTokens.CountTokensReducer;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

public class JobControlWorkflow extends Configured implements Tool{
	private final Logger log = Logger.getLogger(JobControlWorkflow.class);
	@Override
	public int run(String[] args) throws Exception {
		String inputText = args[0];
		String finalOutput = args[1];
		
		String intermediateTempDir = "/tmp/" + getClass().getSimpleName() + "-tmp";
		Path intermediatePath = new Path(intermediateTempDir);
		log.info("Using tmp dir of [" + intermediatePath + "]");
		delete(intermediatePath);
		
		try {
			JobControl control = new JobControl("JobControl-Worklfow");
			
			ControlledJob step1 = new ControlledJob(getStep1(inputText, intermediateTempDir), null);
			ControlledJob step2 = new ControlledJob(getStep2(intermediateTempDir, finalOutput), Arrays.asList(step1));
			
			control.addJob(step1);
			control.addJob(step2);		
			
			Thread workflowThread = new Thread(control, "Workflow-Thread");
			workflowThread.setDaemon(true);
			workflowThread.start();
			
			while (!control.allFinished()){
				Thread.sleep(500);
			}
			if ( control.getFailedJobList().size() > 0 ){
				log.error(control.getFailedJobList().size() + " jobs failed!");
				for ( ControlledJob job : control.getFailedJobList()){
					log.error(job.getJobName() + " failed");
				}
			} else {
				log.info("Success!! Workflow completed [" + control.getSuccessfulJobList().size() + "] jobs");
			}
			
		} finally {
			delete(intermediatePath);
		}
		
		return 0;
	}
	
	private void delete(Path path) throws IOException {
		FileSystem fs = FileSystem.get(getConf());
		if (fs.exists(path)){
			log.info("Deleting [" + path + "]");
			fs.delete(path, true);
		}
	}
	
	private Job getStep1(String inputText, String tempOutputPath) throws IOException{
		Job job = Job.getInstance(getConf(), "step1");
		job.setJarByClass(getClass());
		
		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(inputText));
		job.setMapperClass(CountTokensMapper.class);
		job.setCombinerClass(CountTokensReducer.class);
		job.setReducerClass(CountTokensReducer.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(tempOutputPath));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		return job;
	}
	
	private Job getStep2(String intermediateTempDir, String finalOutput) throws IOException{
		Job job = Job.getInstance(getConf(), "step2");
		job.setJarByClass(getClass());
		
		// configure output and input source
		TextInputFormat.addInputPath(job, new Path(intermediateTempDir));
		job.setMapperClass(AverageByLetterMapper.class);
		job.setCombinerClass(AverageByLetterReducer.class);
		job.setReducerClass(AverageByLetterReducer.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(finalOutput));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		return job;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new JobControlWorkflow(), args);
		System.exit(exitCode);
	}
}
