package mr.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class StartsWithCountMapper_DistCache extends Mapper<LongWritable, Text, Text, IntWritable> {
    protected Logger log = Logger.getLogger(StartsWithCountMapper_DistCache.class);
    private final static IntWritable countOne = new IntWritable(1);
    private final Text reusableText = new Text();

    public final static String EXCLUDE_FILE = "startWithExcludeFile.txt";
    protected final Set<String> excludeSet = new HashSet<String>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileReader reader = new FileReader(new File(EXCLUDE_FILE));
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

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        StringTokenizer tokenizer = new StringTokenizer(value.toString());

        while (tokenizer.hasMoreTokens()) {
            String firstLetter = tokenizer.nextToken().substring(0, 1);

            if (!excludeSet.contains(firstLetter)) {
                reusableText.set(firstLetter);
                context.write(reusableText, countOne);
            }
        }
    }
}
