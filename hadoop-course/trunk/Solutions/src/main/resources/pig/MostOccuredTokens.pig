-- 1: Load text into a bag, where a row is a line of text
lines = LOAD '/training/data/hamlet.txt' AS (line:chararray);
-- 2: Tokenize the provided text
tokens = FOREACH lines GENERATE flatten(TOKENIZE(line)) AS token:chararray;
-- 3: Group by token
distinctTokens = GROUP tokens BY token;
-- 4: Count distinct tokens and # of time it occurs in the corpus
countPerToken = FOREACH distinctTokens GENERATE group as token:chararray, COUNT(tokens) as tokenCount:long;
-- 5: Descending order the group by the count
countsPerTokenOrdered = ORDER countPerToken BY tokenCount DESC;
-- 6: Grab the first five elements => Most occurrin
result = LIMIT countsPerTokenOrdered 5;
-- 5: Persist result to as file system
STORE result INTO '/training/playArea/pig/mostOccuredTokens';
