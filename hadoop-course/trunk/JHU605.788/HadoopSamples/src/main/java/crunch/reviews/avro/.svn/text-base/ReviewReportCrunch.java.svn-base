package crunch.reviews.avro;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.io.From;
import org.apache.crunch.io.To;
import org.apache.crunch.types.avro.Avros;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *
CRUNCH_JAR=/home/hadoop/.m2/repository/org/apache/crunch/crunch/0.5.0-cdh4.1.3/crunch-0.5.0-cdh4.1.3.jar
AVRO_JAR=/home/hadoop/.m2/repository/org/apache/avro/avro-mapred/1.7.4/avro-mapred-1.7.4-hadoop2.jar
HADOOP_CLASSPATH=$CRUNCH_JAR:$AVRO_JAR:$HADOOP_CLASSPATH
yarn jar $PLAY_AREA/HadoopSamples.jar crunch.reviews.avro.ReviewReportCrunch -libjars /home/hadoop/.m2/repository/org/apache/crunch/crunch/0.5.0-cdh4.1.3/crunch-0.5.0-cdh4.1.3.jar,/home/hadoop/.m2/repository/org/apache/avro/avro-mapred/1.7.4/avro-mapred-1.7.4-hadoop2.jar -Dreport.value=invaluable,affordable examples_input/reviews-avro/reviews.avro /training/playArea/crunch
 * 
 */
public class ReviewReportCrunch extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        String input = args[0];
        String output = args[1];
        Pipeline pipeline = new MRPipeline(ReviewReportCrunch.class, getConf());
        PCollection<ReviewAvro> inputReviews = pipeline.read(From.avroFile(new Path(input), ReviewAvro.class));

        PTable<ReviewKeyAvro, ReviewReportAvro > foundReviews = 
                inputReviews.parallelDo("find-reviews->create-reports", new FindReviewsDoFn(),
                        Avros.tableOf(Avros.specifics(ReviewKeyAvro.class), Avros.specifics(ReviewReportAvro.class)));

        PTable<ReviewKeyAvro, ReviewReportAvro> combined = foundReviews.groupByKey().combineValues(new ReportsCombineFn());
        PCollection<ReviewReportAvro> cominbedReports = combined.values();
        pipeline.write(cominbedReports, To.avroFile(output));
        PipelineResult res = pipeline.run();
        return res.succeeded() ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new ReviewReportCrunch(), args);
        System.exit(exitCode);
    }
}
