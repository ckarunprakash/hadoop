package mr.wordcount;

import static mr.wordcount.StartWithCountJob_HBaseInput.COLUMN;
import static mr.wordcount.StartWithCountJob_HBaseInput.FAMILY;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class StartsWithCountMapper_HBase extends TableMapper<Text, IntWritable> {
	private final static IntWritable countOne = new IntWritable(1);
	private final Text reusableText = new Text();

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context)
			throws IOException, InterruptedException {
		byte[] bytes = value.getValue(toBytes(FAMILY), toBytes(COLUMN));
		String str = Bytes.toString(bytes);
		reusableText.set(str.substring(0, 1));
		context.write(reusableText, countOne);
	}
}
