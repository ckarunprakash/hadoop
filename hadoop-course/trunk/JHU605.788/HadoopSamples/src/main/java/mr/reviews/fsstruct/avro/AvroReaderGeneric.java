package mr.reviews.fsstruct.avro;

import java.io.InputStream;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvroReaderGeneric extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Path inputPath = new Path(args[0]);
        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(inputPath) && fs.isFile(inputPath));

        InputStream in = null;
        DataFileStream<GenericRecord> reader = null;
        try {
            in = fs.open(inputPath);
            DatumReader<GenericRecord> gen = new GenericDatumReader<GenericRecord>();
            reader = new DataFileStream<GenericRecord>(in, gen);
            
            for (GenericRecord genericRecord : reader){
                System.out.println(genericRecord);
            }
        } finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(reader);
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new AvroReaderGeneric(), args);
        System.exit(code);
    }
}
