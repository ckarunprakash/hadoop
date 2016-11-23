package mr.joins;

import java.io.IOException;

import mr.joins.support.TextPair;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RightSideMapper extends Mapper<LongWritable, Text, TextPair, TextPair> {
    public static final Text INDICATOR = new Text("R");
    private final Text joinKey = new Text();

    private final TextPair joinKeyPair = new TextPair();
    private final TextPair valuePair = new TextPair();
    {
        joinKeyPair.setSecond(INDICATOR);
        valuePair.setSecond(INDICATOR);
    }
    
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String [] split = value.toString().split(",");
        joinKey.set(split[0]);
        
        joinKeyPair.setFirst(joinKey);
        valuePair.setFirst(value);
        context.write(joinKeyPair, valuePair);
    }
}
