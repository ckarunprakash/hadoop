package mapRed.runningJobs;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ExpectProperty extends Configured implements Tool{

	public final static String PROPERTY_TO_EXPECT = "training.prop";
	@Override
	public int run(String[] args) throws Exception {
		String prop = getConf().get(PROPERTY_TO_EXPECT);
		if ( prop == null || prop.trim().length() == 0){
			throw new IllegalArgumentException("Expected property [" + PROPERTY_TO_EXPECT + "] to be provided");
		}
		
		System.out.println("Property [" + PROPERTY_TO_EXPECT + "] is set to [" + prop + "]");
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ExpectProperty(), args);
		System.exit(exitCode);
	}
}
