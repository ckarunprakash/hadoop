package mr.samples;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Mapper;

public class SwithKeyAndValueMapper<T> extends Mapper<T, T, T, T> {

    @Override
    public void map(T key, T value, Context context)
            throws IOException, InterruptedException {
        // switcharoo
        context.write(value, key);
    }
}
