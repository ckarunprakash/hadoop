package hbase;

import static org.apache.hadoop.hbase.util.Bytes.*;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class GetExample {

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable hTable = new HTable(conf, "HBaseSamples");
		
		Get get = new Get(toBytes("row1"));		
		Result result = hTable.get(get);
		print(result);
		
		get.addColumn(toBytes("test"), toBytes("col2"));
		result = hTable.get(get);
		print(result);
		
		hTable.close();
	}

	private static void print(Result result) {
		System.out.println("--------------------------------");
		System.out.println("RowId: " + Bytes.toString(result.getRow()));
		byte [] val1 = result.getValue(toBytes("test"), toBytes("col1"));
		System.out.println("test1:col1="+Bytes.toString(val1));
		byte [] val2 = result.getValue(toBytes("test"), toBytes("col2"));
		System.out.println("test1:col2="+Bytes.toString(val2));
	}

}
