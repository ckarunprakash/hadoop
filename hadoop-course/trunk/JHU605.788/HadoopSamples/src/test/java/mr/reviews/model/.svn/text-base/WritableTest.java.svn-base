package mr.reviews.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.Writable;
import org.junit.Test;

public class WritableTest {

    @Test
    public void testReviewWritableSerDer() throws IOException {
        String author = "author";
        String content = "content";
        Long posted = 100000000l;
        
        ReviewWritable origWritable = new ReviewWritable(author, content, posted);
        
        DataOutputBuffer b1 = writeWritable(origWritable);
        DataInputBuffer in = new DataInputBuffer();
        in.reset(b1.getData(), b1.getLength());

        ReviewWritable result = new ReviewWritable();
        result.readFields(in);
        assertEquals(author, result.getAuthor());
        assertEquals(content, result.getContent());
        assertEquals(posted, result.getPostedTimestamp());
    }
    
    @Test
    public void testReviewKeyWritableSerDer() throws IOException {
        String author = "author";
        String keyword = "keyword";
        
        ReviewKeyWritable origWritable = new ReviewKeyWritable(author,keyword);
        
        DataOutputBuffer b1 = writeWritable(origWritable);
        DataInputBuffer in = new DataInputBuffer();
        in.reset(b1.getData(), b1.getLength());

        ReviewKeyWritable result = new ReviewKeyWritable();
        result.readFields(in);
        assertEquals(author, result.getAuthor());
        assertEquals(keyword, result.getKeyword());
    }
    
    protected DataOutputBuffer writeWritable(Writable writable)
            throws IOException {
        DataOutputBuffer out = new DataOutputBuffer(1024);
        writable.write(out);
        out.flush();
        return out;
    }

}
