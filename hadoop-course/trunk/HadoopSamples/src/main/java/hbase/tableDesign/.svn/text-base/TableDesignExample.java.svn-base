package hbase.tableDesign;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class TableDesignExample {
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	public static void main(String[] args) throws IOException {
		List<Blog> testBlogs = getTestBlogs();
		Configuration conf = HBaseConfiguration.create();
		
		DataFacade facade = new FlatAndWideTableDataFacade(conf);
		printTestBlogs(testBlogs);
		exercixeFacade(facade, testBlogs);
		facade.close();
		
		facade = new TallAndNarrowTableDataFacade(conf);
		exercixeFacade(facade, testBlogs);
		facade.close();
	}
	
	private static void printTestBlogs(List<Blog> testBlogs) {
		System.out.println("----------------");
		System.out.println("Test set has [" + testBlogs.size() + "] Blogs:");
		System.out.println("----------------");
		for (Blog blog : testBlogs){
			System.out.println(blog);
		}
	}

	private static List<Blog> getTestBlogs(){
		List<Blog> blogs = new ArrayList<Blog>();
		blogs.add(new Blog("user1", "Blog1", new Date(11111111)));
		blogs.add(new Blog("user1", "Blog2", new Date(22222222)));
		blogs.add(new Blog("user1", "Blog3", new Date(33333333)));
		blogs.add(new Blog("user2", "Blog4", new Date(44444444)));
		blogs.add(new Blog("user2", "Blog5", new Date(55555555)));
		return blogs;
	}
	
	public static void exercixeFacade(DataFacade facade, List<Blog> testBlogs) throws IOException{
		System.out.println("----------------");
		System.out.println("Running aggainst facade: " + facade.getClass());
		System.out.println("----------------");
		for (Blog blog : testBlogs){
			facade.save(blog);
		}
		getBlogs(facade, "user1", new Date(22222222), new Date(33333333));
		getBlogs(facade, "user2", new Date(55555555), new Date(55555555));
	}

	private static void getBlogs(DataFacade facade, String user, Date start, Date end)
			throws IOException {
		System.out.println("Selecting blogs for user [" + user + "] " +
				"between [" + DATE_FORMAT.format(start) + "] " +
				"and [" + DATE_FORMAT.format(end) + "]");
		
		for ( Blog blog : facade.getBlogs(user, start, end)){
			System.out.println("  " + blog);
		}
	}
}
