#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar compression.CompareCompression examples_input/txt/jdkAppendedSrc.txt"
yarn jar ~/Training/play_area/HadoopSamples.jar mr.wordcount.StartsWithCountJob examples_input/txt/compressedTest/compressed.bz2 /training/playArea/wordCount/
hdfs dfs -du -h /training/playArea/wordCount/
hdfs dfs -rm -r /training/playArea/wordCount/


execCommand "yarn jar ~/Training/play_area/HadoopSamples.jar mr.wordcount.StartsWithCountJob examples_input/txt/compressedTest/compressed.gz /training/playArea/wordCount/"
hdfs dfs -du -h /training/playArea/wordCount/
hdfs dfs -rm -r /training/playArea/wordCount/

execCommand "yarn jar ~/Training/play_area/HadoopSamples.jar mr.wordcount.StartsWithCountJob examples_input/txt/compressedTest/compressed.lz4 /training/playArea/wordCount/"
hdfs dfs -du -h /training/playArea/wordCount/
hdfs dfs -rm -r /training/playArea/wordCount/

execCommand "yarn jar ~/Training/play_area/HadoopSamples.jar mr.wordcount.StartsWithCountJob examples_input/txt/compressedTest/compressed.snappy /training/playArea/wordCount/"
hdfs dfs -du -h /training/playArea/wordCount/
hdfs dfs -rm -r /training/playArea/wordCount/

execCommand "yarn jar ~/Training/play_area/HadoopSamples.jar mr.wordcount.StartsWithCountJob examples_input/books/hamlet.txt /training/playArea/wordCount/"
hdfs dfs -du -h /training/playArea/wordCount/
hdfs dfs -rm -r /training/playArea/wordCount/

execCommand "yarn jar ~/Training/play_area/HadoopSamples.jar mr.wordcount.StartsWithCountJob \
    -Dmapreduce.output.fileoutputformat.compress=true \
    -Dmapreduce.output.fileoutputformat.compress.codec=org.apache.hadoop.io.compress.BZip2Codec \
    examples_input/books/hamlet.txt /training/playArea/wordCount/"
hdfs dfs -du -h /training/playArea/wordCount/
hdfs dfs -rm -r /training/playArea/wordCount/

execCommand "yarn jar ~/Training/play_area/HadoopSamples.jar mr.wordcount.StartsWithCountJob \
    -Dmapreduce.map.output.compress=true \
    -Dmapreduce.map.output.compress.codec=org.apache.hadoop.io.compress.SnappyCodec \
    examples_input/books/hamlet.txt /training/playArea/wordCount/"
hdfs dfs -rm -r /training/playArea/wordCount/

printStats