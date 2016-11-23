package mr.joins;

import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_STATE;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TEXT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TIMESTAMP;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;

import utils.ConfUtil;

public class ReplicatedJoinMapper extends TableMapper<NullWritable, Writable> {
    public final static String PROP_JOIN_FILE = "replicated.join.file";
    private final Map<String,byte[]> userToState = new HashMap<String,byte[]>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        File joinFile = ConfUtil.getFile(context.getConfiguration(), PROP_JOIN_FILE);
        FileReader reader = new FileReader(joinFile);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String [] split = line.split(",");
                userToState.put(split[0], toBytes(split[1]));
            }
            
        } finally {
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(reader);
        }
    }
    
    @Override
    public void map(ImmutableBytesWritable key, Result value, Context context) throws IOException,
            InterruptedException {
        String user = Bytes.toString(value.getValue(REVIEW_FAMILY_CONTENT,REVIEW_COLUMN_USER));
        byte [] state = userToState.get(user);
        if ( state != null ){
            Put put = new Put(value.getRow());
            add(put,value,REVIEW_FAMILY_CONTENT,REVIEW_COLUMN_TEXT);
            add(put,value,REVIEW_FAMILY_CONTENT,REVIEW_COLUMN_USER);
            add(put,value,REVIEW_FAMILY_CONTENT,REVIEW_COLUMN_TIMESTAMP);
            put.add(REVIEW_FAMILY_CONTENT,REVIEW_COLUMN_STATE,state);
            context.write(NullWritable.get(), put);
        }
    }
    public void add(Put put, Result result, byte [] family, byte [] column){
        put.add(family,column,result.getValue(family, column));
    }

}
