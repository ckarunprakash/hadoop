package hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;

public class DeleteExample {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        HTable hTable = new HTable(conf, "HBaseSamples");
        try {
            Delete delete = new Delete(toBytes("rowToDelete"));
            hTable.delete(delete);

            Delete delete1 = new Delete(toBytes("anotherRow"));
            delete1.deleteColumns(toBytes("metrics"), toBytes("loan"));
            hTable.delete(delete1);
        } finally {
            hTable.close();
        }
    }
}
