package common;

import java.util.Map;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class PropPrinter extends Configured implements Tool{

	@Override
	public int run(String[] args) throws Exception {
		for ( Map.Entry<String,String> entry : getConf()){
			System.out.println(entry.getKey() + "=>" + entry.getValue());
		}
		return 0;
	}
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new PropPrinter(), args);
		System.exit(exitCode);
	}
}
