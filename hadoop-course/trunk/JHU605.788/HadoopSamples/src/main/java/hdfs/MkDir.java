package hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class MkDir {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		Path newDir = new Path("/training/playArea/newDir");
		FileSystem fs = FileSystem.get(conf);
		boolean created = fs.mkdirs(newDir);
		System.out.println(created);
	}

}
