package compression;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.StopWatch;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.io.compress.Lz4Codec;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import various.BuildLargeTextFile;

public class CompareCompression extends Configured implements Tool{

    @Override
    public int run(String[] args) throws Exception {
        if ( args.length != 1){
            System.out.println(BuildLargeTextFile.class.getSimpleName() + " <file_to_compress");
            System.exit(-1);
        }
        Path from = new Path(args[0]);
        Path toRoot = new Path(from.getParent(), "compressedTest");

        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(from));
        Validate.isTrue(fs.isFile(from));
        if ( fs.exists(toRoot)){
            fs.delete(toRoot, true);
            fs.mkdirs(toRoot);
        }
        
        int mbSize = (int)(fs.getFileStatus(from).getLen()/1024d/1024d);
        System.out.println("Compressing/Decompressing file [" + from + "] of size [~" + mbSize + "MB]" );

        CompressionCodec [] codecs = createCodecs();
        CompressHelper compressHelper = new CompressHelper(getConf());
        System.out.println("\nCompression:");
        for (CompressionCodec codec : codecs){
            Path toPath = new Path(toRoot, "compressed" + codec.getDefaultExtension());
            StopWatch sw = new StopWatch();
            sw.start();
            compressHelper.compress(from, toPath, codec);
            sw.stop();
            System.out.println(codec.getClass().getSimpleName() + ": " + sw.getTime() + " millis");
        }
        printFileSizes(fs, toRoot);
        System.out.println("\nDecompression:");
        for (FileStatus fsCompPath : fs.listStatus(toRoot)){
            Path compPath = fsCompPath.getPath();
            Path destPath =  new Path(compPath.getParent(), compPath.getName() + "-decompressed");
            StopWatch sw = new StopWatch();
            sw.start();
            compressHelper.decompress(compPath, destPath);
            sw.stop();
            System.out.println(compPath.getName() + ": " + sw.getTime() + " millis");
        }
        
        return 0;
    }
    
    private void printFileSizes(FileSystem fs, Path toRoot) throws FileNotFoundException, IOException {
        System.out.println("\nCompressed Sizes: ");
        for (FileStatus file : fs.listStatus(toRoot)){
            System.out.println(file.getPath().getName() + ": ~" + (int)(file.getLen()/1024d/1024d) + " MB");
        }
    }

    private CompressionCodec[] createCodecs() {
        GzipCodec gzip = new GzipCodec();
        gzip.setConf(getConf());
        
        BZip2Codec bzip = new BZip2Codec();
        
        Lz4Codec lz4 = new Lz4Codec();
        lz4.setConf(getConf());
        
        SnappyCodec snappy = new SnappyCodec();
        snappy.setConf(getConf());
        
        return new CompressionCodec[]{gzip, bzip, lz4, snappy};
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new CompareCompression(), args);
        System.exit(exitCode);
    }

}
