package hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class SimpleGlobbing {
	public static void main(String[] args) throws IOException {
		Path glob = new Path(args[0]);
		FileSystem fs = FileSystem.get(new Configuration());
		FileStatus[] files = fs.globStatus(glob);
		for (FileStatus file : files) {
			System.out.println(file.getPath().getName());
		}
	}
}
