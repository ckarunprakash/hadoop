package hbase;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IOUtils;

public class ValueFilterExample {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        HTable hTable = new HTable(conf, "HBaseSamples");
        ResultScanner scanner = null;
        try {
            Scan scan = new Scan();
            scan.setFilter(new ValueFilter(CompareOp.EQUAL, new SubstringComparator("3")));

            scanner = hTable.getScanner(scan);
            for (Result result : scanner) {
                byte[] value = result.getValue(
                        toBytes("metrics"), toBytes("counter"));
                System.out.println("  " +
                        Bytes.toString(result.getRow()) + " => " +
                        Bytes.toString(value));
            }
        } finally {
            IOUtils.closeStream(scanner);
            IOUtils.closeStream(hTable);
        }
    }
}
