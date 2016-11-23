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

public class ScanCachingExample {
	
	private static void printResults(HTable hTable, Scan scan) throws IOException {
		System.out.println("\nCaching table=" + hTable.getScannerCaching() + 
				", scanner=" + scan.getCaching());
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
	
	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable hTable = new HTable(conf, "HBaseSamples");
		
		Scan scan = new Scan();
		scan.addColumn(toBytes("metrics"), toBytes("counter"));
		printResults(hTable, scan); 
		
		hTable.setScannerCaching(5);
		printResults(hTable, scan); 
		
		scan.setCaching(10);
		printResults(hTable, scan); 
		
		hTable.close();
	}

}
