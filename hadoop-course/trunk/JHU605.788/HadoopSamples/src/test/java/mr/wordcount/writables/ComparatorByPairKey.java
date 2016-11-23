package mr.wordcount.writables;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.types.Pair;

import java.util.Comparator;

public class ComparatorByPairKey<T> implements Comparator<Pair<Text, T>> {
    @Override
    public int compare(Pair<Text, T> o1, Pair<Text, T> o2) {
        return o1.getFirst().compareTo(o2.getFirst());
    }

    @Override
    public boolean equals(Object obj){
        return super.equals(obj);
    }
}
