package hbase.keyDesign;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class TableDesignDriver {
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	public static void main(String[] args) throws IOException {
		List<Book> books = generateTestSet();
		Configuration conf = HBaseConfiguration.create();
		
		DataFacade facade = new FlatAndWideTableDataFacade(conf);
		printTestSet(books);
		exercixeFacade(facade, books);
		facade.close();
		
		facade = new TallAndNarrowTableDataFacade(conf);
		exercixeFacade(facade, books);
		facade.close();
	}
	
	private static void printTestSet(List<Book> testBooks) {
		System.out.println("----------------");
		System.out.println("Test set has [" + testBooks.size() + "] Books:");
		System.out.println("----------------");
		for (Book book : testBooks){
			System.out.println(book);
		}
	}

	private static List<Book> generateTestSet(){
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("author1", "Title1", new Date(11111111)));
		books.add(new Book("author1", "Title2", new Date(22222222)));
		books.add(new Book("author1", "Title3", new Date(33333333)));
		books.add(new Book("author2", "Title4", new Date(44444444)));
		books.add(new Book("author2", "Title5", new Date(55555555)));
		return books;
	}
	
	public static void exercixeFacade(DataFacade facade, List<Book> testBooks) throws IOException{
		System.out.println("----------------");
		System.out.println("Running against facade: " + facade.getClass());
		System.out.println("----------------");
		for (Book book : testBooks){
			facade.save(book);
		}
		getBooks(facade, "author1", new Date(22222222), new Date(33333333));
		getBooks(facade, "author3", new Date(55555555), new Date(55555555));
	}

	private static void getBooks(DataFacade facade, String authorId, Date start, Date end)
			throws IOException {
		System.out.println("Selecting books for author [" + authorId + "] " +
				"between [" + DATE_FORMAT.format(start) + "] " +
				"and [" + DATE_FORMAT.format(end) + "]");
		
		for ( Book book : facade.getBooks(authorId, start, end)){
			System.out.println("  " + book);
		}
	}
}
