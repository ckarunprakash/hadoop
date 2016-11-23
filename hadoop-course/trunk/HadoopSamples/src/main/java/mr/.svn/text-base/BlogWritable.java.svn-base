package mr;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class BlogWritable implements WritableComparable<BlogWritable> {

	private String author;
	private String content;
	public BlogWritable(){}
	public BlogWritable(String author, String content) {
		this.author = author;
		this.content = content;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		author = input.readUTF();
		content = input.readUTF();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(author);
		output.writeUTF(content);
	}

	@Override
	public int compareTo(BlogWritable other) {
		return author.compareTo(other.author);
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	public static void main(String[] args) throws IOException {
		BlogWritable origWritable = new BlogWritable("author", "content");
		
		DataOutputBuffer b1 = writeWritable(origWritable);
		DataInputBuffer in = new DataInputBuffer();
		in.reset(b1.getData(), b1.getLength());

		BlogWritable result = new BlogWritable();
		result.readFields(in);
		System.out.println(result.getAuthor());
		System.out.println(result.getContent());
	}

	protected static DataOutputBuffer writeWritable(Writable writable)
			throws IOException {
		DataOutputBuffer out = new DataOutputBuffer(1024);
		writable.write(out);
		out.flush();
		return out;
	}

}
