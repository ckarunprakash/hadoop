package hbase.keyDesign;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;
import static org.apache.hadoop.hbase.util.Bytes.toLong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class TallAndNarrowTableDataFacade implements DataFacade {

	private final HTable table;
	private final static byte [] ENTRY_FAMILY = toBytes("entry");
	private final static byte [] BOOK_TITLE = toBytes("title");
	private final static byte [] PUBLISH_DATE = toBytes("published");
	
	private final static char KEY_SPLIT_CHAR = '_';
	public TallAndNarrowTableDataFacade(Configuration conf) throws IOException{
		table = new HTable(conf, "Exercise_Book_TallAndNarrow");
	}
	
	@Override
	public void close() throws IOException {
		table.close();
	}
	
	@Override
	public void save(Book book) throws IOException {
		Put put = new Put(toBytes(book.getAuthorId() + KEY_SPLIT_CHAR +  convertForId(book.getPublishDate())));
		put.add(ENTRY_FAMILY, BOOK_TITLE, toBytes(book.getTitle()));
		put.add(ENTRY_FAMILY, PUBLISH_DATE, toBytes(book.getPublishDate().getTime()));
		
		table.put(put);
	}
	
	private String convertForId(Date date ){
		return convertForId(date.getTime());
	}
	
	private String convertForId(long timestamp){
		String reversedDateAsStr=
				Long.toString(Long.MAX_VALUE-timestamp);
		StringBuilder builder = new StringBuilder();
		for ( int i = reversedDateAsStr.length(); i < 19; i++){
			builder.append('0');
		}
		builder.append(reversedDateAsStr);
		return builder.toString();
	}
	
	@Override
	public List<Book> getBooks(String authorId, Date from, Date to) throws IOException{
		List<Book> books = new ArrayList<Book>();
		
		Scan scan = new Scan(toBytes(authorId + KEY_SPLIT_CHAR + convertForId(to)), 
				toBytes(authorId + KEY_SPLIT_CHAR + convertForId(from.getTime()-1)));
		scan.addFamily(ENTRY_FAMILY);
		
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner){
			String title = Bytes.toString(result.getValue(ENTRY_FAMILY, BOOK_TITLE));
			long created = toLong(result.getValue(ENTRY_FAMILY, PUBLISH_DATE));
			books.add(new Book(authorId, title, new Date(created)));
		}
		return books;
	}

}
