package mr.reviews.fsstruct.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvroSorter extends Configured implements Tool {
    private static Logger LOG = LoggerFactory.getLogger(AvroSorter.class);
    
    static class AvroSortMapper<K> extends Mapper<AvroKey<K>, NullWritable, AvroKey<K>, AvroValue<K>> {
        private final AvroValue<K> value = new AvroValue<K>();
        @Override
        protected void map(AvroKey<K> keyIn, NullWritable ignore, Context context) throws IOException,
                InterruptedException {
            value.datum(keyIn.datum());
            context.write(keyIn, value);
        }
    }

    static class AvroSortReducer<K> extends Reducer<AvroKey<K>, AvroValue<K>, AvroKey<K>, NullWritable> {
        private final AvroKey<K> key = new AvroKey<K>();
        @Override
        protected void reduce(AvroKey<K> inputKey, Iterable<AvroValue<K>> values, Context context) throws IOException,
                InterruptedException {
            for (AvroValue<K> val : values) {
                key.datum(val.datum());
                context.write(key, NullWritable.get());
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        validateArgs(args);
        
        Path from = new Path(args[0]);
        Path to = new Path(args[1]);
        File schemaFile = new File(args[2]);
        validate(from,to,schemaFile);
        
        Job job = Job.getInstance(getConf(), this.getClass().getName());
        job.setJarByClass(getClass());
        
        Schema schema = new Schema.Parser().parse(schemaFile);
        
        Configuration jobConf = job.getConfiguration();
        
        job.setInputFormatClass(AvroKeyInputFormat.class);
        AvroKeyInputFormat.addInputPath(job, from);
        
        // configure keys used between mappers and reducers
        AvroJob.setMapOutputKeySchema(job, schema);
        AvroJob.setMapOutputValueSchema(job, schema);
        
        job.setOutputFormatClass(AvroKeyOutputFormat.class);
        AvroKeyOutputFormat.setOutputPath(job, to);
        
        AvroKeyOutputFormat.setCompressOutput(job, true);
        jobConf.set(AvroJob.CONF_OUTPUT_CODEC, CodecFactory.snappyCodec().toString());
        AvroJob.setOutputKeySchema(job, schema);
        
        // sort mapper and reducer
        job.setMapperClass(AvroSortMapper.class);
        job.setReducerClass(AvroSortReducer.class);
        
        return job.waitForCompletion(true) ? 0 : 1;
    }

    private void validateArgs(String[] args) {
        if (args.length != 3) {
            System.err.printf("Use: [options] <input_path> <output_path> <schema_file>\n", getClass()
                    .getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            System.exit(-1);
        }
    }

    private void validate(Path from, Path to, File schema) throws IOException {
        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(from), "From [" + from + "] location must exist");
        Validate.isTrue(from.getName().endsWith(".avro"), "From [" + from + "] location must be an avro file");
        if ( fs.exists(to)){
            LOG.info("Removing desitncation directory [{}]", to);
            fs.delete(to, true);
        }
        
        Validate.isTrue(schema.exists(), "Schema [" + schema + "] location must exist");
        Validate.isTrue(schema.getName().endsWith(".avsc"), "[" + schema + "] must be an avro schema");
    }
    
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new AvroSorter(), args);
        System.exit(exitCode);
    }
}
