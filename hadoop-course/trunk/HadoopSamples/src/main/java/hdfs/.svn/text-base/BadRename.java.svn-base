package hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class BadRename {

	public static void main(String[] args) throws IOException {
		FileSystem fs = FileSystem.get(new Configuration());
		Path source = new Path("/does/not/exist/file.txt");
		Path nonExistentPath = new Path("/does/not/exist/file1.txt");
		boolean result = fs.rename(source, nonExistentPath);
		System.out.println("Rename: " + result);
	}

}
