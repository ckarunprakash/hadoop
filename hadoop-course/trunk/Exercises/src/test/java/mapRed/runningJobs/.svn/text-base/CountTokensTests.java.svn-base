package mapRed.runningJobs;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class CountTokensTests {

	private File inputFile = 
			new File("./target/test/input.txt");
	private File output = new File("./target/test-result/");

	@Before
	public void setUpTest() throws IOException{
		FileUtils.deleteQuietly(inputFile);
		FileUtils.deleteQuietly(output);
		FileUtils.writeStringToFile(inputFile, 
							"one two\nthree four\nfive\nsix seven\neight nine");
	}

	@Test
	public void test() throws Exception {
		// put your unit test here
	}
}
