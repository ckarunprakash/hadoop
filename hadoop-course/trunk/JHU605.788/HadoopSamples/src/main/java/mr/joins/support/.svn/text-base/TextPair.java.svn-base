package mr.joins.support;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import com.google.common.collect.ComparisonChain;

public class TextPair implements WritableComparable<TextPair>{
    private Text first = new Text();
    private Text second = new Text();
    @Override
    public void write(DataOutput out) throws IOException {
        first.write(out);
        second.write(out);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        first.readFields(in);
        second.readFields(in);
    }
    @Override
    public int compareTo(TextPair o) {
        return ComparisonChain.start().
                compare(first, o.first).
                compare(second, o.second).
                result();
    }
    public Text getFirst() {
        return first;
    }
    public void setFirst(Text first) {
        this.first = first;
    }
    public Text getSecond() {
        return second;
    }
    public void setSecond(Text second) {
        this.second = second;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
