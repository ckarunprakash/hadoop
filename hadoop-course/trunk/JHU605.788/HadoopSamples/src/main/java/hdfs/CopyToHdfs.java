package hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class CopyToHdfs {

	public static void main(String[] args) throws IOException {
		FileSystem fs = FileSystem.get(new Configuration());
		fs.copyFromLocalFile(new Path(args[0]), new Path(args[1]));
	}

}
