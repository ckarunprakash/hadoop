package hbase.keyDesign;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class FlatAndWideTableDataFacade implements DataFacade {

	private final HTable table;
	private final static byte [] ENTRY_FAMILY = toBytes("entry");
	public FlatAndWideTableDataFacade(Configuration conf) throws IOException{
		table = new HTable(conf, "Exercise_Book_FlatAndWide");
	}
	@Override
	public void close() throws IOException{
		table.close();
	}
	
	@Override
	public void save(Book book) throws IOException {
		Put put = new Put(toBytes(book.getAuthorId()));
		put.add(ENTRY_FAMILY, toBytes(dateToColumn(book.getPublishDate())), 
				toBytes(book.getTitle()));
		table.put(put);
	}
	
	private String dateToColumn(Date date ){
		String reversedDateAsStr=
				Long.toString(Long.MAX_VALUE-date.getTime());
		StringBuilder builder = new StringBuilder();
		for ( int i = reversedDateAsStr.length(); i < 19; i++){
			builder.append('0');
		}
		builder.append(reversedDateAsStr);
		return builder.toString();
	}
	public Date columnToDate(String column){
		long reverseStamp = Long.parseLong(column);
		return new Date(Long.MAX_VALUE-reverseStamp);
	}
	
	@Override
	public List<Book> getBooks(String authorId, Date from,
			Date to) throws IOException {
		return null;
	}

}
