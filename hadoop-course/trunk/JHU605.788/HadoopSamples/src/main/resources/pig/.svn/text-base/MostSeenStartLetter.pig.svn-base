-- 1: Load text into a bag, where a row is a line of text
lines = LOAD 'examples_input/books/hamlet.txt' AS (line:chararray);
-- 2: Tokenize the provided text
tokens = FOREACH lines GENERATE flatten(TOKENIZE(line)) AS token:chararray;
-- 3: Retain first letter of each token
letters = FOREACH tokens GENERATE SUBSTRING(token,0,1) AS letter:chararray;
-- 4: Group by letter
letterGroup = GROUP letters BY letter;
-- 5: Count the number of occurrences in each group
countPerLetter = FOREACH letterGroup GENERATE group, COUNT(letters);
-- 6: Descending order the group by the count
orderedCountPerLetter = ORDER countPerLetter BY $1 DESC;
-- 7: Grab the first element => Most occurring letter
result = LIMIT orderedCountPerLetter 1;
-- 8: Persist result on a file system
STORE result INTO '/training/playArea/pig/mostSeenLetterOutput';
