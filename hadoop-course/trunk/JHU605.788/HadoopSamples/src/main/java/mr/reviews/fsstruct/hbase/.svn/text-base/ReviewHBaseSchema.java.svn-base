package mr.reviews.fsstruct.hbase;

import static org.apache.hadoop.hbase.util.Bytes.*;


/**
Statement to create schema:
  create 'ReviewTable', {NAME=>'reviews',COMPRESSION=>'snappy'}
  create 'ReviewReportTable', {NAME=>'reviewKeywordHits',COMPRESSION=>'snappy'}, {NAME=>'reviewKeywordReport',COMPRESSION=>'snappy'}
  
  create 'ErrorTable', {NAME=>'errors',COMPRESSION=>'snappy'}
  
  create 'ReviewJoinedTable', {NAME=>'reviews',COMPRESSION=>'snappy'}
 */
public interface ReviewHBaseSchema {
    public static String REVIEW_TABLE = "ReviewTable";

    public static byte[] REVIEW_FAMILY_CONTENT = toBytes("reviews");
    
    public static byte[] REVIEW_COLUMN_USER = toBytes("user");
    public static byte[] REVIEW_COLUMN_TEXT = toBytes("text");
    public static byte[] REVIEW_COLUMN_TIMESTAMP = toBytes("timestamp");
    
    // REPORT TABLE
    public static String REVIEW_REPORT_TABLE = "ReviewReportTable";
    public static byte[] REVIEW_REPORT_FAMILY_KEYWORDHITS = toBytes("reviewKeywordHits");
    public static byte[] REVIEW_REPORT_FAMILY_HITSREPORT = toBytes("reviewKeywordReport");
    
    public static byte[] REVIEW_REPORT_COLUMN_KEYWORD = toBytes("keyword");
    public static byte[] REVIEW_REPORT_COLUMN_NUMBER_OF_REVIEWS = toBytes("number");
    
    public static String ERROR_TABLE = "ErrorTable";
    public static byte[] ERROR_FAMILY =  toBytes("errors");
    public static byte[] ERROR_COLUMN_EXCEPTION =  toBytes("exception");
    
    public static byte [] SPLIT = toBytes("-");
    
    // Join example
    public static String REVIEW_JOIN_TABLE = "ReviewJoinedTable";
    public static byte[] REVIEW_COLUMN_STATE = toBytes("state");
}
