package mr.wordcount;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class StartsWithCountJob_DistCache extends Configured implements Tool{

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), getClass().getSimpleName());       
        job.setJarByClass(getClass());

        // configure output and input source
        TextInputFormat.addInputPath(job, new Path(args[0]));
        job.setInputFormatClass(TextInputFormat.class);
        
        // configure mapper and reducer
        job.setMapperClass(StartsWithCountMapper_DistCache.class);
        job.setCombinerClass(StartsWithCountReducer.class);
        job.setReducerClass(StartsWithCountReducer.class);

        // configure output
        TextOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new StartsWithCountJob_DistCache(), args);
        System.exit(exitCode);
    }
}
