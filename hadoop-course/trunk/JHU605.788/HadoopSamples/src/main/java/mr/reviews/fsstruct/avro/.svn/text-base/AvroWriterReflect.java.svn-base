package mr.reviews.fsstruct.avro;

import java.io.File;
import java.io.OutputStream;

import mr.reviews.fsstruct.support.ConfHelper;
import mr.reviews.fsstruct.support.FsHelper;
import mr.reviews.fsstruct.support.Review;
import mr.reviews.fsstruct.support.XmlHelper;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvroWriterReflect extends Configured implements Tool {
    private static Logger LOG = LoggerFactory.getLogger(AvroWriterReflect.class);
    @Override
    public int run(String[] args) throws Exception {
        File schemaFile = new File(args[0]);
        ConfHelper confHelper = new ConfHelper(getConf());
        Path inputDir = confHelper.getInput();
        Path outFile = confHelper.getOutputPath();

        FileSystem fs = FileSystem.get(getConf());
        FsHelper fsHelper = new FsHelper(getConf());
        XmlHelper xmlHelper = new XmlHelper();
        
        OutputStream outStream = null;
        DataFileWriter<Review> writer = null;
        try {
            outStream = fs.create(outFile, false);

            Schema schema = new Schema.Parser().parse(schemaFile);
            writer = new DataFileWriter<Review>(new ReflectDatumWriter<Review>());
            writer.setCodec(CodecFactory.snappyCodec());
            writer.create(schema, outStream);
            
            for (FileStatus fStatus : fs.listStatus(inputDir)) {
                LOG.info("Converting [{}] to avro", fStatus.getPath());
                byte[] bytes = fsHelper.readBytes(fStatus.getPath());
                Review review = xmlHelper.convert(bytes);
                writer.append(review);
            }

        } finally {
            IOUtils.closeStream(writer);
            IOUtils.closeStream(outStream);
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new AvroWriterReflect(), args);
        System.exit(code);
    }
}
