package mr.chaining;

import java.util.Arrays;

import static mr.chaining.support.SampleJobFactory.createGrep;
import static mr.chaining.support.SampleJobFactory.createWordCount;
import static mr.chaining.support.SampleJobFactory.randomTextWriter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FsUtil;

/**
 * yarn jar $PLAY_AREA/HadoopSamples.jar mr.chaining.JobControlDriver /training/playArea/JobControlDriver/
 * 
 */
public class JobControlDriver extends Configured implements Tool {
    private Logger log = LoggerFactory.getLogger(JobControlDriver.class);

    public int run(String[] args) throws Exception {
        Path workingDir = new Path(args[0]);
        Configuration conf = getConf();
        FileSystem fs = FileSystem.get(conf);
        FsUtil.delete(fs, workingDir);
        Path intermediateOutput = new Path(workingDir, "intermediate_output");
        Path countOutput = new Path(workingDir, "count_result");
        Path grepOutput = new Path(workingDir, "grep_result");

        JobControl control = new JobControl("JobControl-Example");
        ControlledJob genDataStep = new ControlledJob(randomTextWriter(conf, "job1-WriteText",
                intermediateOutput), null); // dependent jobs
        ControlledJob countStep = new ControlledJob(createWordCount(conf, "job2-WordCount",
                intermediateOutput, countOutput), Arrays.asList(genDataStep)); // dependent
                                                                               // jobs
        ControlledJob grepStep = new ControlledJob(createGrep(conf, "job3-Grep", intermediateOutput, grepOutput, ".*au.*"),
                Arrays.asList(genDataStep)); // dependent
                                                                               // jobs

        control.addJobCollection(Arrays.asList(genDataStep, countStep, grepStep));

        Thread workflowThread = new Thread(control, "Workflow-Thread");
        workflowThread.setDaemon(true);
        workflowThread.start();

        while (!control.allFinished()) {
            Thread.sleep(500);
        }

        report(control);

        FsUtil.delete(fs, intermediateOutput);
        return CollectionUtils.isEmpty(control.getFailedJobList()) ? 0 : 1;
    }

    private void report(JobControl control) {
        if (control.getFailedJobList().size() > 0) {
            log.error("[{}] jobs failed!", control.getFailedJobList().size());
            for (ControlledJob job : control.getFailedJobList()) {
                log.error("[{}] failed", job.getJobName());
            }
        } else {
            log.info("Success!! Workflow completed [{}] jobs", control.getSuccessfulJobList().size());
            for (ControlledJob job : control.getSuccessfulJobList()) {
                log.info("[{}] completed successfully", job.getJobName());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new JobControlDriver(), args);
        System.exit(exitCode);
    }
}
