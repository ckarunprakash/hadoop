package mr.reviews.fsstruct.seq;

import mr.reviews.fsstruct.support.ConfHelper;
import mr.reviews.fsstruct.support.FsHelper;
import mr.reviews.fsstruct.support.Review;
import mr.reviews.fsstruct.support.XmlHelper;
import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ReviewSequenceFileWriter extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        ConfHelper confHelper = new ConfHelper(getConf());
        Path inputDir = confHelper.getInput();
        Path outputSeqFile = confHelper.getOutputPath() ;

        LongWritable key = new LongWritable();
        ReviewWritable value = new ReviewWritable();

        long count = 0;
        Writer writer = null;
        try {
            writer = SequenceFile.createWriter(getConf(), Writer.file(outputSeqFile),
                    Writer.compression(CompressionType.BLOCK, new SnappyCodec()), Writer.keyClass(key.getClass()),
                    Writer.valueClass(value.getClass()));
            
            FileSystem fs = FileSystem.get(getConf());
            FsHelper fsHelper = new FsHelper(getConf());
            XmlHelper xmlHelper = new XmlHelper();
            
            for ( FileStatus fStatus : fs.listStatus(inputDir)){
                byte [] bytes = fsHelper.readBytes(fStatus.getPath());
                Review review = xmlHelper.convert(bytes);
                populateWritable(value, review);
                key.set(count++);
                writer.append(key, value);
            }
        } finally {
            if ( writer!=null){
                writer.close();
            }
        }

        return 0;
    }

    private void populateWritable(ReviewWritable value, Review review) {
        value.setAuthor(review.getUser());
        value.setContent(review.getText());
        value.setPostedTimestamp(review.getTimestamp());
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new ReviewSequenceFileWriter(), args);
        System.exit(code);
    }
}
