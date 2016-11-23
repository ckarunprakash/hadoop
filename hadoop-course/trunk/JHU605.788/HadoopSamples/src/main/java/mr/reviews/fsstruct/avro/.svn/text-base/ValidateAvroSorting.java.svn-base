package mr.reviews.fsstruct.avro;

import java.io.InputStream;

import mr.reviews.fsstruct.avro.model.ReviewAvro;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ValidateAvroSorting extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Path inputPath = new Path(args[0]);
        FileSystem fs = FileSystem.get(getConf());
        Validate.isTrue(fs.exists(inputPath) && fs.isFile(inputPath));

        long timestamp = -1;
        InputStream in = null;
        DataFileStream<ReviewAvro> reader = null;
        try {
            in = fs.open(inputPath);
            SpecificData specificData = new SpecificData(this.getClass().getClassLoader());
            SpecificDatumReader<ReviewAvro> specificDatumReader = new SpecificDatumReader<ReviewAvro>(
                    ReviewAvro.SCHEMA$, ReviewAvro.SCHEMA$, specificData);
            reader = new DataFileStream<ReviewAvro>(in, specificDatumReader);
            int i =0;
            for (ReviewAvro review : reader) {
                if (timestamp == -1) {
                    timestamp = review.getTimestamp();
                } else {
                    Validate.isTrue(timestamp >= review.getTimestamp(), 
                            i + ": timestamps are not descending [" + timestamp
                            + "] not >= [" + review.getTimestamp() + "]");
                    timestamp = review.getTimestamp();
                }
                i++;
            }
        } finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(reader);
        }
        return 0;
    }

    // private void print(ReviewAvro review) {
    // String str = ToStringBuilder.reflectionToString(review,
    // ToStringStyle.SIMPLE_STYLE);
    // System.out.println(str);
    // }

    public static void main(String[] args) throws Exception {
        int code = ToolRunner.run(new ValidateAvroSorting(), args);
        System.exit(code);
    }
}
