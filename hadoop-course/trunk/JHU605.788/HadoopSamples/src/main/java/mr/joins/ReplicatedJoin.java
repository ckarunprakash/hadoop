package mr.joins;

import mr.reviews.fsstruct.hbase.ReviewHBaseSchema;

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
yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReplicatedJoin -Dreplicated.join.file=userToState.txt -libjars $PLAY_AREA/data/userToState.txt
 */
public class ReplicatedJoin extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(HBaseConfiguration.create(getConf()), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        TableMapReduceUtil.initTableMapperJob(
                ReviewHBaseSchema.REVIEW_TABLE, 
                new Scan(), 
                ReplicatedJoinMapper.class, 
                NullWritable.class, Writable.class, 
                job,
                true);
        
        TableMapReduceUtil.initTableReducerJob(
                ReviewHBaseSchema.REVIEW_JOIN_TABLE, 
                IdentityTableReducer.class, job);
        job.setNumReduceTasks(0);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new ReplicatedJoin(), args);
        System.exit(exitCode);
    }
}
