package compression;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;

public class CompressHelper {

    private final Configuration conf;
    private final CompressionCodecFactory codecFactory;
    private final FileSystem fs;
    public CompressHelper(Configuration conf) throws IOException{
        Validate.notNull(conf);
        this.conf = conf;
        this.fs = FileSystem.get(conf);
        this.codecFactory = new CompressionCodecFactory(conf);
    }
    public void compress(Path from, Path to, CompressionCodec codec) throws IOException{
        FileSystem fs = FileSystem.get(conf);
        InputStream input = null;
        FSDataOutputStream out = null;
        CompressionOutputStream compressedOut = null;
        try {
            out = fs.create(to);
            compressedOut = codec.createOutputStream(out);
            input = fs.open(from);
            IOUtils.copyBytes(input, compressedOut, 4096);
        } finally {
            IOUtils.closeStream(input);
            IOUtils.closeStream(compressedOut);
            IOUtils.closeStream(out);
        }
    }
    
    public void decompress(Path from, Path to) throws IOException{
        CompressionCodec codec = codecFactory.getCodec(from);
        Validate.notNull(codec, "Could not infer codec from [" + from + "]");
        InputStream in = null;
        FSDataOutputStream out = null;
        CompressionInputStream uncompressedIn = null;
        try {
            in = fs.open(from);
            uncompressedIn = codec.createInputStream(in);
            out = fs.create(to);
            IOUtils.copyBytes(uncompressedIn, out, 4096);
        } finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(uncompressedIn);
            IOUtils.closeStream(out);
        }
    }
}
