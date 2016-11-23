package mr.reviews.fsstruct.mf;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile.Reader;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MfReader extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        // skipping validation for clarity
        Path from = new Path(args[0]);
        int maxRecordsToRead = 10;
        if (args.length > 1) {
            maxRecordsToRead = Integer.parseInt(args[1]);
        }

        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(from), "From directory does not exist");

        int i = 0;
        Reader reader = null;
        try {
            reader = new Reader(from, getConf());
            WritableComparable<?> key = (WritableComparable<?>) ReflectionUtils.newInstance(reader.getKeyClass(), getConf());
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), getConf());
            readerLoop: while (reader.next(key, value)) {
                printToScreen(key, value);
                i+=1;
                if (i > maxRecordsToRead) {
                    break readerLoop;
                }
            }
            
        } finally {
            reader.close();
        }

        return 0;
    }

    private void printToScreen(Writable key, Writable value) {
        System.out.println("------------------");
        System.out.println("key=" + key);
        System.out.println("value=" + value);
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new MfReader(), args);
        System.exit(code);
    }
}
