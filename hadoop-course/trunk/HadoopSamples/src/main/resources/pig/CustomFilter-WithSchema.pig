--CustomFilter-NoSchema.pig
REGISTER HadoopSamples.jar
DEFINE isShort pig.IsShortWithSchema();

posts = LOAD '/training/data/user-posts.txt' USING PigStorage(',');
filtered = FILTER posts BY isShort($1);
dump filtered;