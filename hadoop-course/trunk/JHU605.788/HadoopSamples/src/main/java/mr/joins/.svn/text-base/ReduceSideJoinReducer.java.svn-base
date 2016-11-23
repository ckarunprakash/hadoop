package mr.joins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class ReduceSideJoinReducer extends Reducer<Text, Text, Text, Text> {
    
    private final List<Text> leftSide = new ArrayList<Text>();
    private final List<Text> rightSide = new ArrayList<Text>();
    private final Text empty = new Text();
    private final static String GROUP_NAME = "REDUCE-SIDE-JOIN";
    private final static String JOIN_INNER = "inner";
    private final static String JOIN_LEFT_OUTER = "leftOuter";
    private final static String JOIN_RIGHT_OUTER = "rightOuter";
    private final static String JOIN_FULL_OUTER = "fullOuter";
    private final static String JOIN_ANTI = "anti";
    
    private MultipleOutputs<Text, Text> out;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        out = new MultipleOutputs<Text,Text>(context);
    }
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        out.close();
    }
   

    @Override
    protected void reduce(Text token, Iterable<Text> joined, Context context) throws IOException, InterruptedException {
        leftSide.clear();
        rightSide.clear();

        for (Text t : joined) {
            if (t.charAt(0)=='L'){
                leftSide.add(new Text(t.toString().substring(1)));
            } else if (t.charAt(0)=='R'){
                rightSide.add(new Text(t.toString().substring(1)));
            }
        }
        
        innerJoin(context);
        leftOuterJoin(context);
        rightOuterJoin(context);
        fullOuterJoin(context);
        antiJoin(context);
    }

    private void innerJoin(Context context) throws IOException, InterruptedException {
        if (!leftSide.isEmpty() && !rightSide.isEmpty()){
            context.getCounter(GROUP_NAME, JOIN_INNER).increment(1);
            for (Text l : leftSide){
                for (Text r : rightSide){
                    out.write(l, r, JOIN_INNER);
                }
            }
        }
    }
    
    private void leftOuterJoin(Context context) throws IOException, InterruptedException {
        if (!leftSide.isEmpty()){
            context.getCounter(GROUP_NAME, JOIN_LEFT_OUTER).increment(1);
            for (Text l : leftSide){
                if (!rightSide.isEmpty()){
                    for (Text r : rightSide){
                        out.write(l, r, JOIN_LEFT_OUTER);
                    }
                } else {
                    out.write(l, empty, JOIN_LEFT_OUTER);
                }
            }
        }
    }
    
    private void rightOuterJoin(Context context) throws IOException, InterruptedException {
        if (!rightSide.isEmpty()){
            context.getCounter(GROUP_NAME, JOIN_RIGHT_OUTER).increment(1);
            for (Text r : rightSide){
                if (!leftSide.isEmpty()){
                    for (Text l : leftSide){
                        out.write(l, r, JOIN_RIGHT_OUTER);
                    }
                } else {
                    out.write(empty, r, JOIN_RIGHT_OUTER);
                }
            }
        }
    }
    
    private void fullOuterJoin(Context context) throws IOException, InterruptedException {
        if (!leftSide.isEmpty()){
            context.getCounter(GROUP_NAME, JOIN_FULL_OUTER).increment(1);
            for (Text l : leftSide){
                if (!rightSide.isEmpty()){
                    for (Text r : rightSide){
                        out.write(l, r, JOIN_FULL_OUTER);
                    }
                } else {
                    out.write(l, empty, JOIN_FULL_OUTER);
                }
            }
        } else if (!rightSide.isEmpty()){
            for (Text r : rightSide){
                out.write(empty, r, JOIN_FULL_OUTER);
            }
        }
    }
    
    private void antiJoin(Context context) throws IOException, InterruptedException {
        if (leftSide.isEmpty() || rightSide.isEmpty()){
            context.getCounter(GROUP_NAME, JOIN_ANTI).increment(1);
            for (Text l : leftSide){
                out.write(l, empty, JOIN_ANTI);
            }
            for (Text r : rightSide){
                out.write(empty,r, JOIN_ANTI);
            }
        } 
    }
}
