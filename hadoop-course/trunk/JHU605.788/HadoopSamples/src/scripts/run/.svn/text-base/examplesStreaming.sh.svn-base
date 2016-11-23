#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh


hdfs dfs -rm -r /training/out/streaming/
execCommand "yarn jar /usr/lib/hadoop-mapreduce/hadoop-streaming.jar -D mapred.job.name='Count Job via Streaming' \
    -files $resources/streaming/countMap.py,$resources/streaming/countReduce.py -input examples_input/books/hamlet.txt \
    -output /training/out/streaming/ -mapper countMap.py -combiner countReduce.py -reducer countReduce.py"
hdfs dfs -rm -r /training/out/streaming/

execCommand "yarn jar /usr/lib/hadoop-mapreduce/hadoop-streaming.jar -D mapred.job.name='Count Job via Streaming' \
    -files $resources/streaming/countMap_withReporting.py,$resources/streaming/countReduce.py -input examples_input/books/hamlet.txt \
    -output /training/out/streaming/ -mapper countMap_withReporting.py -combiner countReduce.py -reducer countReduce.py"
hdfs dfs -rm -r /training/out/streaming/

groovyJar="/home/hadoop/.m2/repository/org/codehaus/groovy/groovy-all/2.2.2/groovy-all-2.2.2.jar"
execCommand "HADOOP_CLASSPATH=${groovyJar} \
    yarn jar $PLAY_AREA/HadoopSamples.jar mr.wordcount.groovy.CountGroovyJob -libjars ${groovyJar} \
    examples_input/books/hamlet.txt /training/playArea/wordCount/"
hdfs dfs -rm -r /training/playArea/wordCount/

printStats