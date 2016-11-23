package mr.chaining.tasks;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinTokenLengthMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
    private Logger log = LoggerFactory.getLogger(MinTokenLengthMapper.class);
    public static String PROP_MIN_LENGTH = "min.token.length";
    private int minLength;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        minLength = context.getConfiguration().getInt(PROP_MIN_LENGTH, 3);
        log.info("Created filter [{}] with value of [{}]", this.getClass(), minLength);
    }

    @Override
    protected void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {
       if ( key.toString().length()>=minLength){
           context.write(key, value);
       }  else {
           log.debug("Filtred record with key [{}] because it was shorter than [{}]", key, minLength);
       }
    }
}
