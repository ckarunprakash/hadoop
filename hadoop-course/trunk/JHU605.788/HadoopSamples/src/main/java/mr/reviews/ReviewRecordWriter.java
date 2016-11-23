package mr.reviews;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewReport;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewRecordWriter extends RecordWriter<ReviewKeyWritable, ReviewReport>{
    private static Logger LOG = LoggerFactory.getLogger(ReviewRecordWriter.class);
    private Path mrRootPath;
    private FileSystem fs;
    private Configuration conf;
    
    public ReviewRecordWriter(Configuration conf, Path mrRootPath) throws IOException{
        this.mrRootPath = mrRootPath;
        this.conf = conf;
        this.fs = FileSystem.get(conf);
    }
    
    @Override
    public void write(ReviewKeyWritable key, ReviewReport review) throws IOException, InterruptedException {
        Path filePath = new Path(mrRootPath, key.getAuthor() + "_" + key.getKeyword() + "-" + review.getNumReviews() + ".txt");
        LOG.info("Opening new file to [{}]", filePath);
        FSDataOutputStream out = fs.create(filePath);
        try {
            InputStream in = new BufferedInputStream(
                  new ByteArrayInputStream(review.getFullReport().getBytes()));
            IOUtils.copyBytes(in, out, conf);
            IOUtils.closeStream(in);
        } finally {
            IOUtils.closeStream(out);
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        fs.close();
    }
}