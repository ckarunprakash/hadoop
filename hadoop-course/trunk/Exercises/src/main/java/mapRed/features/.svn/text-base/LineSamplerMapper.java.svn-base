package mapRed.features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class LineSamplerMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	private final Text emptyText = new Text();
	
	// TODO: load tokens into this set from file 'tokensToRetain.txt' that should reside on a Distributed Cache
	private final Set<String> tokensToRetain = new HashSet<String>();
	
	@Override	
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		for ( String tokenToRetain : tokensToRetain ){
			if ( value.toString().matches(".*\\b" + tokenToRetain + "\\b.*$")){
				context.write(value, emptyText);
				// counter for all the rows to be retained and for each token
				context.getCounter("LineSampler", "Rows Retained").increment(1);
				context.getCounter("LineSampler", "Rows Retained [" + tokenToRetain + "]").increment(1);
				return;
			}
		}
	}
}
