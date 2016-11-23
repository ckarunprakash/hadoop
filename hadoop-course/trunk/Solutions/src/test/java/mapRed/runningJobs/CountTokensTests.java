package mapRed.runningJobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
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
		Configuration conf = new Configuration();
		conf.set("mapreduce.framework.name", "local");
		conf.set("fs.default.name", "file:///");
		
		CountTokens underTest = new CountTokens();
		underTest.setConf(conf);
		
		int exitCode = underTest.run(new 		
								String[]{inputFile.getAbsolutePath(), 
								output.getAbsolutePath()});
		assertEquals("Returned error code.", 0, exitCode);
		assertTrue(new File(output, "_SUCCESS").exists());
		
		String contentOfFile = FileUtils.readFileToString(new File(output, "part-r-00000"));
		String [] vals = contentOfFile.split("\t");
		String label = vals[0].trim();
		Integer count = Integer.parseInt(vals[1].trim());
	    assertEquals("count", label);
		assertEquals(9, count.intValue());
	}
}
