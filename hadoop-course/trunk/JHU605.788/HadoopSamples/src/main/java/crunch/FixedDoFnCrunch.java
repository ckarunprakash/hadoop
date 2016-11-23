package crunch;

import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.lib.Aggregate;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * CRUNCH_JAR=/home/hadoop/.m2/repository/org/apache/crunch/crunch/0.5.0-cdh4.1.3/crunch-0.5.0-cdh4.1.3.jar 
 * HADOOP_CLASSPATH=$CRUNCH_JAR:$HADOOP_CLASSPATH
 * yarn jar $PLAY_AREA/HadoopSamples.jar crunch.FixedDoFnCrunch -libjars $CRUNCH_JAR examples_input/books/hamlet.txt /training/playArea/crunch
 * 
 */
public class FixedDoFnCrunch extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        String input = args[0];
        String output = args[1];
        Pipeline pipeline = new MRPipeline(FixedDoFnCrunch.class, getConf());
        PCollection<String> lines = pipeline.readTextFile(input);

        PCollection<String> words = lines.parallelDo("good fn", new FixedDoFn(),
                Writables.strings());

        PTable<String, Long> counts = Aggregate.count(words);

        pipeline.writeTextFile(counts, output);
        PipelineResult res = pipeline.run();
        return res.succeeded() ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new FixedDoFnCrunch(), args);
        System.exit(exitCode);
    }
}
