package mr.chaining.tasks;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinIntValueMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
    private Logger log = LoggerFactory.getLogger(MinIntValueMapper.class);
    public static String PROP_MIN_VALUE = "min.int.value";
    private int minValue;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        minValue = context.getConfiguration().getInt(PROP_MIN_VALUE, 100);
        log.info("Created filter [{}] with value of [{}]", this.getClass(), minValue);
    }

    @Override
    protected void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {
       if ( value.get() >= minValue){
           context.write(key, value);           
       } else {
           log.debug("Filtered with key [{}] because value [{}] is too low", key, value);
       }
    }
}
