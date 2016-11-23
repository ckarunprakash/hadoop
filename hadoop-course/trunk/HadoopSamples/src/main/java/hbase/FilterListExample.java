package hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class FilterListExample {

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable hTable = new HTable(conf, "HBaseSamples");
		
		Scan scan = new Scan();
		FilterList filters = new FilterList(Operator.MUST_PASS_ALL);
		filters.addFilter(new KeyOnlyFilter());
		filters.addFilter(new FirstKeyOnlyFilter());
		
		scan.setFilter(filters);
		
		ResultScanner scanner = hTable.getScanner(scan);		
		for ( Result result : scanner){
			byte [] value = result.getValue(
					toBytes("metrics"), toBytes("counter"));
			System.out.println("  " + 
					Bytes.toString(result.getRow()) + " => " + 
					Bytes.toString(value));
		} 
		scanner.close();
		hTable.close();
	}
}
