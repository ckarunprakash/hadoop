package crunch.support;

import org.apache.crunch.Emitter;

public class ImportantUtility {
    public void importantMethod(String line, Emitter<String> emitter) {
        emitter.emit(line);
    }
}
