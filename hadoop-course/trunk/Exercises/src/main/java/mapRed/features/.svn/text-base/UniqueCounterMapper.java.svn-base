package mapRed.features;

import static mapRed.features.UniqueCounterTool.*;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;

public class UniqueCounterMapper extends TableMapper<Text, IntWritable> {

	private final static IntWritable countOne = new IntWritable(1);
	private final Text reusableText = new Text();

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context)
			throws IOException, InterruptedException {

		byte[] bytes = value.getValue(INPUT_FAMILY, INPUT_COLUMN);
		String str = Bytes.toString(bytes);
		reusableText.set(str);
		context.write(reusableText, countOne);
	}
}
