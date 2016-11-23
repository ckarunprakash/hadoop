package mr.joins;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.joins.ReduceSideJoinWithBloomFilter -file <bloom> <user_posts_path> <users_likes_path> <output_path>

BLOOM=$TRAINING_HOME/eclipse/workspace/HadoopSamples/src/main/resources/mr/reviews/joins/userBloomFilter.bloom 
yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReduceSideJoinWithBloomFilter -files $BLOOM -D bloom.filter.file=userBloomFilter.bloom /data/users/user-posts.txt /data/users/user-likes.txt /training/playArea/reduceSideJoin/
 */
public class ReduceSideJoinWithBloomFilter extends Configured implements Tool{
    
    
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        MultipleInputs.addInputPath(job, new Path(args[0]), 
                TextInputFormat.class, ReduceSideJoinUsersPostsWithBloomMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), 
                TextInputFormat.class, ReduceSideJoinUsersLikesMapper.class);
        
        job.setReducerClass(ReduceSideJoinReducer.class);
        TextOutputFormat.setOutputPath(job, new Path(args[2]));
        
        LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new ReduceSideJoinWithBloomFilter(), args);
        System.exit(exitCode);
    }
}
