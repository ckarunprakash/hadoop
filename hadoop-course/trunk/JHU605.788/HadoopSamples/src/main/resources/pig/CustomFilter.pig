--CustomFilter.pig
REGISTER HadoopSamples.jar
DEFINE isShort pig.IsShort();

posts = LOAD 'examples_input/postAndLikes/user-posts.txt' USING PigStorage(',') AS (user:chararray,post:chararray,date:long);
filtered = FILTER posts BY isShort(post);
dump filtered;