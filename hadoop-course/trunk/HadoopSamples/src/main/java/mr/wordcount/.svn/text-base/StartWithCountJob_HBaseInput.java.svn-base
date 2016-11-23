package mr.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class StartWithCountJob_HBaseInput extends Configured implements Tool {

	protected final static String TABLE_NAME = "HBaseSamples";
	protected final static String FAMILY = "count";
	protected final static String COLUMN = "word";

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "StartsWithCount-FromHBase");
		job.setJarByClass(getClass());

		// configure input
		job.setInputFormatClass(TableInputFormat.class);
		job.setMapperClass(StartsWithCountMapper_HBase.class);
		
		Configuration conf = job.getConfiguration();
		HBaseConfiguration.merge(conf, HBaseConfiguration.create(conf));
		TableMapReduceUtil.addDependencyJars(job);
		
		conf.set(TableInputFormat.INPUT_TABLE, TABLE_NAME);
		conf.set(TableInputFormat.SCAN_COLUMNS, FAMILY + ":" + COLUMN);
		

		// configure mapper and reducer
		job.setCombinerClass(StartsWithCountReducer.class);
		job.setReducerClass(StartsWithCountReducer.class);

		// configure output
		TextOutputFormat.setOutputPath(job, new Path(args[0]));
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new StartWithCountJob_HBaseInput(), args);
		System.exit(exitCode);
	}
}
