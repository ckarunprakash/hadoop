package hdfs;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class ReadFile {

	public static void main(String[] args) throws IOException {
		Path fileToRead = new Path("/training/data/readMe.txt");
		FileSystem fs = FileSystem.get(new Configuration());
		InputStream input = null;
		try {
			input = fs.open(fileToRead);
			IOUtils.copyBytes(input, System.out, 4096);
		} finally {
			IOUtils.closeStream(input);
		}
	}

}
