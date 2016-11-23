package mr.chaining;

import static mr.chaining.support.SampleJobFactory.createGrep;
import static mr.chaining.support.SampleJobFactory.createWordCount;
import static mr.chaining.support.SampleJobFactory.randomTextWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import utils.FsUtil;

/**
yarn jar $PLAY_AREA/HadoopSamples.jar mr.chaining.SimpleLinearDriver /training/playArea/SimpleLinearDriver/
 *
 */
public class SimpleLinearDriver extends Configured implements Tool {
    public int run(String[] args) throws Exception {
        Path workingDir = new Path(args[0]);
        Configuration conf = getConf();
        FileSystem fs = FileSystem.get(conf);
        FsUtil.delete(fs, workingDir);
        Path intermediateOutput = new Path(workingDir, "intermediate_output");
        Path countOutput = new Path(workingDir, "count_result");
        Path grepOutput = new Path(workingDir, "grep_result");
        
        Job genText = randomTextWriter(conf, "job1-WriteText", intermediateOutput);
        boolean status = genText.waitForCompletion(true);
        if ( status ){
            Job countWords = createWordCount(conf, "job2-WordCount", intermediateOutput, countOutput);
            status = countWords.waitForCompletion(true);
        }
        if ( status ){
            Job grep = createGrep(conf, "job3-Grep", intermediateOutput, grepOutput, ".*au.*");
            status = grep.waitForCompletion(true);
        }
            
        FsUtil.delete(fs, intermediateOutput);
        return status ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new SimpleLinearDriver(), args);
        System.exit(exitCode);
    }
}
