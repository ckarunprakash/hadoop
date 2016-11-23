--LeftOuterJoin.pig
posts = LOAD '/training/data/user-posts.txt' USING PigStorage(',') AS (user:chararray,post:chararray,date:long);
likes = LOAD '/training/data/user-likes.txt' USING PigStorage(',') AS (user:chararray,likes:int,date:long);
userInfo = JOIN posts BY user LEFT OUTER, likes BY user;
DUMP userInfo;