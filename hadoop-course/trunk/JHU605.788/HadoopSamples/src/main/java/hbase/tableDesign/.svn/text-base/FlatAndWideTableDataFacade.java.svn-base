package hbase.tableDesign;

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
		table = new HTable(conf, "Blog_FlatAndWide");
	}
	@Override
	public void close() throws IOException{
		table.close();
	}
	
	@Override
	public void save(Blog blog) throws IOException {
		Put put = new Put(toBytes(blog.getUsername()));
		put.add(ENTRY_FAMILY, toBytes(dateToColumn(blog.getCreated())), 
				toBytes(blog.getBlogEntry()));
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
	public List<Blog> getBlogs(String userId,Date startDate, Date endDate) throws IOException {
		List<Blog> blogs = new ArrayList<Blog>();
		
		Get get = new Get(toBytes(userId));
		
		FilterList filters = new FilterList();
		filters.addFilter(new QualifierFilter(CompareOp.LESS_OR_EQUAL, 
				new BinaryComparator(toBytes(dateToColumn(startDate)))));
		filters.addFilter(new QualifierFilter(CompareOp.GREATER_OR_EQUAL, 
				new BinaryComparator(toBytes(dateToColumn(endDate)))));
		
		get.setFilter(filters);
		Result result = table.get(get);
		Map<byte[], byte[]> columnValueMap = result.getFamilyMap(ENTRY_FAMILY);
		for (Map.Entry<byte[], byte[]> entry : columnValueMap.entrySet()){
			Date createdOn = columnToDate(Bytes.toString(entry.getKey()));
			String blogEntry = Bytes.toString(entry.getValue());
			blogs.add(new Blog(userId, blogEntry, createdOn));
		}
		
		return blogs;
	}

}
