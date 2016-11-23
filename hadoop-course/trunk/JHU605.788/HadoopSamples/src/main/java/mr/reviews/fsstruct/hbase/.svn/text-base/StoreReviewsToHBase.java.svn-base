package mr.reviews.fsstruct.hbase;

import mr.reviews.fsstruct.EntireFileCombineFileTextInputFormat;
import mr.reviews.fsstruct.support.ConfHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
CONF_FILE=$TRAINING_HOME/eclipse/workspace/HadoopSamples/src/main/resources/hbase/ReviewsJob.xml
yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.StoreReviewsToHBase -conf $CONF_FILE
 */
public class StoreReviewsToHBase extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(HBaseConfiguration.create(getConf()), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        Configuration conf = job.getConfiguration();
        ConfHelper confHelper = new ConfHelper(conf);
        
        // configure output and input source
        EntireFileCombineFileTextInputFormat.addInputPath(job, confHelper.getInput());
        EntireFileCombineFileTextInputFormat.setMaxInputSplitSize(job, 256*1024); // 256kb
        job.setInputFormatClass(EntireFileCombineFileTextInputFormat.class);
       
        job.setMapperClass(StoreReviewToHBaseMapper.class);
        job.setReducerClass(Reducer.class);
        
        job.setOutputFormatClass(TableOutputFormat.class);
        conf.set(TableOutputFormat.OUTPUT_TABLE, ReviewHBaseSchema.REVIEW_TABLE);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Writable.class);
        job.setNumReduceTasks(0);
        
        // this needs to happen at the end because it will setJarByClass for
        // various class configured on job such as OutputFormat
        TableMapReduceUtil.addDependencyJars(job);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new StoreReviewsToHBase(), args);
        System.exit(exitCode);
    }
}
