package hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class ScanBatchingExample {
	
	private static void printResults(HTable hTable, Scan scan) throws IOException {
		System.out.println("\n------------------");
		System.out.println("Batch=" + scan.getBatch());
		ResultScanner scanner = hTable.getScanner(scan);
		for ( Result result : scanner){
			System.out.println("Result: ");
			for ( KeyValue keyVal : result.list()){
				System.out.println("  " + 
						Bytes.toString(keyVal.getFamily()) + ":" +
						Bytes.toString(keyVal.getQualifier()) + " => " +
						Bytes.toString(keyVal.getValue()));
			}
		}
		scanner.close();
	}
	
	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable hTable = new HTable(conf, "HBaseSamples");
		
		Scan scan = new Scan();
		scan.addFamily(toBytes("columns"));
		printResults(hTable, scan); 
		
		scan.setBatch(2);
		printResults(hTable, scan); 
		
		hTable.close();
	}

}
