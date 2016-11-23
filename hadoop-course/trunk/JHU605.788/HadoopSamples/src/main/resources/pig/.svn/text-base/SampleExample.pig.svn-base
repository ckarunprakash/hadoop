--SampleExample.pig
records = LOAD '/training/playArea/pig/excite-small.log' AS (userId:chararray, timestamp:long, query:chararray);
smallSample = SAMPLE records 0.01;
DUMP smallSample;