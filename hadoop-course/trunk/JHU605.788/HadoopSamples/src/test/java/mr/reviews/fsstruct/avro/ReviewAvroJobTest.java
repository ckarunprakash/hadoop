package mr.reviews.fsstruct.avro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewAvroJobTest {

    private Logger log = LoggerFactory.getLogger(ReviewAvroJobTest.class);
    private File inputFile = new File("./target/ReviewAvroJobTest/in/input.avro");
    private File output = new File("./target/ReviewAvroJobTest/test-result/");

    private Configuration conf;
    @Before
    public void setUpTest() throws IOException {
        prepareLocalLocations();
        conf = new Configuration();
        conf.set("mapreduce.framework.name", "local");
        conf.set("fs.defaultFS", "file:///");
    }

    @Test
    public void testReviewJob() throws Exception {
        List<ReviewAvro> inputSet = createInputDataset();
        addToAvroFile(inputFile, inputSet);
        conf.set(ReviewAvroJob.PROP_FIND_VALUE, "where");
        conf.set(ReviewAvroJob.PROP_INPUT_PATH, inputFile.getPath());
        conf.set(ReviewAvroJob.PROP_OUTPUT_PATH, output.getPath());
        
        ReviewAvroJob underTest = new ReviewAvroJob();
        underTest.setConf(conf);
        
        int exitCode = underTest.run(new String[]{});
        assertEquals("Returned error code.", 0, exitCode);
        assertTrue(new File(output, "_SUCCESS").exists());
        
        List<ReviewReportAvro> resultReports = getResultReports(new File(output, "part-r-00000.avro"));
        validateOutputSet(resultReports);
    }
    
    private void validateOutputSet(List<ReviewReportAvro> resultReports) {
        assertEquals(2, resultReports.size());
        
        ReviewReportAvro report = resultReports.get(0);
        assertEquals("user1", report.getUser().toString());
        assertEquals(2, report.getNumReviews().intValue());
        assertEquals("where", report.getKeyword().toString());
        assertEquals(2, report.getReviews().size());
        // further validation of report
        
        report = resultReports.get(1);
        assertEquals("user2", report.getUser().toString());
        assertEquals(1, report.getNumReviews().intValue());
        assertEquals("where", report.getKeyword().toString());
        assertEquals(1, report.getReviews().size());
        // further validation of report        
    }

    private List<ReviewAvro> createInputDataset(){
        ReviewAvro in1 = ReviewAvro.newBuilder()
                .setUser("user1").setTimestamp(13330820823030l)
                .setText("This is where review would go").build();
        ReviewAvro in2 = ReviewAvro.newBuilder()
                .setUser("user1").setTimestamp(13330820823030l)
                .setText("where will we find this keyword").build();
        ReviewAvro in3 = ReviewAvro.newBuilder()
                .setUser("user2").setTimestamp(13330820823030l)
                .setText("looking for where keeyword").build();
        return Arrays.asList(in1,in2,in3);
    }

    private void addToAvroFile(File inputFile, List<ReviewAvro> records) throws IOException {
        OutputStream outStream = null;
        DataFileWriter<ReviewAvro> writer = null;
        try {
            outStream = new FileOutputStream(inputFile);
            writer = new DataFileWriter<ReviewAvro>(new SpecificDatumWriter<ReviewAvro>());
            writer.create(ReviewAvro.SCHEMA$, outStream);
            for (ReviewAvro record : records){
                writer.append(record);
                log.info("Added record [{}] to [{}]", record, inputFile);
            }
        } finally {
            IOUtils.closeStream(writer);
            IOUtils.closeStream(outStream);
        }
    }
    
    private List<ReviewReportAvro> getResultReports(File file) throws IOException{
        
        List<ReviewReportAvro> result = new ArrayList<ReviewReportAvro>();
        InputStream in = null;
        DataFileStream<ReviewReportAvro> reader = null;
        try {
            in = new FileInputStream(file);
            
            SpecificData specificData = new SpecificData(this.getClass().getClassLoader());
            SpecificDatumReader<ReviewReportAvro> specificDatumReader = new SpecificDatumReader<ReviewReportAvro>(
                    ReviewReportAvro.SCHEMA$, ReviewReportAvro.SCHEMA$, specificData);
            reader = new DataFileStream<ReviewReportAvro>(in, specificDatumReader);
            for (ReviewReportAvro record : reader){
                result.add(record);
            }
        } finally {
            IOUtils.closeStream(reader);
            IOUtils.closeStream(in);
        }
        
        return result;
    }
    
    private void prepareLocalLocations() throws IOException {
        if (inputFile.exists()){
            FileUtils.forceDelete(inputFile);
        }
        if (output.exists()){
            FileUtils.forceDelete(output);
        }
        if (!inputFile.getParentFile().exists()){
            inputFile.getParentFile().mkdirs();
            inputFile.createNewFile();
        }
        if (!output.getParentFile().exists()){
            output.getParentFile().mkdirs();
        }
    }

}
