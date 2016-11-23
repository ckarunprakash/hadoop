package crunch;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;

public class CountFirstLettersDoFN extends DoFn<String, String>{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void process(String line, Emitter<String> emitter) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        while (tokenizer.hasMoreTokens()) {
            String nextToken = tokenizer.nextToken();
            if ( StringUtils.isNotBlank(nextToken)){
                emitter.emit(nextToken.substring(0, 1));    
            }
        }
    }
}
