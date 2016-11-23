--CustomFilter-NoSchema.pig
REGISTER HadoopSamples.jar
DEFINE isShort pig.IsShortWithSchema();

posts = LOAD 'examples_input/postAndLikes/user-posts.txt' USING PigStorage(',');
filtered = FILTER posts BY isShort($1);
dump filtered;