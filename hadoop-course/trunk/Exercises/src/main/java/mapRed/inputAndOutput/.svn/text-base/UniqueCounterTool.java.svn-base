package mapRed.inputAndOutput;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class UniqueCounterTool extends Configured implements Tool {

	protected final static String INPUT_TABLE_NAME = "Exercise_InputAndOutput_Tokens";
	protected final static byte[] INPUT_FAMILY = toBytes("token");
	protected final static byte[] INPUT_COLUMN = toBytes("value");
	
	protected final static String OUTPUT_TABLE_NAME = "Exercise_InputAndOutput_Result";
	protected final static byte[] OUTPUT_FAMILY = toBytes("counts");
	protected final static byte[] OUTPUT_COLUMN = toBytes("count");

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), getClass().getSimpleName());
		job.setJarByClass(getClass());

		// TODO: Implement
		// HINT: utilize TableMapReduceUtil to set up mapper and reducer
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new UniqueCounterTool(), args);
		System.exit(exitCode);
	}
}
