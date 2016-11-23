package mr.joins;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ReduceSideJoinUsersLikesMapper extends Mapper<LongWritable, Text, Text, Text> {
    private final Text joinKey = new Text();
    private final Text outputValue = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        joinKey.set(value.toString().split(",")[0]);
        outputValue.set("R" + value.toString());
        context.write(joinKey, outputValue);
    }
}
