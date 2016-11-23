--Cross.pig
posts = LOAD '/training/data/user-posts.txt' USING PigStorage(',') AS (user:chararray,post:chararray,date:long);
likes = LOAD '/training/data/user-likes.txt' USING PigStorage(',') AS (user:chararray,likes:int,date:long);
userInfo = CROSS posts, likes parallel 10;
result = FILTER userInfo BY posts::user == likes::user;
DUMP result;