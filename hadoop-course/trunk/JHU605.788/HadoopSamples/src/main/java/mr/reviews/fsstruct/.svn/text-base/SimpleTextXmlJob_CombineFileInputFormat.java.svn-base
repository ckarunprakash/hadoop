package mr.reviews.fsstruct;

import mr.reviews.ReviewOutputFormat;
import mr.reviews.ReviewReducer;
import mr.reviews.fsstruct.support.ConfHelper;
import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewReport;
import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SimpleTextXmlJob_CombineFileInputFormat extends Configured implements Tool{

    @Override
    public int run(String[] args) throws Exception {
        ConfHelper confHelper = new ConfHelper(getConf());
        Job job = Job.getInstance(getConf(), this.getClass().getName());
        job.setJarByClass(getClass());
        
        // configure output and input source
        EntireFileCombineFileTextInputFormat.addInputPath(job, confHelper.getInput());
        EntireFileCombineFileTextInputFormat.setMaxInputSplitSize(job, 256*1024); // 256kb
        job.setInputFormatClass(EntireFileCombineFileTextInputFormat.class);

        // configure mapper and reducer
        job.setMapperClass(XmlProcessMapper.class);
        job.setReducerClass(ReviewReducer.class);
        
        // configure keys used between mappers and reducers
        job.setMapOutputKeyClass(ReviewKeyWritable.class);
        job.setMapOutputValueClass(ReviewWritable.class);
        
        ReviewOutputFormat.setOutputPath(job, confHelper.getOutputPath());
        job.setOutputFormatClass(ReviewOutputFormat.class);
        job.setOutputKeyClass(ReviewKeyWritable.class);
        job.setOutputValueClass(ReviewReport.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new SimpleTextXmlJob_CombineFileInputFormat(), args);
        System.exit(exitCode);
    }

}
