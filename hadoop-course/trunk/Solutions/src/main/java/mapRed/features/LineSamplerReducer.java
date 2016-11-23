package mapRed.features;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LineSamplerReducer extends Reducer<Text, Text, Text, Text> {
	private final Text emptyText = new Text();
	@Override
	protected void reduce(Text token, Iterable<Text> nothing, Context context)
			throws IOException, InterruptedException {	
		context.write(token, emptyText);
	}
}