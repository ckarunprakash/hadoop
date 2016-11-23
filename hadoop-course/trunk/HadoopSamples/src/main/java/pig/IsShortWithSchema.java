package pig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pig.FilterFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.impl.logicalLayer.schema.Schema.FieldSchema;

public class IsShortWithSchema extends FilterFunc {
	private static final int MAX_CHARS = 15;

	@Override
	public Boolean exec(Tuple tuple) throws IOException {
		if (tuple == null || tuple.isNull() || tuple.size() == 0) {
			return false;
		}
		Object obj = tuple.get(0);
		if (obj instanceof String) {
			String st = (String) obj;
			if (st.length() > MAX_CHARS) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
		List<FuncSpec> schemaSpec = new ArrayList<FuncSpec>();
		FieldSchema fieldSchema = new FieldSchema(null, DataType.CHARARRAY);
		FuncSpec fieldSpec = new FuncSpec(this.getClass().getName(),
				new Schema(fieldSchema));
		schemaSpec.add(fieldSpec);

		return schemaSpec;
	}
}
