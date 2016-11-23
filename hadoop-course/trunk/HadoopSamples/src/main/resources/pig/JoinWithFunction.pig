posts = load '/training/data/user-posts.txt' using PigStorage(',') as (user:chararray,post:chararray,date:long);
likes = load '/training/data/user-likes.txt' using PigStorage(',') as (user:chararray,likes:int,date:long);
userInfo = join posts by SUBSTRING(user, 0, 1) left outer, likes by SUBSTRING(user, 0, 1);
dump userInfo;