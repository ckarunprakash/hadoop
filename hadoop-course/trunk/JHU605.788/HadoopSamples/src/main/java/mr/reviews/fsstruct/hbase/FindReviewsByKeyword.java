package mr.reviews.fsstruct.hbase;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.IdentityTableReducer;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.FindReviewsByKeyword -Dreport.value="invaluable,affordable"
 */
public class FindReviewsByKeyword extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(HBaseConfiguration.create(getConf()), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        Scan scan = new Scan();
        TableMapReduceUtil.initTableMapperJob(
                ReviewHBaseSchema.REVIEW_TABLE, 
                scan, 
                FindReviewsByKeywordMapper.class, 
                NullWritable.class, Writable.class, 
                job,
                true);
        
        TableMapReduceUtil.initTableReducerJob(
                ReviewHBaseSchema.REVIEW_REPORT_TABLE, 
                IdentityTableReducer.class, job);
        job.setNumReduceTasks(0);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new FindReviewsByKeyword(), args);
        System.exit(exitCode);
    }
}
