package mr.reviews.fsstruct.support;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfHelper {
    private static Logger LOG = LoggerFactory.getLogger(ConfHelper.class); 
    public final static String PROP_FIND_VALUE  = "report.value";
    public final static String PROP_INPUT_PATH = "report.input.path";
    public final static String PROP_OUTPUT_PATH = "report.output.path";
    
    private final Configuration conf;
    public ConfHelper(Configuration conf){
        this.conf = conf;
    }
    
    public Path getInput() throws IOException {
        String input = conf.get(PROP_INPUT_PATH);
        Validate.notEmpty(input, "Must provide input path via [" + PROP_INPUT_PATH + "] property");
        Path path =  new Path(input);
        FileSystem fs = FileSystem.get(conf);
        Validate.notNull(fs, "Couldn't create file system");
        Validate.isTrue(fs.exists(path), "The input directory [" + path + "] does not exist");
        Validate.isTrue(fs.globStatus(path).length>0, "The input directory [" + path + "] is empty");
        LOG.info("Input is [{}]", path);
        return path;
    }

    public Path getOutputPath() throws IOException {
        String out = conf.get(PROP_OUTPUT_PATH);
        Validate.notEmpty(out, "You must provide value to find via [" + PROP_OUTPUT_PATH + "]");
        Path outPath = new Path(out);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(outPath)){
            fs.delete(outPath, true);
            LOG.info("Removed output [" + outPath + "]");
        }
        fs.close();
        return outPath;
    }
    public List<String> getSearchTerms(){
        String value = conf.get(PROP_FIND_VALUE);
        Validate.notEmpty(value, "You must provide value to find via [" + PROP_FIND_VALUE + "]");
        return Arrays.asList(value.split(","));
    }
   
}
