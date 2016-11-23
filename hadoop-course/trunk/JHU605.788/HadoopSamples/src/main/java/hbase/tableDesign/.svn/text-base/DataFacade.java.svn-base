package hbase.tableDesign;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DataFacade {
	public List<Blog> getBlogs(String userId, 
			Date startDate, Date endDate) throws IOException;
	public void save(Blog blog) throws IOException;
	public void close() throws IOException;
}
