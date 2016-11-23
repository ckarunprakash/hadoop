package mr.wordcount.writables;

import mr.wordcount.StartsWithCountMapper;
import mr.wordcount.StartsWithCountReducer;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Show the usage of ObjectWritable - allow to emit more different types from Map phase
 */
public class StartsWithCountJob_ObjectWritable extends Configured implements Tool {

    static abstract class ObjWritableMapper extends Mapper<LongWritable, Text, Text, ObjectWritable> {
        protected final ObjectWritable outValue = new ObjectWritable();
        private final Text outKey = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer tokenizer = new StringTokenizer(value.toString());
            while (tokenizer.hasMoreTokens()) {
                outKey.set(tokenizer.nextToken().substring(0, 1));
                setValue();
                context.write(outKey, outValue);
            }
        }

        protected abstract void setValue();
    }

    static class LongObjWritableMapper extends ObjWritableMapper {
        private final static LongWritable countOne = new LongWritable(1);

        @Override
        protected void setValue() {
            outValue.set(countOne);
        }
    }

    static class IntObjWritableMapper extends ObjWritableMapper {
        private final static IntWritable countOne = new IntWritable(1);

        @Override
        protected void setValue() {
            outValue.set(countOne);
        }
    }

    static class ObjectWritableReducer extends Reducer<Text, ObjectWritable, Text, ObjectWritable> {
        private IntWritable intWritable = new IntWritable();
        private ObjectWritable outValue = new ObjectWritable();

        @Override
        protected void reduce(Text token, Iterable<ObjectWritable> counts, Context context) throws IOException,
                InterruptedException {
            int sum = 0;
            for (ObjectWritable count : counts) {
                Object incomingObj = count.get();
                if (incomingObj instanceof IntWritable) {
                    IntWritable intWritable = (IntWritable) incomingObj;
                    sum += intWritable.get();
                } else if (incomingObj instanceof LongWritable) {
                    LongWritable longWritable = (LongWritable) incomingObj;
                    sum += longWritable.get();
                } else {
                    throw new IllegalArgumentException("Reducer does not support type [" + incomingObj.getClass() + "]");
                }

            }
            intWritable.set(sum);
            outValue.set(intWritable);

            // TextOutputFormat will call .toString on the ObjectWritable, which will further give insight into ObjectWritable impl
            context.write(token, outValue);
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), getClass().getSimpleName());
        job.setJarByClass(getClass());

        // emitting validate in order to keep example clear
        Path inputPath1 = new Path(args[0]); // will be mapped to IntWritable output from map
        Path inputPath2 = new Path(args[1]); // will be mapped to LongWritable output from map
        Path output = new Path(args[2]);

        // configure input & mappers
        MultipleInputs.addInputPath(job, inputPath1, TextInputFormat.class, IntObjWritableMapper.class);
        MultipleInputs.addInputPath(job, inputPath2, TextInputFormat.class, LongObjWritableMapper.class);

        // reducer
        job.setCombinerClass(ObjectWritableReducer.class);
        job.setReducerClass(ObjectWritableReducer.class);

        // configure output
        TextOutputFormat.setOutputPath(job, output);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ObjectWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new StartsWithCountJob_ObjectWritable(), args);
        System.exit(exitCode);
    }
}
