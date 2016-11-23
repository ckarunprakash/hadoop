package mr.wordcount;

import mr.BlogWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class CustomPartitioner extends Partitioner<Text, BlogWritable>{
	@Override
	public int getPartition(Text key, BlogWritable blog, int numReduceTasks) {
		int positiveHash = blog.getAuthor().hashCode()& Integer.MAX_VALUE;
		return positiveHash % numReduceTasks;
	}
}
