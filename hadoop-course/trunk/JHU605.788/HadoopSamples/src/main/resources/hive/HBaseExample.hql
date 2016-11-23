CREATE TABLE inhbase (rowId STRING, post STRING, time TIMESTAMP) 
     stored by 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
     with serdeproperties ("hbase.columns.mapping" = ":key,content:post,content:time");
     

INSERT OVERWRITE TABLE inhbase SELECT user, post, time from posts;     
select * from inhbase;

select * from inhbase where rowId="user1";
select * from inhbase where post="Funny Story";

-- join hbase-based table with file-based table
SELECT p.user, i.post, i.time FROM posts p JOIN inhbase i ON (p.user = i.rowId);


-- create external table and point to already existing table
CREATE EXTERNAL TABLE inhbase_external (rowId STRING, post STRING) 
     stored by 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
     with serdeproperties ("hbase.columns.mapping" = ":key,content:post")
     TBLPROPERTIES("hbase.table.name" = "inhbase");
select * from inhbase_external;

-- will not drop underlying table
drop table inhbase_external;

-- example usage of map complex data type
-- lookup column is a map where key will be hbase's qualifier name
CREATE EXTERNAL TABLE inhbase_map (rowId STRING, lookup map<string,string>) 
     stored by 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
     with serdeproperties ("hbase.columns.mapping" = ":key,content:")
     TBLPROPERTIES("hbase.table.name" = "inhbase");
-- can select a particular value by doing qualifier name lookup
select lookup["post"] from inhbase_map;
drop table inhbase_map;

drop table inhbase;
