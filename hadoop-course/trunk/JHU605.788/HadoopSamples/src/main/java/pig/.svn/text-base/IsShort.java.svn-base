package pig;

import java.io.IOException;

import org.apache.pig.FilterFunc;
import org.apache.pig.data.Tuple;

public class IsShort extends FilterFunc{
	private static final int MAX_CHARS = 15;
	@Override
	public Boolean exec(Tuple tuple) throws IOException {
		if ( tuple == null || tuple.isNull(0)){
			return false;
		}
		Object obj = tuple.get(0);
		if ( obj instanceof String){
			String st = (String)obj;
			if ( st.length() > MAX_CHARS ){
				return false;
			}
			return true;
		}
		return false;
	}
}
