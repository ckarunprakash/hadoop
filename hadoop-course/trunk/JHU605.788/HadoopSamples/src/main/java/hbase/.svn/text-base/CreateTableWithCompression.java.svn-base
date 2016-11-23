package hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.io.hfile.Compression.Algorithm;

public class CreateTableWithCompression {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        HBaseAdmin admin = new HBaseAdmin(conf);
        try {
            String name = "NewTableWithCompression";
            byte[] tableName = toBytes(name);
            HTableDescriptor table = new HTableDescriptor(tableName);
            HColumnDescriptor family = new HColumnDescriptor(toBytes("new_family"));
            family.setCompressionType(Algorithm.GZ);
            table.addFamily(family);

            System.out.println("Table " + name + " exist: " + admin.tableExists(tableName));
            System.out.println("Creating " + name + " table...");
            admin.createTable(table);
            System.out.println("Table " + name + " exist: " + admin.tableExists(tableName));
        } finally {
            admin.close();
        }
    }

}
