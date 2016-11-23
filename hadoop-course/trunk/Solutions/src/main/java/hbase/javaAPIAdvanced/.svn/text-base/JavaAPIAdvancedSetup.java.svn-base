package hbase.javaAPIAdvanced;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class JavaAPIAdvancedSetup {

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
		HBaseAdmin admin = new HBaseAdmin(conf);

		/////////////////////////////////////
		// 1. Create table 'Book' with two families 'info' and 'author'
		HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
		tableDescriptor.addFamily(new HColumnDescriptor(infoFamily));
		tableDescriptor.addFamily(new HColumnDescriptor(authorFamily));
		admin.createTable(tableDescriptor);
		System.out.println("------------------------------");
		System.out.println("1: Created table ["+Bytes.toString(tableName) +"] with 2 families: "+ 
				Bytes.toString(infoFamily) + " and " + Bytes.toString(authorFamily));
			
		HTable table = new HTable(conf, tableName);
			
		/////////////////////////////////////
		// 2. Add data
		System.out.println("------------------------------");
		System.out.println("2: Saving rows: ");
		saveRow(table, "1", "Faster than the speed love", "Long book about love.", "Brian", "Dog");
		saveRow(table, "2", "Long day", "Story about Monday.", "Emily", "Blue");
		saveRow(table, "3", "Flying Car", "Novel about airplanes.", "Phil", "High");
		/////////////////////////////////////		
		// remember to close the table to release all the resources
		table.close();
			
		System.out.println("------------------------------");
	}

	private static void saveRow(HTable table, String rowId, String title, String description, String first, String last) throws IOException {
		Put put = new Put(toBytes(rowId));
		put.add(infoFamily, titleColumn, toBytes(title));
		put.add(infoFamily, descriptionColumn, toBytes(description));
		put.add(authorFamily, firstNameColumn, toBytes(first));
		put.add(authorFamily, lastNameColumn, toBytes(last));
		table.put(put);
			
		System.out.println("Saved row with id [" + rowId + "]");
	}

}
