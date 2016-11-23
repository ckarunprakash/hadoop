package mapRed.inputAndOutput;

import static mapRed.inputAndOutput.UniqueCounterTool.*;
import static org.apache.hadoop.hbase.util.Bytes.*;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

public class UniqueCounterReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> counts, Context context)
			throws IOException, InterruptedException {
		// TODO: implement
		// HINT: use key.copyBytes() to the row id
		// HINT: Use static variables for family and column names declared in UniqueCounterTool
	}
}
