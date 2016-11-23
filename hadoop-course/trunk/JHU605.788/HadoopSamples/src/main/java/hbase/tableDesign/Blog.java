package hbase.tableDesign;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Blog {
	private final String username;
	private final String blogEntry;
	private final Date created;
	public Blog(String username, String blogEntry, Date created) {
		this.username = username;
		this.blogEntry = blogEntry;
		this.created = created;
	}
	public String getUsername() {
		return username;
	}
	public String getBlogEntry() {
		return blogEntry;
	}
	public Date getCreated() {
		return created;
	}
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	@Override
	public String toString() {
		return "["+ blogEntry + "] by [" + username + "] on [" + 
				DATE_FORMAT.format(created) + "]";
	}
}
