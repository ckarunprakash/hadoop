package mr.wordcount;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class StartWithCountJob_HBase extends Configured implements Tool {

	protected final static String TABLE_NAME = "HBaseSamples";
	protected final static String FAMILY = "count";
	protected final static String INPUT_COLUMN = "word";
	
	protected final static String RESULT_COLUMN = "result";

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "StartsWithCount-HBase");
		job.setJarByClass(getClass());

		Scan scan = new Scan();
		scan.addColumn(toBytes(FAMILY), toBytes(INPUT_COLUMN));
		TableMapReduceUtil.initTableMapperJob(
			TABLE_NAME,       // input table
			scan,	          // Scan instance to control CF and attribute selection
			StartsWithCountMapper_HBase.class,   // mapper class
			Text.class,	      // mapper output key
			IntWritable.class,// mapper output value
			job);
		TableMapReduceUtil.initTableReducerJob(
			TABLE_NAME,      // output table
			StartsWithCountReducer_HBase.class,  // reducer class
			job);
		job.setNumReduceTasks(1);                // at least one, adjust as required
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new StartWithCountJob_HBase(), args);
		System.exit(exitCode);
	}
}
