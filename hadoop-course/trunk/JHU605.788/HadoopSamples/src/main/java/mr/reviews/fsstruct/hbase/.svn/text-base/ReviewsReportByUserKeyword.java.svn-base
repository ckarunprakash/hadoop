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
yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.ReviewsReportByUserKeyword -Dreport.value="invaluable,affordable"
 */
public class ReviewsReportByUserKeyword extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(HBaseConfiguration.create(getConf()), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        Scan scan = new Scan();
        TableMapReduceUtil.initTableMapperJob(
                ReviewHBaseSchema.REVIEW_TABLE,  // table name
                scan,                            // scan
                ReviewsReportByUserKeywordMapper.class, // mapper
                ReviewKeyWritable.class, Result.class, // output key and value of map phase
                job,  // job
                true); // add dependencies to tasks' CLASSPATH
        
        TableMapReduceUtil.initTableReducerJob(
                ReviewHBaseSchema.REVIEW_REPORT_TABLE, 
                ReviewsReportByUserKeywordReducer.class, job);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new ReviewsReportByUserKeyword(), args);
        System.exit(exitCode);
    }
}
