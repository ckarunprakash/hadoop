package mr.reviews.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.hadoop.io.WritableComparable;

public class ReviewKeyWritable implements WritableComparable<ReviewKeyWritable> {

    private String author;
    private String keyword;

    public ReviewKeyWritable() {
    }

    public ReviewKeyWritable(String author, String keyword) {
        this.setAuthor(author);
        this.setKeyword(keyword);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        author = input.readUTF();
        keyword = input.readUTF();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(author);
        output.writeUTF(keyword);
    }

    @Override
    public int compareTo(ReviewKeyWritable other) {
        return new CompareToBuilder()
               .append(this.author, other.author)
               .append(this.keyword, other.keyword)
               .toComparison();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
