package crunch;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;

import crunch.support.ImportantUtility;

public class BadDoFn extends DoFn<String, String> {
    private static final long serialVersionUID = 1L;

    private ImportantUtility util = new ImportantUtility();

    @Override
    public void process(String line, Emitter<String> emitter) {
        util.importantMethod(line, emitter);
    }
}
