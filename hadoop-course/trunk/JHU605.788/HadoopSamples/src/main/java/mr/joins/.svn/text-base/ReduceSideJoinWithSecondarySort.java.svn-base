package mr.joins;

import mr.joins.ReduceSideJoinWithSecondarySortReducer.ReduceSideGroupComparator;
import mr.joins.ReduceSideJoinWithSecondarySortReducer.ReduceSidePartitioner;
import mr.joins.support.TextPair;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReduceSideJoinWithSecondarySort <user_posts_path> <users_likes_path> <output_path>
yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReduceSideJoinWithSecondarySort /data/users/user-posts.txt /data/users/user-likes.txt /training/playArea/reduceSideJoin/
 */
public class ReduceSideJoinWithSecondarySort extends Configured implements Tool{
    
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        MultipleInputs.addInputPath(job, new Path(args[0]), 
                TextInputFormat.class, LeftSideMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), 
                TextInputFormat.class, RightSideMapper.class);
        
        job.setReducerClass(ReduceSideJoinWithSecondarySortReducer.class);
        TextOutputFormat.setOutputPath(job, new Path(args[2]));
        job.setMapOutputKeyClass(TextPair.class);
        job.setMapOutputValueClass(TextPair.class);
        
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        job.setPartitionerClass(ReduceSidePartitioner.class);
        job.setGroupingComparatorClass(ReduceSideGroupComparator.class);
        
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new ReduceSideJoinWithSecondarySort(), args);
        System.exit(exitCode);
    }
}
