package mr.reviews.fsstruct.avro;

import java.io.IOException;

import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;
import mr.reviews.fsstruct.support.ConfHelper;

import org.apache.avro.file.CodecFactory;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ReviewAvroJob extends Configured implements Tool {
    
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
        Configuration jobConf = job.getConfiguration();
        ConfHelper confHelper = new ConfHelper(jobConf);
		
		job.setInputFormatClass(AvroKeyInputFormat.class);
		AvroKeyInputFormat.addInputPath(job, confHelper.getInput());
		
		job.setOutputFormatClass(AvroKeyOutputFormat.class);
		AvroKeyOutputFormat.setOutputPath(job, confHelper.getOutputPath());


        AvroKeyOutputFormat.setCompressOutput(job, true);
		// **********************************************
        // The following does not work:
        //   1. AvroKeyOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
        //      Method still exists since Avro's output format(s) extend FileOutputFormat;
        //      this is one downfall of inheritance
        // **********************************************

        // this property expect the string "snappy" and not the fully qualified class
        jobConf.set(AvroJob.CONF_OUTPUT_CODEC, CodecFactory.snappyCodec().toString());


		AvroJob.setOutputKeySchema(job, ReviewReportAvro.SCHEMA$);
        // uses the schema instead of set these methods
//        job.setOutputKeyClass(ReviewAvro.class);
//        job.setOutputValueClass(NullWritable.class);
		
		// configure mapper and reducer
		job.setMapperClass(ReviewAvroMapper.class);
		job.setCombinerClass(ReviewAvroCombiner.class);
		job.setReducerClass(ReviewAvroReducer.class);
		
		// configure keys used between mappers and reducers
		AvroJob.setMapOutputKeySchema(job, ReviewKeyAvro.SCHEMA$);
		AvroJob.setMapOutputValueSchema(job, ReviewReportAvro.SCHEMA$);
    }

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ReviewAvroJob(), args);
		System.exit(exitCode);
	}
}
