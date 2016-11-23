package mr.wordcount;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class StartsWithCountMapper_DistCache1 extends StartsWithCountMapper_DistCache{
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] cachedUris = context.getCacheFiles();
        URI excludeFileUri = null;
        if(ArrayUtils.isNotEmpty(cachedUris)){
            for(URI cachedUri : cachedUris){
                if(cachedUri.getPath().endsWith(EXCLUDE_FILE)){
                    excludeFileUri = cachedUri;
                }
            }
        }
        Validate.notNull(excludeFileUri, "Failed to find [" + EXCLUDE_FILE + "] on dist cache");
        FileReader reader = new FileReader(new File(excludeFileUri));
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                excludeSet.add(line);
                log.info("Ignoring words that start with [" + line + "]");
            }
            
        } finally {
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(reader);
        }
    }
}
