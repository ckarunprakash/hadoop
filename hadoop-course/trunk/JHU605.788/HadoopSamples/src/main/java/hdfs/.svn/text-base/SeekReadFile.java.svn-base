package hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class SeekReadFile {

	public static void main(String[] args) throws IOException {
		Path fileToRead = new Path("/training/data/readMe.txt");
		FileSystem fs = FileSystem.get(new Configuration());
		FSDataInputStream input = null;
		try {
			input = fs.open(fileToRead);
			System.out.print("start postion=" + input.getPos() + ": ");
			IOUtils.copyBytes(input, System.out, 4096, false);
			input.seek(11);
			System.out.print("start postion=" + input.getPos() + ": ");
			IOUtils.copyBytes(input, System.out, 4096, false);
			input.seek(0);
			System.out.print("start postion=" + input.getPos() + ": ");
			IOUtils.copyBytes(input, System.out, 4096, false);
		} finally {
			IOUtils.closeStream(input);
		}
	}

}
