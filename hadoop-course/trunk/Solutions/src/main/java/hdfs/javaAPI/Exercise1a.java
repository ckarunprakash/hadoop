package hdfs.javaAPI;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class Exercise1a {
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		Path glob = new Path("/training/exercises/filesystem/*.txt");
		String localRoot = "/home/hadoop/Training/play_area/exercises/filesystem/e1/";
		
		FileSystem hdfs = FileSystem.get(new Configuration());
		FileSystem localFs = FileSystem.get(new URI("file:///"), new Configuration());
		
		FileStatus [] files = hdfs.globStatus(glob);
		for (FileStatus file : files ){
			copyToLocal(hdfs, localFs, file.getPath(), 
					new Path(localRoot + file.getPath().getName()));
		}
	}

	private static void copyToLocal(FileSystem fromFs, FileSystem toFs, Path fromPath, Path toPath) throws IOException {
		System.out.println("Copying [" + fromPath + "] to [" + toPath + "]");
		OutputStream out = null;
		InputStream in = null;
		try {
			in = fromFs.open(fromPath);
			out = toFs.create(toPath);
			IOUtils.copyBytes(in, out, 10, false);
		} finally {
			IOUtils.closeStream(out);
			IOUtils.closeStream(in);
		}
	}
}
