--CustomFilter-NoSchema.pig
REGISTER HadoopSamples.jar
DEFINE isShort pig.IsShort();

posts = LOAD 'examples_input/postAndLikes/user-posts.txt' USING PigStorage(',');
filtered = FILTER posts BY isShort($1);
dump filtered;