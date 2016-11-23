--LimitExample.pig
records = LOAD '/training/playArea/pig/excite-small.log' AS (userId:chararray, timestamp:long, query:chararray);
toPrint = LIMIT records 5;
DUMP toPrint;