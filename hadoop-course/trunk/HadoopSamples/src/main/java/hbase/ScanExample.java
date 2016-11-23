package hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class ScanExample {
	
	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable hTable = new HTable(conf, "HBaseSamples");
		
		scan(hTable, "row-03", "row-05");
		scan(hTable, "row-10", "row-15");
		hTable.close();
	}

	private static void scan(HTable hTable, String startRow, 
			String stopRow) throws IOException {
		System.out.println("Scanning from " +
				"["+startRow+"] to ["+stopRow+"]");
		
		Scan scan = new Scan(toBytes(startRow), toBytes(stopRow));
		scan.addColumn(toBytes("metrics"), toBytes("counter"));
		ResultScanner scanner = hTable.getScanner(scan);		
		for ( Result result : scanner){
			byte [] value = result.getValue(
					toBytes("metrics"), toBytes("counter"));
			System.out.println("  " + 
					Bytes.toString(result.getRow()) + " => " + 
					Bytes.toString(value));
		} 
		scanner.close();
	}
}
