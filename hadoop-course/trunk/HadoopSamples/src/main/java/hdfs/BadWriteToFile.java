package hdfs;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class BadWriteToFile {

	public static void main(String[] args) throws IOException {
		String textToWrite = "Hello HDFS! Elephants are awesome!";
		InputStream in = new BufferedInputStream(new ByteArrayInputStream(textToWrite.getBytes()));
		
		Configuration conf = new Configuration();
		Path toHdfs = new Path("/training/playArea/writeMe.txt");
		FileSystem fs = FileSystem.get(conf);
		FSDataOutputStream out = fs.create(toHdfs, false);
		IOUtils.copyBytes(in, out, conf);
	}
}
