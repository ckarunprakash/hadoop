package hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

import static org.apache.hadoop.hbase.util.Bytes.*;

public class PutExample {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        HTable hTable = new HTable(conf, "HBaseSamples");
        try {
            Put put1 = new Put(toBytes("row1"));
            put1.add(toBytes("test"), toBytes("col1"), toBytes("val1"));
            put1.add(toBytes("test"), toBytes("col2"), toBytes("val2"));
            hTable.put(put1);
        } finally {
            hTable.close();
        }
    }

}
