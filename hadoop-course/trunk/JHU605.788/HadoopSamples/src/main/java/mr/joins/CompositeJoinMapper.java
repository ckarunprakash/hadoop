package mr.joins;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.join.TupleWritable;

public class CompositeJoinMapper extends Mapper<Text, TupleWritable, Text, Text> {

    @Override
    protected void map(Text key, TupleWritable value, Context context)
            throws IOException, InterruptedException {
        context.write((Text)value.get(0), (Text)value.get(1));
    }
}
