package mr.reviews.fsstruct.avro;

import java.io.InputStream;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewAvroV2;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvroReaderSpecificV2 extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Path inputPath = new Path(args[0]);
        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(inputPath) && fs.isFile(inputPath));

        InputStream in = null;
        DataFileStream<ReviewAvroV2> reader = null;
        try {
            in = fs.open(inputPath);
            reader = new DataFileStream<ReviewAvroV2>(in, new SpecificDatumReader<ReviewAvroV2>(ReviewAvroV2.class));
            for ( ReviewAvroV2 review : reader){
                System.out.println(review);
            }
        } finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(reader);
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new AvroReaderSpecificV2(), args);
        System.exit(code);
    }
}
