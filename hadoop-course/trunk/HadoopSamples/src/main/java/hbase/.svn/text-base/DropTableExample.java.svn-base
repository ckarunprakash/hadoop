package hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class DropTableExample {

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		byte [] tableName = toBytes("NewTable");
		admin.disableTable(tableName);
		admin.deleteTable(tableName);		
	}

}
