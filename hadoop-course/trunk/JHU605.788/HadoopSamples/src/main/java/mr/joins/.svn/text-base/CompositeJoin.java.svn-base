package mr.joins;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.join.CompositeInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.CompositeJoin <in1> <in2> <out>
yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.CompositeJoin examples_input/compositeJoin/data1/ examples_input/compositeJoin/data2/ /training/playArea/compositeJoin/
 */
public class CompositeJoin extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(HBaseConfiguration.create(getConf()), this.getClass().getName());        
        job.setJarByClass(getClass());
        
        Path joinP1 = new Path(args[0]);
        Path joinP2 = new Path(args[1]);
        Path out = new Path(args[2]);
        
        job.setInputFormatClass(CompositeInputFormat.class);
        job.setMapperClass(CompositeJoinMapper.class);
        job.getConfiguration().set(CompositeInputFormat.JOIN_EXPR, 
                CompositeInputFormat.compose("inner", KeyValueTextInputFormat.class, joinP1, joinP2));
        job.setNumReduceTasks(0);
        TextOutputFormat.setOutputPath(job, out);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new CompositeJoin(), args);
        System.exit(exitCode);
    }
}
