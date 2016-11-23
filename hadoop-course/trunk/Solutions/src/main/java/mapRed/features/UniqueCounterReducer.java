package mapRed.features;

import static mapRed.features.UniqueCounterTool.COUNTER_GROUP;
import static mapRed.features.UniqueCounterTool.OUTPUT_COLUMN;
import static mapRed.features.UniqueCounterTool.OUTPUT_FAMILY;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.log4j.Logger;

public class UniqueCounterReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {

	private final Logger log = Logger.getLogger(UniqueCounterReducer.class);
	@Override
	protected void reduce(Text key, Iterable<IntWritable> counts, Context context)
			throws IOException, InterruptedException {
		log.debug("Processing key [" + key.toString() + "]");
		int sum = 0;
		int numOfItems = 0;
		for (IntWritable count : counts) {
			sum += count.get();
			numOfItems++;
		}
		
		Put put = new Put(key.copyBytes());
		put.add(OUTPUT_FAMILY, OUTPUT_COLUMN, toBytes(Integer.toString(sum)));

		context.write(null, put);
		
		Counter counter = context.getCounter(COUNTER_GROUP, "in");
		counter.increment(numOfItems);
		counter = context.getCounter(COUNTER_GROUP, "out");
		counter.increment(1);
	}
}
