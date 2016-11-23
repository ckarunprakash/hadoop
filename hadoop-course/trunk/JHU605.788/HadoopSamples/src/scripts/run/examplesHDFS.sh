#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh

execCommand "java -cp $PLAY_AREA/HadoopSamples.jar:/usr/lib/hadoop/*:/usr/lib/hadoop/lib/* hdfs.LoadConfigurations"
execCommand "java -cp $PLAY_AREA/HadoopSamples.jar:/usr/lib/hadoop/*:/usr/lib/hadoop/lib/* hdfs.SimpleLs"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SimpleLs"

echo "Hello from readme.txt" > readMe.txt
hdfs dfs -mkdir -p /training/data/
hdfs dfs -put readMe.txt /training/data/
rm readMe.txt
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.ReadFile"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SeekReadFile"
hdfs dfs -rm /training/data/readMe.txt

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.WriteToFile"
execCommand "hdfs dfs -rm /training/playArea/writeMe.txt"

echo "Hello from writeme.txt" > writeMe.txt
hdfs dfs -ls /training/playArea/
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.CopyToHdfs ./writeMe.txt /training/playArea/"
hdfs dfs -ls /training/playArea/
rm writeMe.txt
execCommand "hdfs dfs -rm /training/playArea/writeMe.txt"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.LsWithPathFilter"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SimpleGlobbing 'examples_input/glob/201*'"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SimpleGlobbing 'examples_input/glob/20[01][17]/'"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SimpleGlobbing 'examples_input/glob/201?/0[1-5]/*'"

printStats