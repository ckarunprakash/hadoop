package compression;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * You can try this class with something like this:
 * 
 * $ echo "some test" | yarn jar $PLAY_AREA/HadoopSamples.jar compression.SimpleCompression org.apache.hadoop.io.compress.GzipCodec | gunzip -
 *
 */
public class SimpleCompression extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        String codeClass = args[0];

        Class<?> codecClazz = Class.forName(codeClass);
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClazz, getConf());
        CompressionOutputStream out = codec.createOutputStream(System.out);
        IOUtils.copyBytes(System.in, out, 4096, false);
        out.finish();
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new SimpleCompression(), args);
        System.exit(exitCode);
    }

}
