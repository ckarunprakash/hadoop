#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh

pigS=$resources/pig
#execCommand "pig $pigS/MostSeenStartLetter.pig"


hdfs dfs -rm -r /training/playArea/pigAvro
#execCommand "pig $pigS/Cogroup.pig"
#execCommand "pig $pigS/CogroupInner.pig"
#execCommand "pig $pigS/CogroupWithParallel.pig"
#execCommand "pig $pigS/Cross.pig"
#execCommand "pig $pigS/CrossFilter.pig"
#execCommand "pig $pigS/CustomFilter.pig"
#execCommand "pig $pigS/CustomFilter-NoSchema.pig"
#execCommand "pig $pigS/CustomFilter-WithSchema.pig"
#execCommand "pig $pigS/FullOuterJoin.pig"
#execCommand "pig $pigS/InnerJoin.pig"
#execCommand "pig $pigS/InnerJoinWithMultipleKeys.pig"
#execCommand "pig $pigS/JoinWithFunction.pig"
#execCommand "pig $pigS/LeftOuterJoin.pig"
#
#execCommand "pig $pigS/MostSeenStartLetter.pig"
#execCommand "pig $pigS/MultiKeyCoGroup.pig"
#execCommand "pig $pigS/RightOuterJoin.pig"

#execCommand "pig $pigS/LimitExample.pig"
#execCommand "pig $pigS/LimitExampleWithDefaultParam.pig"
#execCommand "pig $pigS/LimitExampleWithMultipleParams.pig"
#execCommand "pig $pigS/LimitExampleWithParam.pig"
#execCommand "pig $pigS/SampleExample.pig"

#echo "create 'ReviewTable', {NAME=>'reviews',COMPRESSION=>'snappy'}" | hbase shell
#echo "create 'ReviewReportTable', {NAME=>'reviewKeywordHits',COMPRESSION=>'snappy'}, {NAME=>'reviewKeywordReport',COMPRESSION=>'snappy'}" | hbase shell

#execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.StoreReviewsToHBase -Dreport.input.path=examples_input/reviews-xml"
#echo "count 'ReviewTable'" | hbase shell

execCommand "pig $pigS/HBaseUsage.pig"
execCommand "pig $pigS/HBaseUsageWithCasterOption.pig"

#echo "disable 'ReviewTable'" | hbase shell
#echo "drop 'ReviewTable'" | hbase shell
#echo "disable 'ReviewReportTable'" | hbase shell
#echo "drop 'ReviewReportTable'" | hbase shell


#export HADOOP_CLASSPATH=$avroMapRedJar
#avroResources="$resources/mr/reviews/fsstruct/avro"
#avroCreationConf="$avroResources/AvroCreation.xml"
#yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroWriterGeneric -conf $avroCreationConf $avroResources/model/review.avsc
#execCommand "pig $pigS/AvroUsage.pig"

printStats