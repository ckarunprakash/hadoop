package utils;

import java.io.File;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configuration;

public class ConfUtil {

    public static File getFile(Configuration conf, String prop){
        Validate.notEmpty(prop, "prop parameters must NOT be null or empty");
        Validate.notNull(conf, "conf parameters must NOT be null");
        String fileName= conf.get(prop);
        Validate.notNull(fileName, "Must provide file name via [" + prop + "] property");
        File fileResult = new File(fileName);
        Validate.isTrue(fileResult.exists(), "Must place [" + fileName + "] on Distributed Cache");
        return fileResult;
    }
    
}
