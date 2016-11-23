package mr.reviews.fsstruct.hbase;

import mr.reviews.model.ReviewKeyWritable;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.ReviewsReportViaHTables -Dreport.value="invaluable,affordable"
 */
public class ReviewsReportViaHTables extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(HBaseConfiguration.create(getConf()), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        Scan scan = new Scan();
        TableMapReduceUtil.initTableMapperJob(
                ReviewHBaseSchema.REVIEW_TABLE, 
                scan, 
                ReviewsReportByUserKeywordMapper.class, 
                ReviewKeyWritable.class, Result.class, 
                job,
                true);
        
        TableMapReduceUtil.initTableReducerJob(
                ReviewHBaseSchema.REVIEW_REPORT_TABLE, 
                ReviewsReportViaHTablesReducer.class, job);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new ReviewsReportViaHTables(), args);
        System.exit(exitCode);
    }
}
