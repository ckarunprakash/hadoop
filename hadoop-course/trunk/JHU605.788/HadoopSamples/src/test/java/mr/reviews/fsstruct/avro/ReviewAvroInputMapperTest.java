package mr.reviews.fsstruct.avro;

import java.io.IOException;
import java.util.Arrays;

import mr.reviews.ReviewJob;
import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.avro.hadoop.io.AvroSerialization;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.serializer.WritableSerialization;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

public class ReviewAvroInputMapperTest {

    @Test
    public void testValueFoundInReview() throws IOException {
        ReviewAvro inputReview = ReviewAvro.newBuilder().setUser("user1")
                .setTimestamp(13330820823030l).setText("This is where review would go").build();
        String keyword = "where";

        ReviewKeyAvro foundKey = ReviewKeyAvro.newBuilder()
                .setUser(inputReview.getUser()).setKeyword(keyword).build();

        ReviewReportAvro foundValue = ReviewReportAvro.newBuilder()
                .setKeyword(keyword).setNumReviews(1).setReviews(Arrays.asList(inputReview))
                .setUser(inputReview.getUser()).build();

        MapDriver driver = MapDriver.newMapDriver(new ReviewAvroMapper());
        Configuration conf = driver.getConfiguration();
        conf.setStrings("io.serializations", new String[]{
                WritableSerialization.class.getName(), AvroSerialization.class.getName()});
        AvroSerialization.setKeyWriterSchema(conf, ReviewAvro.SCHEMA$);
        driver.withInput(new AvroKey<ReviewAvro>(inputReview), NullWritable.get());

        AvroSerialization.setKeyWriterSchema(conf, ReviewKeyAvro.SCHEMA$);
        AvroSerialization.setValueWriterSchema(conf, ReviewReportAvro.SCHEMA$);

        driver.withOutput(new AvroKey<ReviewKeyAvro>(foundKey), new AvroValue<ReviewReportAvro>(foundValue));
        conf.set(ReviewJob.PROP_FIND_VALUE, keyword);
        driver.runTest();
    }


    @Test
    public void testNoValueFound() throws IOException {
        ReviewAvro inputReview = ReviewAvro.newBuilder()
                .setUser("user1").setTimestamp(13330820823030l)
                .setText("This is where review would go").build();
        String keyword = "wontfind";

        MapDriver driver = MapDriver.newMapDriver(new ReviewAvroMapper());
        Configuration conf = driver.getConfiguration();
        conf.set(ReviewJob.PROP_FIND_VALUE, keyword);
        conf.setStrings("io.serializations", new String[]{
                WritableSerialization.class.getName(), AvroSerialization.class.getName()});
        AvroSerialization.setKeyWriterSchema(conf, ReviewAvro.SCHEMA$);
        driver.withInput(new AvroKey<ReviewAvro>(inputReview), NullWritable.get());
        driver.runTest();
    }
}
