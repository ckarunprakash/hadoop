--LimitExampleWithDefaultParam.pig
%default LIMIT_NUM 2
records = LOAD '/training/playArea/pig/excite-small.log' AS (userId:chararray, timestamp:long, query:chararray);
toPrint = LIMIT records $LIMIT_NUM;
DUMP toPrint;