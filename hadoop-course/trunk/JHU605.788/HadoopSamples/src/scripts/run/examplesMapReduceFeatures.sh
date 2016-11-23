#!/bin/bash


source ./jobExecSupport.sh
source ./sharedProps.sh


execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.wordcount.StartsWithCountJob_UserCounters examples_input/books/hamlet.txt /training/playArea/wordCount/"
hdfs dfs -rm -r /training/playArea/wordCount/

mkdir -p $PLAY_AREA/data
echo "b" >> $PLAY_AREA/data/startWithExcludeFile.txt
echo "c" >> $PLAY_AREA/data/startWithExcludeFile.txt
echo "d" >> $PLAY_AREA/data/startWithExcludeFile.txt
echo "e" >> $PLAY_AREA/data/startWithExcludeFile.txt
echo "f" >> $PLAY_AREA/data/startWithExcludeFile.txt
echo "G" >> $PLAY_AREA/data/startWithExcludeFile.txt

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.wordcount.StartsWithCountJob_DistCache -files $PLAY_AREA/data/startWithExcludeFile.txt examples_input/books/hamlet.txt /training/playArea/wordCount/"
rm $PLAY_AREA/data/startWithExcludeFile.txt
hdfs dfs -rm -r /training/playArea/wordCount/

printStats