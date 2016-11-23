package mr.reviews.fsstruct.avro;

import java.io.OutputStream;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.support.ConfHelper;
import mr.reviews.fsstruct.support.FsHelper;
import mr.reviews.fsstruct.support.Review;
import mr.reviews.fsstruct.support.XmlHelper;

import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvroWriterSpecific extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        ConfHelper confHelper = new ConfHelper(getConf());
        Path inputDir = confHelper.getInput();
        Path outFile = confHelper.getOutputPath();

        FileSystem fs = FileSystem.get(getConf());
        FsHelper fsHelper = new FsHelper(getConf());
        XmlHelper xmlHelper = new XmlHelper();
        
        OutputStream outStream = null;
        DataFileWriter<ReviewAvro> writer = null;
        try {
            outStream = fs.create(outFile, false);

            DatumWriter<ReviewAvro> userDatumWriter = new SpecificDatumWriter<ReviewAvro>(ReviewAvro.class);
            writer = new DataFileWriter<ReviewAvro>(userDatumWriter);
            writer.setCodec(CodecFactory.snappyCodec());
            writer.create(ReviewAvro.SCHEMA$, outStream);
            for (FileStatus fStatus : fs.listStatus(inputDir)) {
                byte[] bytes = fsHelper.readBytes(fStatus.getPath());
                ReviewAvro reviewAvro = createReviewAvro(xmlHelper.convert(bytes));
                writer.append(reviewAvro);
            }

        } finally {
            writer.close();
            outStream.close();
        }
        return 0;
    }

    private ReviewAvro createReviewAvro(Review review) {
        return ReviewAvro.newBuilder()
                .setUser(review.getUser())
                .setText(review.getText())
                .setTimestamp(review.getTimestamp())
                .build();
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new AvroWriterSpecific(), args);
        System.exit(code);
    }
}
