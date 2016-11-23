#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh

hdfsRoot=/training/playArea/workflows

hdfs dfs -rm -r $hdfsRoot

confFile=$resources/mr/chaining/workflows-conf.xml

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.chaining.SimpleLinearDriver -conf $confFile $hdfsRoot/simpleLinearDriver"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.chaining.SimpleParallelDriver -conf $confFile $hdfsRoot/simpleParallelDriver"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.chaining.JobControlDriver -conf $confFile  $hdfsRoot/jobControlDriver"


execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.chaining.TaskChainingExample examples_input/books/hamlet.txt $hdfsRoot/taskChainingExample"


hdfs dfs -rm -r $hdfsRoot

printStats