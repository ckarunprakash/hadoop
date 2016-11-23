package hdfs;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * Copy a context of a file into a new file several times to increase the size. 
 */
public class GenerateABigFile {

	public static void main(String[] args) throws IOException {
		String fromFile = args[0];
		String toFile = args[1];
		int numTimeToCopy = Integer.parseInt(args[2]);
		
		Path fileToRead = new Path(fromFile);
		Path fileToWrite = new Path(toFile);
		
		FileSystem fs = FileSystem.get(new Configuration());
		
		fs.create(fileToWrite).close();
		
		for ( int i = 0; i < numTimeToCopy; i++){
			System.out.println("Copy #" + i);
			InputStream input = null;
			FSDataOutputStream out = null; 
			try {
				input = fs.open(fileToRead);
				out = fs.append(fileToWrite);
				
				IOUtils.copyBytes(input, out, 4096, false);
			} finally {
				IOUtils.closeStream(input);
				IOUtils.closeStream(out);
			}
		}
		
	}

}
