--LimitExampleWithMultipleParams.pig
records = LOAD '$PATH_TO_LOAD' AS (userId:chararray, timestamp:long, query:chararray);
toPrint = LIMIT records $LIMIT_NUM;
DUMP toPrint;