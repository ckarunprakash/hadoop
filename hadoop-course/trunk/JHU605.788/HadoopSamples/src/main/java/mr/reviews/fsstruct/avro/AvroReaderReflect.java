package mr.reviews.fsstruct.avro;

import java.io.File;
import java.io.InputStream;

import mr.reviews.fsstruct.avro.model.ReviewAvro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvroReaderReflect extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Path inputPath = new Path(args[0]);
        File schemaFile = new File(args[1]);

        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(inputPath) && fs.isFile(inputPath));

        InputStream in = null;
        DataFileStream<ReviewAvro> reader = null;
        try {
            Schema schema = new Schema.Parser().parse(schemaFile);
            in = fs.open(inputPath);
            
            // Prior 1.7.3 release will use classloader that many not have 
            // knowledge of ReviewAvro class will not be found; avro then defaults
            // to GenericData$Record which cause this code to get
            // ClassCastException; This issue is addressed/explained in
            // https://issues.apache.org/jira/browse/AVRO-1123
            ReflectData rd = new ReflectData(this.getClass().getClassLoader());
            ReflectDatumReader<ReviewAvro> reflect = new ReflectDatumReader<ReviewAvro>(schema,schema,rd);
            reader = new DataFileStream<ReviewAvro>(in, reflect);
            Object o = reader.next();
            System.out.println(o);
            for (ReviewAvro review : reader) {
                System.out.println(review);
            }
        } finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(reader);
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new AvroReaderReflect(), args);
        System.exit(code);
    }
}
