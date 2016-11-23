package crunch.reviews.hbase;

import static mr.reviews.ReviewJob.PROP_FIND_VALUE;

import java.util.Arrays;
import java.util.List;

import mr.reviews.fsstruct.hbase.ReviewHBaseSchema;

import org.apache.commons.lang.Validate;
import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.io.hbase.HBaseSourceTarget;
import org.apache.crunch.io.hbase.HBaseTarget;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *
CRUNCH_JAR=/home/hadoop/.m2/repository/org/apache/crunch/crunch/0.5.0-cdh4.1.3/crunch-0.5.0-cdh4.1.3.jar
CRUNCH_HBASE_JAR=/home/hadoop/.m2/repository/org/apache/crunch/crunch-hbase/0.5.0-cdh4.1.3/crunch-hbase-0.5.0-cdh4.1.3.jar
HBASE_JAR=/home/hadoop/.m2/repository/org/apache/hbase/hbase/0.92.1-cdh4.1.2/hbase-0.92.1-cdh4.1.2.jar
HADOOP_CLASSPATH=$CRUNCH_JAR:$CRUNCH_HBASE_JAR:$HADOOP_CLASSPATH
yarn jar $PLAY_AREA/HadoopSamples.jar crunch.reviews.hbase.ReviewReportCrunchWithHBase -libjars $CRUNCH_JAR,$CRUNCH_HBASE_JAR,$HBASE_JAR -Dreport.value=invaluable,affordable
 * 
 */
public class ReviewReportCrunchWithHBase extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        Pipeline pipeline = new MRPipeline(ReviewReportCrunchWithHBase.class, getConf());
        
        String keywords = getConf().get(PROP_FIND_VALUE);
        Validate.notEmpty(keywords, "You must provide value to find via [" + PROP_FIND_VALUE + "]");
        List<String> valuesToLookFor = Arrays.asList(keywords.split(","));
        
        Scan scan = new Scan();
        HBaseSourceTarget source = new HBaseSourceTarget(ReviewHBaseSchema.REVIEW_TABLE, scan);
        PTable<ImmutableBytesWritable, Result> inputReviews = pipeline.read(source);
        PCollection<Put> puts = inputReviews.values().parallelDo(
                "find-reviews", new FindReviewsHBaseDoFn(valuesToLookFor), 
                Writables.writables(Put.class));

        
        pipeline.write(puts, new HBaseTarget(ReviewHBaseSchema.REVIEW_REPORT_TABLE));
        PipelineResult res = pipeline.run();
        return res.succeeded() ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new ReviewReportCrunchWithHBase(), args);
        System.exit(exitCode);
    }
}
