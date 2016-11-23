package mapRed.workflows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AverageByLetterTests {

	private File inputFile = 
			new File("./target/test/input.txt");
	private File output = new File("./target/test-result/");

	@Before
	public void setUpTest() throws IOException{
		FileUtils.deleteQuietly(inputFile);
		FileUtils.deleteQuietly(output);
		FileUtils.writeStringToFile(inputFile, 
				"one\t1\n"+
				"owner\t5\n"+
				"blue\t8\n"+
				"band\t13\n"+
				"one\t10\n"+
				"back\t27\n"+				
				"clamb\t9\n"
		);
	}

	@After
	public void tearDown() throws IOException{
		FileUtils.deleteQuietly(inputFile);
		FileUtils.deleteQuietly(output);
	}
	
	@Test
	public void test() throws Exception {
		Configuration conf = new Configuration();
		conf.set("mapreduce.framework.name", "local");
		conf.set("fs.default.name", "file:///");
		
		AverageByLetter underTest = new AverageByLetter();
		underTest.setConf(conf);
		
		int exitCode = underTest.run(new 		
								String[]{inputFile.getAbsolutePath(), 
								output.getAbsolutePath()});
		assertEquals("Returned error code.", 0, exitCode);
		assertTrue(new File(output, "_SUCCESS").exists());
		
		String contentOfFile = FileUtils.readFileToString(new File(output, "part-r-00000"));
		Map<String,Integer> resultAsMap = getResultAsMap(contentOfFile);
		
		assertEquals(3, resultAsMap.size());
	    assertEquals(5, resultAsMap.get("o").intValue());
	    assertEquals(16, resultAsMap.get("b").intValue());
	    assertEquals(9, resultAsMap.get("c").intValue());
	}

	private Map<String, Integer> getResultAsMap(String contentOfFile) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		for ( String line : contentOfFile.split("\n")){
			String [] vals = line.split("\t");
			String label = vals[0].trim();
			Integer count = Integer.parseInt(vals[1].trim());
			result.put(label, count);
		}
		return result;
	}
}
