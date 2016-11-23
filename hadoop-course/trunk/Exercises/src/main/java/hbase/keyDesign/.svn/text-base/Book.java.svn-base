package hbase.keyDesign;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
	private final String authorId;
	private final String title;
	private final Date publishDate;
	public Book(String authorId, String title, Date publishDate) {
		this.authorId = authorId;
		this.title = title;
		this.publishDate = publishDate;
	}
	public String getAuthorId() {
		return authorId;
	}
	public String getTitle() {
		return title;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	@Override
	public String toString() {
		return "[" + title + "] by author with id [" + authorId
				+ "] published on [" + DATE_FORMAT.format(publishDate) + "]";
	}
}
