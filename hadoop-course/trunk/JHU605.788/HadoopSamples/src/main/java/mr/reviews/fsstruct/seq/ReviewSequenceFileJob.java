package mr.reviews.fsstruct.seq;

import java.io.IOException;

import mr.reviews.ReviewMapper;
import mr.reviews.ReviewReducer;
import mr.reviews.fsstruct.support.ConfHelper;
import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewReport;
import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ReviewSequenceFileJob extends Configured implements Tool {
    
    public final static String PROP_FIND_VALUE  = "report.value";
    public final static String PROP_INPUT_PATH = "report.input.path";
    public final static String PROP_OUTPUT_PATH = "report.output.path";
    
	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), this.getClass().getName());
		job.setJarByClass(getClass());
		configureJob(job);
		return job.waitForCompletion(true) ? 0 : 1;
	}

    private void configureJob(Job job) throws IOException {
        ConfHelper confHelper = new ConfHelper(job.getConfiguration());
		SequenceFileInputFormat.addInputPath(job, confHelper.getInput());
		job.setInputFormatClass(SequenceFileInputFormat.class);

        SequenceFileOutputFormat.setOutputPath(job, confHelper.getOutputPath());
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        
        SequenceFileOutputFormat.setCompressOutput(job, true);
        SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.BLOCK);
        SequenceFileOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
        
        job.setOutputKeyClass(ReviewKeyWritable.class);
        job.setOutputValueClass(ReviewReport.class);
		
		// configure mapper and reducer
		job.setMapperClass(ReviewMapper.class);
		job.setReducerClass(ReviewReducer.class);
		
		// configure keys used between mappers and reducers
		job.setMapOutputKeyClass(ReviewKeyWritable.class);
		job.setMapOutputValueClass(ReviewWritable.class);
    }

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ReviewSequenceFileJob(), args);
		System.exit(exitCode);
	}
}
