package mr.wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class StartsWithCountMapper_PrintLocalCache extends
		Mapper<LongWritable, Text, Text, IntWritable> {
	private Logger log = Logger
			.getLogger(StartsWithCountMapper_PrintLocalCache.class);
	private final static IntWritable countOne = new IntWritable(1);
	private final Text reusableText = new Text();

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		log(context.getLocalCacheFiles());
		log(context.getLocalCacheArchives());
	}

	private void log(Path[] localCacheFiles) {
		if (localCacheFiles != null && localCacheFiles.length > 0) {
			for (Path path : localCacheFiles) {
				log.info("Localized File: " + path);
			}
		} else {
			log.info("Local Cache is empty");
		}
	}

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		StringTokenizer tokenizer = new StringTokenizer(value.toString());
		while (tokenizer.hasMoreTokens()) {
			reusableText.set(tokenizer.nextToken().substring(0, 1));
			context.write(reusableText, countOne);
		}
	}
}
