-- MultiKeyCoGroup
posts = LOAD 'examples_input/postAndLikes/user-posts1.txt'
USING PigStorage(',')
AS (user:chararray,post:chararray,date:long);
likes = LOAD 'examples_input/postAndLikes/user-likes.txt'
USING PigStorage(',')
AS (user:chararray,likes:int,date:long);
userInfo = COGROUP posts BY user INNER, likes BY user INNER;
DUMP userInfo;