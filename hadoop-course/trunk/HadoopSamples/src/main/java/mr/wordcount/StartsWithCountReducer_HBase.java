package mr.wordcount;

import static mr.wordcount.StartWithCountJob_HBase.FAMILY;
import static mr.wordcount.StartWithCountJob_HBase.RESULT_COLUMN;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class StartsWithCountReducer_HBase extends
		TableReducer<Text, IntWritable, ImmutableBytesWritable> {
	@Override
	protected void reduce(Text key, Iterable<IntWritable> counts,
			Context context) throws IOException, InterruptedException {
		int sum = 0;
		
		for (IntWritable count : counts) {
			sum+= count.get();
		}
		Put put = new Put(key.copyBytes());
		put.add(toBytes(FAMILY), toBytes(RESULT_COLUMN), toBytes(Integer.toString(sum)));
		context.write(null, put);
	}
	
}
