package mr.chaining.tasks;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LowercaseMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
    private Text outKey = new Text();
    private Logger log = LoggerFactory.getLogger(LowercaseMapper.class);
    @Override
    protected void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {
       String lowcased = key.toString().toLowerCase();
       log.debug("Lowercased [{}]", lowcased);
       outKey.set(lowcased);       
       context.write(outKey, value);
    }
}
