package mapRed.runningJobs;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import common.PropPrinter;

public class ExpectClassOnClient extends Configured implements Tool{

	@SuppressWarnings("unused")
	@Override
	public int run(String[] args) throws Exception {
		PropPrinter classFromSampleJar = new PropPrinter();
		System.out.println("Class [" + PropPrinter.class + "] was on CLASSPATH");
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ExpectClassOnClient(), args);
		System.exit(exitCode);
	}
}
