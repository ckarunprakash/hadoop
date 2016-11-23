package crunch.join;

import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.Pair;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.lib.Join;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/**
 * CRUNCH_JAR=/home/hadoop/.m2/repository/org/apache/crunch/crunch/0.5.0-cdh4.1.3/crunch-0.5.0-cdh4.1.3.jar 
 * HADOOP_CLASSPATH=$CRUNCH_JAR:$HADOOP_CLASSPATH
 * yarn jar $PLAY_AREA/HadoopSamples.jar crunch.join.JoinPostsAndLikesCrunch -libjars $CRUNCH_JAR  /data/users/user-posts.txt /data/users/user-likes.txt
 * 
 */
public class JoinPostsAndLikesCrunch extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        String postsInput = args[0];
        String likesInput = args[1];
        
        Pipeline pipeline = new MRPipeline(JoinPostsAndLikesCrunch.class, getConf());
        PCollection<String> postsLines = pipeline.readTextFile(postsInput);
        PCollection<String> likesLines = pipeline.readTextFile(likesInput);

        PTable<String,String> posts = postsLines.parallelDo(
                "prepare posts", new ExtractPostsDoFN(),
                Writables.tableOf(Writables.strings(), Writables.strings()));

        PTable<String,String> likes = likesLines.parallelDo(
                "prepare likes", new ExtractPostsDoFN(),
                Writables.tableOf(Writables.strings(), Writables.strings()));
        
        PTable<String,Pair<String,String>> joined = Join.join(posts, likes);
        for ( Pair<String,Pair<String,String>> joinedRecord : joined.materialize()){
            System.out.println(joinedRecord.first() + 
                    ": [" + joinedRecord.second().first() + "] and [" + 
                            joinedRecord.second().second() + "]");
        }
        PipelineResult res = pipeline.done();
        return res.succeeded() ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new JoinPostsAndLikesCrunch(), args);
        System.exit(exitCode);
    }
}
