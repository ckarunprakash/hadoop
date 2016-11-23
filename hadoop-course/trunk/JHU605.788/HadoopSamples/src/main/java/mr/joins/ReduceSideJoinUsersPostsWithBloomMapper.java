package mr.joins;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.bloom.BloomFilter;
import org.apache.hadoop.util.bloom.Key;

import utils.ConfUtil;

public class ReduceSideJoinUsersPostsWithBloomMapper extends Mapper<LongWritable, Text, Text, Text> {
    private final Text joinKey = new Text();
    private final Text outputValue = new Text();
    
    public final static String PROP_BLOOM_FILE = "bloom.filter.file";

    private BloomFilter bloomFilter = new BloomFilter();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        File joinFile = ConfUtil.getFile(context.getConfiguration(), PROP_BLOOM_FILE);
        DataInputStream in = new DataInputStream(new FileInputStream(joinFile));
        bloomFilter.readFields(in);
    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String user = value.toString().split(",")[0];
        if (bloomFilter.membershipTest(new Key(user.getBytes()))){
            joinKey.set(user);
            outputValue.set("L" + value.toString());
            context.write(joinKey, outputValue);    
            context.getCounter("BLOOM", "PASS").increment(1);
        } else {
            context.getCounter("BLOOM", "FILTERED").increment(1);
        }
    }
}