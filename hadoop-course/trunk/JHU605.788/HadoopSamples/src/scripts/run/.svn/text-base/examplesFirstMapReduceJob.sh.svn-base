#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.wordcount.StartsWithCountJob examples_input/books/hamlet.txt /training/playArea/wordCount/"
hdfs dfs -rm -r /training/playArea/wordCount/

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.wordcount.StartsWithCountJob examples_input/books/*.txt /training/playArea/wordCount/"
hdfs dfs -rm -r /training/playArea/wordCount/

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.wordcount.StartsWithCountJob 'examples_input/books/{juliusCaesar,hamlet}.txt' /training/playArea/wordCount/"
hdfs dfs -rm -r /training/playArea/wordCount/

printStats
