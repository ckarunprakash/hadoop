package mr.reviews.fsstruct.avro;

import java.io.IOException;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;
import mr.reviews.fsstruct.support.ConfHelper;

import org.apache.avro.file.CodecFactory;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
JOB_CONF_FILE=$TRAINING_HOME/eclipse/workspace/HadoopSamples/src/main/resources/mr/reviews/fsstruct/avro/AvroMapRed.xml
AVRO_MAPRED_JAR=~/.m2/repository/org/apache/avro/avro-mapred/1.7.3/avro-mapred-1.7.3-hadoop2.jar
HADOOP_CLASSPATH=$AVRO_MAPRED_JAR 
yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.avro.ReviewAvroJob -libjars $AVRO_MAPRED_JAR -conf $JOB_CONF_FILE

 */
public class ReviewAvroInputOutputJob extends Configured implements Tool {
    
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
		
		// **********************************************
//      AvroKeyOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
        // This will not work as avro uses its own property but the method still
        // exists since Avro's output format(s) extend FileOutputFormat; this is
        // one downfall of inheritance
        // **********************************************
		
		AvroKeyOutputFormat.setCompressOutput(job, true);
		jobConf.set(AvroJob.CONF_OUTPUT_CODEC, CodecFactory.snappyCodec().toString());
		job.setOutputKeyClass(ReviewAvro.class);
        job.setOutputValueClass(NullWritable.class);
        AvroJob.setOutputKeySchema(job, ReviewReportAvro.SCHEMA$);
		
		// configure mapper and reducer
		job.setMapperClass(ReviewAvroInputMapper.class);
		job.setReducerClass(ReviewAvroOutputReducer.class);
    }

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ReviewAvroInputOutputJob(), args);
		System.exit(exitCode);
	}
}
