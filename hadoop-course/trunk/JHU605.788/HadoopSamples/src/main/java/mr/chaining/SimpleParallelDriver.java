package mr.chaining;

import java.io.IOException;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FsUtil;

/**
yarn jar $PLAY_AREA/HadoopSamples.jar mr.chaining.SimpleParallelDriver /training/playArea/SimpleParallelDriver/
 *
 */
public class SimpleParallelDriver extends Configured implements Tool {
    private Logger log = LoggerFactory.getLogger(SimpleParallelDriver.class);
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
            countWords.submit();
            Job grep = createGrep(conf, "job3-Grep", intermediateOutput, grepOutput, ".*au.*");
            grep.submit();
            
            while(!countWords.isComplete() || !grep.isComplete()){
                Thread.sleep(1000);
            }
            
            reportStatus(countWords);
            reportStatus(grep);
        }
            
        FsUtil.delete(fs, intermediateOutput);
        return status ? 0 : 1;
    }

    private void reportStatus(Job job) throws IOException, InterruptedException {
        if(job.isSuccessful()){
            log.info("Job [{}] completed successfully", job.getJobName());
        } else {
            log.info("Job [{}] failed", job.getJobName());
        }
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new SimpleParallelDriver(), args);
        System.exit(exitCode);
    }
}
