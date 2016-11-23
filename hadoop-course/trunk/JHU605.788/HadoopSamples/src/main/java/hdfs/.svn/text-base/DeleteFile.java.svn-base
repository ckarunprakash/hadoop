package hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class DeleteFile {
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path toDelete = new Path("/training/playArea/writeMe.txt");
		boolean isDeleted = fs.delete(toDelete, false);
		System.out.println("Deleted: " + isDeleted);
	}
}
