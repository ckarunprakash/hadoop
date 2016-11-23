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
import org.apache.hadoop.util.Progressable;

public class Exercise2 {
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		FileSystem hdfs = FileSystem.get(new Configuration());
		FileSystem local = FileSystem.get(new URI("file:///"), new Configuration());
		Path localGlob = new Path("/home/hadoop/Training/play_area/exercises/filesystem/e2/*.txt");
		Path hdfsRoot = new Path("/training/playArea/filesystem/e2");
		hdfs.mkdirs(hdfsRoot);
		
		FileStatus [] files = local.globStatus(localGlob);
		for (FileStatus file : files ){
			Path from = file.getPath();
			Path to = new Path(hdfsRoot, file.getPath().getName());
			copy(local, from, hdfs, to);
		}
	}
	
	private static void copy(FileSystem fromFs, Path fromPath, FileSystem toFs, Path toPath) throws IOException {
		System.out.println("Copying [" + fromPath + "] to [" + toPath + "]");
		OutputStream out = null;
		InputStream in = null;
		try {
			in = fromFs.open(fromPath);
			out = toFs.create(toPath, new Progressable() {
			    @Override
			    public void progress() {
			        System.out.print("..");
			    }
			});
			IOUtils.copyBytes(in, out, 10, false);
		} finally {
			IOUtils.closeStream(out);
			IOUtils.closeStream(in);
		}
		System.out.print("\n");
	}
}
