package hbase.keyDesign;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DataFacade {
	public List<Book> getBooks(String authorId, Date from, Date to) throws IOException;
	public void save(Book book) throws IOException;
	public void close() throws IOException;
}
