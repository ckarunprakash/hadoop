CREATE TABLE posts (user STRING, post STRING, time TIMESTAMP) 
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE;
	
-- Display all of the tables
show tables;

-- Display schema for posts table
describe posts;

LOAD DATA LOCAL INPATH 'data/user-posts.txt'
   OVERWRITE INTO TABLE posts;
	
select count (1) from posts;

select * from posts where user="user2";

select * from posts where time<=1343182133839 limit 2;

DROP TABLE posts;