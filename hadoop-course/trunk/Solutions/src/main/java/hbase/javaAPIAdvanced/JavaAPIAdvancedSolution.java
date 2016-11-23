package hbase.javaAPIAdvanced;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class JavaAPIAdvancedSolution {

	// schema variables
	private static byte [] tableName = toBytes("Exercise_Advanced_Book");
	private static byte [] infoFamily = toBytes("info");
	private static byte [] titleColumn = toBytes("title");
	private static byte [] descriptionColumn = toBytes("description");
	
	private static byte [] authorFamily = toBytes("author");
	private static byte [] firstNameColumn = toBytes("first");
	private static byte [] lastNameColumn = toBytes("last");
	
	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, tableName);
		
		/////////////////////////////////////		
		// 1. Display all the records to the screen for the Book table (hint. Scan through the records)
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		System.out.println("------------------------------");
		System.out.println("1: All results are: ");
		for (Result scanResult : scanner){
			System.out.println(resultToString(scanResult));
		}
		scanner.close();
		
		////////////////////////////////////
		// 2. Display title and description for the first 2 records (hint. Scan through the records)

		// 2. Option #1
		scan = new Scan(toBytes("1"), toBytes("3"));
		scan.addColumn(infoFamily, titleColumn);
		scan.addColumn(infoFamily, descriptionColumn);
		scanner = table.getScanner(scan);
		System.out.println("------------------------------");
		System.out.println("2-option1: First 2 results ");
		for (Result scanResult : scanner){
			System.out.println(resultToString(scanResult));
		}
		scanner.close();
		
		// 2. Option #2
		scan = new Scan();
		scan.addColumn(infoFamily, titleColumn);
		scan.addColumn(infoFamily, descriptionColumn);
		scanner = table.getScanner(scan);
		System.out.println("------------------------------");
		System.out.println("2-option2: First 2 results ");
		int i = 0;
		for (Result scanResult : scanner){
			if ( i >= 2){
				break;
			}
			System.out.println(resultToString(scanResult));
			i++;
		}
		scanner.close();
		
		// 3. Display cells which contain “about” word using Scan Filter.  
		scan = new Scan();		
		scan.setFilter(new ValueFilter(CompareOp.EQUAL, new SubstringComparator("about")));
		scanner = table.getScanner(scan);
		System.out.println("------------------------------");
		System.out.println("3: Results with filter");
		for (Result scanResult : scanner){
			System.out.println(resultToString(scanResult));
		}
		scanner.close();
		
		//   
		scan = new Scan();		
		FilterList filters = new FilterList(Operator.MUST_PASS_ALL);
		filters.addFilter(new KeyOnlyFilter());
		filters.addFilter(new FirstKeyOnlyFilter());
		scan.setFilter(filters);
		
		scanner = table.getScanner(scan);
		System.out.println("------------------------------");
		System.out.println("4: Only Row Ids");
		for (Result scanResult : scanner){
			System.out.println(Bytes.toString(scanResult.getRow()));
		}
		scanner.close();		
		
		/////////////////////////////////////		
		// remember to close the table to release all the resources
		table.close();
	}

	private static String resultToString(Result result) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Result with rowId [" + Bytes.toString(result.getRow()) + "]");
		
		if ( result.containsColumn(infoFamily, titleColumn)){
			strBuilder.append(", title=" + Bytes.toString(result.getValue(infoFamily, titleColumn)));
		}
		if ( result.containsColumn(infoFamily, descriptionColumn)){
			strBuilder.append(", description=" + Bytes.toString(result.getValue(infoFamily, descriptionColumn)));
		}
		if ( result.containsColumn(authorFamily, firstNameColumn)){
			strBuilder.append(", first name=" + Bytes.toString(result.getValue(authorFamily, firstNameColumn)));
		}
		if ( result.containsColumn(authorFamily, lastNameColumn)){
			strBuilder.append(", last name=" + Bytes.toString(result.getValue(authorFamily, lastNameColumn)));
		}
		
		return strBuilder.toString();
	}

}
