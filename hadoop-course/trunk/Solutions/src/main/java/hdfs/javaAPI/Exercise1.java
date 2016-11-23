package hdfs.javaAPI;


import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Exercise1 {
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		Path glob = new Path("/training/exercises/filesystem/*.txt");
		String localRoot = "/home/hadoop/Training/play_area/exercises/filesystem/e1/";
		
		FileSystem hdfs = FileSystem.get(new Configuration());
		FileStatus [] files = hdfs.globStatus(glob);
		for (FileStatus file : files ){
			Path from = file.getPath();
			Path to = new Path(localRoot, file.getPath().getName());
			System.out.println("Copying hdfs file [" + from + "] to local [" + to + "]");
			hdfs.copyToLocalFile(from, to);
		}
	}
}
