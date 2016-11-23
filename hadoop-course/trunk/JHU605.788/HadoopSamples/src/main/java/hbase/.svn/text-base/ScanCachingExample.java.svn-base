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
        System.out.println("\nScanner=" + scan.getCaching());
        ResultScanner scanner = hTable.getScanner(scan);
        try {
            for (Result result : scanner) {
                byte[] value = result.getValue(toBytes("metrics"), toBytes("counter"));
                System.out.println("  " +
                        Bytes.toString(result.getRow()) + " => " +
                        Bytes.toString(value));
            }
        } finally {
            scanner.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        HTable hTable = new HTable(conf, "HBaseSamples");
        try {
            Scan scan = new Scan();
            scan.addColumn(toBytes("metrics"), toBytes("counter"));
            printResults(hTable, scan);

            scan.setCaching(10);
            printResults(hTable, scan);
        } finally {
            hTable.close();
        }
    }

}
