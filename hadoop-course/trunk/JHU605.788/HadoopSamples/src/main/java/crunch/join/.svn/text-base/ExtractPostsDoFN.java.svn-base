package crunch.join;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;
import org.apache.crunch.Pair;

public class ExtractPostsDoFN extends DoFn<String, Pair<String, String>> {
    private static final long serialVersionUID = 1L;

    @Override
    public void process(String input, Emitter<Pair<String, String>> emitter) {
        emitter.emit(Pair.of(input.split(",")[0], input));
    }
}
