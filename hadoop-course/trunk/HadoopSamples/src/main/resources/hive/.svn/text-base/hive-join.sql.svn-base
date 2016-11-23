drop table posts if exist;
CREATE TABLE posts (user STRING, post STRING, time BIGINT) 
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ',' 
STORED AS TEXTFILE;
LOAD DATA LOCAL INPATH 'data/user-posts.txt' 
OVERWRITE INTO TABLE posts;

CREATE TABLE likes (user STRING, count STRING, time BIGINT) 
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ',' 
STORED AS TEXTFILE;
LOAD DATA LOCAL INPATH 'data/user-likes.txt' 
OVERWRITE INTO TABLE likes;

CREATE TABLE posts (user STRING, post INT, time BIGINT) 
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ',' 
STORED AS TEXTFILE;
LOAD DATA LOCAL INPATH 'data/user-posts.txt' 
OVERWRITE INTO TABLE posts;

select * from posts limit 10; 
select * from likes limit 10;

CREATE TABLE posts_likes (user STRING, post STRING, likes_cunt INT); 

INSERT OVERWRITE TABLE posts_likes
SELECT p.user, p.post, l.count
FROM posts p JOIN likes l ON (p.user = l.user);

select * from posts_likes limit 10;