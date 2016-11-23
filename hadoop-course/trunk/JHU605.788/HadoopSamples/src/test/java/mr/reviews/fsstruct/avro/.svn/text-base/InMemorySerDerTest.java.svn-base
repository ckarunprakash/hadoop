package mr.reviews.fsstruct.avro;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import mr.reviews.fsstruct.avro.model.ReviewAvro;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

public class InMemorySerDerTest {

    @Test
    public void testSerDer() throws IOException {
        ReviewAvro review = new ReviewAvro();
        review.setText("test text");
        review.setUser("first");
        review.setTimestamp(372080283800l);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DatumWriter<ReviewAvro> writer = new SpecificDatumWriter<ReviewAvro>(ReviewAvro.class);
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(review, encoder);
        encoder.flush();
        out.close();
        DatumReader<ReviewAvro> reader = new SpecificDatumReader<ReviewAvro>(ReviewAvro.class);
        Decoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(), null);
        ReviewAvro result = reader.read(null, decoder);
        
        assertEquals(review.getUser().toString(), result.getUser().toString());
        assertEquals(review.getText().toString(), result.getText().toString());
        assertEquals(review.getTimestamp().toString(), result.getTimestamp().toString());
    }

}
