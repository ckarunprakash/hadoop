package mr.reviews.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.hadoop.io.Writable;

public class ReviewWritable implements Writable{

    private String author;
    private String content;
    private Long postedTimestamp;

    public ReviewWritable() {
    }

    public ReviewWritable(String author, String content, Long posted) {
        this.setAuthor(author);
        this.setContent(content);
        this.setPostedTimestamp(posted);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        author = input.readUTF();
        content = input.readUTF();
        postedTimestamp = input.readLong();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(author);
        output.writeUTF(content);
        output.writeLong(postedTimestamp);
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostedTimestamp() {
        return postedTimestamp;
    }

    public void setPostedTimestamp(Long postedTimestamp) {
        this.postedTimestamp = postedTimestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
