package hbase.tableDesign;

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
    private final static byte[] ENTRY_FAMILY = toBytes("entry");
    private final static byte[] BLOG_COLUMN = toBytes("blog");
    private final static byte[] CREATED_COLUMN = toBytes("created");
    private final static byte[] USER_COLUMN = toBytes("user");

    private final static char KEY_SPLIT_CHAR = '_';

    public TallAndNarrowTableDataFacade(Configuration conf) throws IOException {
        table = new HTable(conf, "Blog_TallAndNarrow");
    }

    @Override
    public void close() throws IOException {
        table.close();
    }

    @Override
    public void save(Blog blog) throws IOException {
        Put put = new Put(toBytes(blog.getUsername() + KEY_SPLIT_CHAR + convertForId(blog.getCreated())));
        put.add(ENTRY_FAMILY, USER_COLUMN, toBytes(blog.getUsername()));
        put.add(ENTRY_FAMILY, BLOG_COLUMN, toBytes(blog.getBlogEntry()));
        put.add(ENTRY_FAMILY, CREATED_COLUMN, toBytes(blog.getCreated().getTime()));

        table.put(put);
    }

    private String convertForId(Date date) {
        return convertForId(date.getTime());
    }

    private String convertForId(long timestamp) {
        String reversedDateAsStr =
                Long.toString(Long.MAX_VALUE - timestamp);
        StringBuilder builder = new StringBuilder();
        for (int i = reversedDateAsStr.length(); i < 19; i++) {
            builder.append('0');
        }
        builder.append(reversedDateAsStr);
        return builder.toString();
    }

    @Override
    public List<Blog> getBlogs(String userId, Date startDate, Date endDate) throws IOException {
        List<Blog> blogs = new ArrayList<Blog>();

        Scan scan = new Scan(toBytes(userId + KEY_SPLIT_CHAR + convertForId(endDate)),
                toBytes(userId + KEY_SPLIT_CHAR + convertForId(startDate.getTime() - 1)));
        scan.addFamily(ENTRY_FAMILY);

        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
                String user = Bytes.toString(result.getValue(ENTRY_FAMILY, USER_COLUMN));
                String blog = Bytes.toString(result.getValue(ENTRY_FAMILY, BLOG_COLUMN));
                long created = toLong(result.getValue(ENTRY_FAMILY, CREATED_COLUMN));
                blogs.add(new Blog(user, blog, new Date(created)));
            }
        } finally {
            scanner.close();
        }
        return blogs;
    }

}
