package utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FsUtil {
    private final static Logger LOG = LoggerFactory.getLogger(FsUtil.class);
    
    public static void delete(Configuration conf, Path dir) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        try {
            delete(fs, dir);
        } finally {
            fs.close();
        }
    }
    
    public static void delete(FileSystem fs, Path dir) throws IOException {
        if (fs.exists(dir)) {
            fs.delete(dir, true);
            LOG.info("Deleted directory [{}]", dir);
        }
    }
}
