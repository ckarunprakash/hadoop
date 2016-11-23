reviews = LOAD 'ReviewTable' USING org.apache.pig.backend.hadoop.hbase.HBaseStorage(
	'reviews:user reviews:timestamp', '-limit 2')
	AS (user:chararray, timestamp:long);
DUMP reviews; 
reviews1 = LOAD 'ReviewTable' USING org.apache.pig.backend.hadoop.hbase.HBaseStorage(
	'reviews:user reviews:timestamp', '-limit 2 -caster org.apache.pig.backend.hadoop.hbase.HBaseBinaryConverter')
	AS (user:chararray, timestamp:long);
DUMP reviews1; 