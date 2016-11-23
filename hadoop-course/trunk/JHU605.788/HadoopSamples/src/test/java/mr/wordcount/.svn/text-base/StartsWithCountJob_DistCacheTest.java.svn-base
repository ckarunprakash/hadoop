package mr.wordcount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class StartsWithCountJob_DistCacheTest {
    
    private File inputFile =
      new File("./target/test/input.txt");
    private File output = new File("./target/test-result/");
    private File distCacheFile = 
      new File("./target/" + StartsWithCountMapper_DistCache.EXCLUDE_FILE);
    
    @Before
    public void setUpTest() throws IOException{
      FileUtils.deleteQuietly(inputFile);
      FileUtils.deleteQuietly(output);
      FileUtils.writeStringToFile(inputFile,
              "this is just a test, yes it is");
      if(distCacheFile.exists()){
          FileUtils.forceDelete(distCacheFile);
      }
      FileUtils.write(distCacheFile, "a");
    }
    
    @Test
    public void testWithLocalJobRunner() throws Exception {
      Configuration conf = new Configuration();
      conf.set("mapreduce.framework.name", "local");
      conf.set("fs.defaultFS", "file:///");
      DistributedCache.addCacheFile(distCacheFile.toURI(), conf);
      
      StartsWithCountJob_DistCache underTest = new StartsWithCountJob_DistCache();
      underTest.setConf(conf);
      
        
      int exitCode = underTest.run(
        new String[]{inputFile.getAbsolutePath(),
            output.getAbsolutePath()});
      assertEquals("Returned error code.", 0, exitCode);
      assertTrue(new File(output, "_SUCCESS").exists());
      Map<String,Integer> resAsMap =
        getResultAsMap(new File(output, "part-r-00000"));
        
      assertEquals(4, resAsMap.size());
      assertEquals(2, resAsMap.get("t").intValue());
      assertEquals(3, resAsMap.get("i").intValue());
      assertEquals(1, resAsMap.get("j").intValue());
      assertEquals(1, resAsMap.get("y").intValue());
    }
    private Map<String, Integer> getResultAsMap(File file) 
            throws IOException {

      Map<String, Integer> result = new HashMap<String, Integer>();
      String contentOfFile = FileUtils.readFileToString(file);
      for (String line : contentOfFile.split("\n")){
        String [] tokens = line.split("\t");
        result.put(tokens[0], Integer.parseInt(tokens[1]));
      }
      return result;
    }
}