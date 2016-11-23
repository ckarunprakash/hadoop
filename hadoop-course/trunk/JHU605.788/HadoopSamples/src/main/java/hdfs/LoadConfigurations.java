package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

public class LoadConfigurations {
	private final static String PROP_NAME = "fs.defaultFS"; 
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		System.out.println("After construction: " + conf.get(PROP_NAME));
		conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
		System.out.println("After addResource: " + conf.get(PROP_NAME));
		conf.set(PROP_NAME, "hdfs://localhost:8111");
		System.out.println("After set: " + conf.get(PROP_NAME));
	}

}
