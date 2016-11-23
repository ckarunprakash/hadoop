package mr.reviews.fsstruct.mf;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapFile.Reader;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MfByKeyReader extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        // skipping validation for clarity
        Path from = new Path(args[0]);
        String keyInput = args[1];

        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(from));

        WritableComparable<?> foundKey = null;
        Writable foundValue = null;
        Reader reader = null;
        try {
            reader = new Reader(from, getConf());

            LongWritable key = new LongWritable(Long.parseLong(keyInput));
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), getConf());

            System.out.println("-----[reader.get(key, value)]-----");
            foundValue = reader.get(key, value);
            printToScreen(key, foundValue);

            System.out.println("-----[reader.getClosest(key, value)]-----");
            foundKey = reader.getClosest(key, value);
            printToScreen(foundKey, value);

            System.out.println("-----[reader.getClosest(key, value, true)]-----");
            foundKey = reader.getClosest(key, value, true);
            printToScreen(foundKey, value);

        } finally {
            reader.close();
        }

        return 0;
    }

    private void printToScreen(Writable key, Writable value) {
        if (key == null) {
            System.out.println("No value found");
        } else {
            System.out.println("key=" + key);
            System.out.println("value=" + value + "\n");
        }
    }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new MfByKeyReader(), args);
        System.exit(code);
    }
}
