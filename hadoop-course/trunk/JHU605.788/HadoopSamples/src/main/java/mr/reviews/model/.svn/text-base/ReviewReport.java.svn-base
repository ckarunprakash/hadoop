package mr.reviews.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.hadoop.io.Writable;

public class ReviewReport implements Writable{

    private int numReviews;
    private String keyword;
    private String author;
    private String fullReport;

    public ReviewReport() {

    }

    public ReviewReport(int numReviews, String keyword, String author, String fullReport) {
        super();
        this.numReviews = numReviews;
        this.keyword = keyword;
        this.author = author;
        this.fullReport = fullReport;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFullReport() {
        return fullReport;
    }

    public void setFullReport(String fullReport) {
        this.fullReport = fullReport;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(numReviews);
        out.writeUTF(keyword);
        out.writeUTF(author);
        out.writeUTF(fullReport);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        numReviews = in.readInt();
        keyword = in.readUTF();
        author = in.readUTF();
        fullReport = in.readUTF();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
