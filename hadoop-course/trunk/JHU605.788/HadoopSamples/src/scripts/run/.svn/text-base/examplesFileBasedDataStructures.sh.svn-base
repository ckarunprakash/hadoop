#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh

# must comment out as it generates lots of splits (2,999) and runs for over an hour
#execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.SimpleTextXmlJob -conf $resources/mr/reviews/fsstruct/ReviewsJob-xmlInput.xml"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.SimpleTextXmlJob_CombineFileInputFormat -conf $resources/mr/reviews/fsstruct/ReviewsJob-xmlInput.xml"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.seq.ReviewSequenceFileWriter -conf $resources/mr/reviews/fsstruct/seq/SeqCreation.xml"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.seq.SequenceFileReader examples_input/reviews-seq/reviews.seqfile 2"

execCommand "HADOOP_CLASSPATH=$PLAY_AREA/HadoopSamples.jar \
yarn jar /usr/lib/hadoop-mapreduce/hadoop-mapreduce-examples.jar sort \
  -Dmapreduce.output.fileoutputformat.compress=true \
  -Dmapreduce.output.fileoutputformat.compress.codec=org.apache.hadoop.io.compress.SnappyCodec \
  -Dmapreduce.output.fileoutputformat.compress.type=BLOCK \
  -libjars $PLAY_AREA/HadoopSamples.jar \
  -r 1 \
  -inFormat org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat \
  -outFormat org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat \
  -outKey org.apache.hadoop.io.LongWritable \
  -outValue mr.reviews.model.ReviewWritable \
  examples_input/reviews-seq/reviews.seqfile /training/out/seq-to-map/"
execCommand "hdfs dfs -mv /training/out/seq-to-map/part-r-00000 /training/out/seq-to-map/data"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.mf.MapFileFix /training/out/seq-to-map/"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.mf.MfReader /training/out/seq-to-map/"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.mf.MfByKeyReader /training/out/seq-to-map/ 5"

hdfs dfs -rm -r examples_input/reviews-seq/
hdfs dfs -rm -r /training/out/seq-to-map/

printStats