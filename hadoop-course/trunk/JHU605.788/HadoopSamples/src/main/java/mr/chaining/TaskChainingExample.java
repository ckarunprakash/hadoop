package mr.chaining;

import mr.chaining.tasks.LowercaseMapper;
import mr.chaining.tasks.MinIntValueMapper;
import mr.chaining.tasks.MinTokenLengthMapper;
import mr.chaining.tasks.SumReducer;
import mr.chaining.tasks.TokenizeMapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import utils.FsUtil;

public class TaskChainingExample extends Configured implements Tool {
    public int run(String[] args) throws Exception {
        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        FsUtil.delete(getConf(), out);
        Job job = Job.getInstance(getConf(), getClass().getSimpleName());
        job.setJarByClass(getClass());
        
        TextInputFormat.addInputPath(job, in);
        job.setInputFormatClass(TextInputFormat.class);
        TextOutputFormat.setOutputPath(job, out);
        job.setOutputFormatClass(TextOutputFormat.class);

        ChainMapper.addMapper(job, 
                TokenizeMapper.class, 
                LongWritable.class, // in key class  
                Text.class,         // in value class
                Text.class,         // out key class
                IntWritable.class,  // out value class 
                new Configuration(false));
        
        Configuration minLengthConf = new Configuration(false);
        minLengthConf.setInt(MinTokenLengthMapper.PROP_MIN_LENGTH, 3);
        ChainMapper.addMapper(job, 
                MinTokenLengthMapper.class, 
                Text.class, IntWritable.class,         
                Text.class, IntWritable.class,   
                minLengthConf);
        
        ChainMapper.addMapper(job, 
                LowercaseMapper.class, 
                Text.class, IntWritable.class,         
                Text.class, IntWritable.class,   
                new Configuration(false));
        
        job.setCombinerClass(SumReducer.class);
        
        ChainReducer.setReducer(job, 
                SumReducer.class, 
                Text.class, IntWritable.class,
                Text.class, IntWritable.class, 
                new Configuration(false));
        
        Configuration minIntMapperConf = new Configuration(false);
        minIntMapperConf.setInt(MinIntValueMapper.PROP_MIN_VALUE, 10);
        ChainReducer.addMapper(job, 
                MinIntValueMapper.class, 
                Text.class, IntWritable.class,         
                Text.class, IntWritable.class,   
                minIntMapperConf);
        
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new TaskChainingExample(), args);
        System.exit(exitCode);
    }
}
