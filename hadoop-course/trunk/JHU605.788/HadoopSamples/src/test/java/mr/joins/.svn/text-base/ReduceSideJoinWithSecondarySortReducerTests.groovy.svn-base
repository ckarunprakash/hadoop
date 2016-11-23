package mr.joins

import mr.joins.support.TextPair
import mr.wordcount.StartsWithCountReducer
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver
import mr.joins.ReduceSideJoinWithSecondarySortReducer
import org.junit.Test
import sun.java2d.pipe.TextPipe

class ReduceSideJoinWithSecondarySortReducerTests {

    @Test
    public void testJoin() {

        List<TextPair> inputPairs = [
                pairMe("a", "L"),
                pairMe("b", "L"),
                pairMe("c", "R")
        ]

        new ReduceDriver<TextPair, TextPair, Text, Text>()
                .withReducer(new ReduceSideJoinWithSecondarySortReducer())
                .withInput(pairMe("key", "left"), inputPairs)
                .withOutput(new Text("a"), new Text("c"))
                .withOutput(new Text("b"), new Text("c"))
                .runTest();
    }

    @Test
    public void testJoinWithManyValues() {

        List<TextPair> inputPairs = [
                pairMe("a", "L"),
                pairMe("b", "L"),
                pairMe("c", "R"),
                pairMe("d", "R"),
                pairMe("e", "R"),
        ]

        new ReduceDriver<TextPair, TextPair, Text, Text>()
                .withReducer(new ReduceSideJoinWithSecondarySortReducer())
                .withInput(pairMe("key", "left"), inputPairs)
                .withOutput(new Text("a"), new Text("c"))
                .withOutput(new Text("b"), new Text("c"))
                .withOutput(new Text("a"), new Text("d"))
                .withOutput(new Text("b"), new Text("d"))
                .withOutput(new Text("a"), new Text("e"))
                .withOutput(new Text("b"), new Text("e"))
                .runTest();
    }


    @Test
    public void testOnlyLeftSide() {

        List<TextPair> inputPairs = [
                pairMe("a", "L"),
                pairMe("b", "L"),
        ]

        new ReduceDriver<TextPair, TextPair, Text, Text>()
                .withReducer(new ReduceSideJoinWithSecondarySortReducer())
                .withInput(pairMe("key", "left"), inputPairs)
                .runTest();
    }

    @Test
    public void testOnlyRideSide() {

        List<TextPair> inputPairs = [
                pairMe("c", "R"),
                pairMe("d", "R"),
                pairMe("e", "R"),
        ]

        new ReduceDriver<TextPair, TextPair, Text, Text>()
                .withReducer(new ReduceSideJoinWithSecondarySortReducer())
                .withInput(pairMe("key", "left"), inputPairs)
                .runTest();
    }

    private TextPair pairMe(String first, String second) {
        return new TextPair(
                first: new Text(first),
                second: new Text(second)
        )
    }
}
