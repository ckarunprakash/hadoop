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
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**

yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.avro.AvroWriterGeneric -Dreport.input.path=examples_input/reviews-xml/ -Dreport.output.path=examples_input/nullTests/reviews.avro $TRAINING_HOME/eclipse/workspace/HadoopSamples/src/main/resources/mr/reviews/fsstruct/avro/model/review.avsc
 *
 */
public class AvroWriterGeneric extends Configured implements Tool {
    private static Logger LOG = LoggerFactory.getLogger(AvroWriterGeneric.class);
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
        DataFileWriter<GenericRecord> writer = null;
        try {
            outStream = fs.create(outFile, false);

            Schema schema = new Schema.Parser().parse(schemaFile);
            writer = new DataFileWriter<GenericRecord>(new GenericDatumWriter<GenericRecord>());
            writer.setCodec(CodecFactory.snappyCodec());
            writer.create(schema, outStream);
            
            GenericRecord record = new GenericData.Record(schema);
            
            for (FileStatus fStatus : fs.listStatus(inputDir)) {
                LOG.info("Converting [{}] to avro", fStatus.getPath());
                
                byte[] bytes = fsHelper.readBytes(fStatus.getPath());
                Review review = xmlHelper.convert(bytes);
                record.put("user", review.getUser());
                record.put("text", review.getText());
                record.put("timestamp", review.getTimestamp());
                writer.append(record);
            }

        } finally {
            IOUtils.closeStream(writer);
            IOUtils.closeStream(outStream);
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new AvroWriterGeneric(), args);
        System.exit(code);
    }
}
