package mr.reviews.fsstruct.mf;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MapFileFix extends Configured implements Tool{

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int run(String[] args) throws Exception {
        // ignoring parameter validation
        Path rootMapPath = new Path(args[0]);
        Path mapDataFile = new Path(rootMapPath, MapFile.DATA_FILE_NAME);
        FileSystem fs = FileSystem.get(getConf());
        
        Reader reader = new Reader(getConf(), Reader.file(mapDataFile));
        Class keyClass = reader.getKeyClass();
        Class valueClass = reader.getValueClass();
        reader.close();
        
        // generate MapFile index file
        long numOfValidEntires = MapFile.fix(fs, rootMapPath, keyClass, valueClass, false, getConf());
        System.out.println("Created index file for [" + numOfValidEntires + "]");
        
        return 0;
    }
    
    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new MapFileFix(), args);
        System.exit(code);
    }

}
