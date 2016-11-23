package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class SimpleLs {

	public static void main(String[] args) throws Exception{
		Path path = new Path("/");
		if ( args.length == 1){
			path = new Path(args[0]);
		}
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		FileStatus [] files = fs.listStatus(path);
		
		for (FileStatus file : files ){
			System.out.println(file.getPath().getName());
		}
	}
}
