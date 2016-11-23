package mr.wordcount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.*;

public class StartsWithCountMapperTest {

    @Test
    public void processLineOfText() throws IOException, InterruptedException {
        StartsWithCountMapper underTest = new StartsWithCountMapper();
        LongWritable inputKey = new LongWritable();
        Text inputValue = new Text("Must process this line of text");
        Mapper.Context context = Mockito.mock(Mapper.Context.class);
        List<KeyValContainer> collected = setupOutputCollector(context);
        underTest.map(inputKey, inputValue, (Mapper.Context) context);

        assertEquals(6, collected.size());
        assertKeyVal(collected.get(0), "M", 1);
        assertKeyVal(collected.get(1), "p", 1);
        assertKeyVal(collected.get(2), "t", 1);
        assertKeyVal(collected.get(3), "l", 1);
        assertKeyVal(collected.get(4), "o", 1);
        assertKeyVal(collected.get(5), "t", 1);
    }

    class KeyValContainer {
        public KeyValContainer(Text key, IntWritable value) {
            this.key = key;
            this.value = value;
        }

        Text key;
        IntWritable value;
    }

    private List<KeyValContainer> setupOutputCollector(Mapper.Context context) throws IOException,
            InterruptedException {
        final List<KeyValContainer> collected = new ArrayList<KeyValContainer>();
        Answer ans = new Answer() {
            @Override
            public Object answer(InvocationOnMock inv) {
                Object[] args = inv.getArguments();
                KeyValContainer keyVal = new KeyValContainer(
                        new Text(((Text) args[0]).copyBytes()),
                        new IntWritable(((IntWritable) args[1]).get()));
                collected.add(keyVal);
                return inv.getMock();
            }
        };
        doAnswer(ans).when(context).write(any(), any());

        return collected;
    }

    private void assertKeyVal(KeyValContainer container, String expectedK, int expectedV) {
        assertEquals(expectedK, container.key.toString());
        assertEquals(expectedV, container.value.get());
    }

    @Test
    public void processLineOfTextWithMRUnit() throws IOException {
        LongWritable inputKey = new LongWritable();
        Text inputValue = new Text("Must process this line of text");
        new MapDriver<LongWritable, Text, Text, IntWritable>()
                .withMapper(new StartsWithCountMapper())
                .withInput(inputKey, inputValue)
                .withOutput(new Text("M"), new IntWritable(1))
                .withOutput(new Text("p"), new IntWritable(1))
                .withOutput(new Text("t"), new IntWritable(1))
                .withOutput(new Text("l"), new IntWritable(1))
                .withOutput(new Text("o"), new IntWritable(1))
                .withOutput(new Text("t"), new IntWritable(1))
                .runTest();
    }
}
