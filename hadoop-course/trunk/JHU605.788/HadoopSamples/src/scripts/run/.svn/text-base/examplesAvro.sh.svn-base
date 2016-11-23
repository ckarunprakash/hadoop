#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh


mkdir -p $PLAY_AREA/avro
cd $PLAY_AREA/avro
wget -O avro-tools.jar http://search.maven.org/remotecontent?filepath=org/apache/avro/avro-tools/1.7.4/avro-tools-1.7.4.jar
execCommand "java -jar $PLAY_AREA/avro/avro-tools.jar compile schema \
  $resources/mr/reviews/fsstruct/avro/model/review.avsc $javaLoc"


export HADOOP_CLASSPATH=$avroMapRedJar
avroResources="$resources/mr/reviews/fsstruct/avro"
avroCreationConf="$avroResources/AvroCreation.xml"
execCommand "yarn jar $samplesJar mr.revifews.fsstruct.avro.AvroWriterGeneric -conf $avroCreationConf $avroResources/model/review.avsc"
execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroAppenderGeneric -conf $avroCreationConf $avroResources/model/review.avsc"


execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroWriterSpecific -conf $avroCreationConf"
execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroWriterReflect -conf $avroCreationConf $avroResources/model/review.avsc"

execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroReaderGeneric examples_input/reviews-avro/reviews.avro > /dev/null"
execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroReaderGenericWithSchema examples_input/reviews-avro/reviews.avro  $avroResources/model/review.avsc"

execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroReaderReflect examples_input/reviews-avro/reviews.avro  $avroResources/model/review.avsc > /dev/null"

execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroReaderSpecific examples_input/reviews-avro/reviews.avro > /dev/null"
execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.AvroReaderSpecificV2 examples_input/reviews-avro/reviews.avro > /dev/null"

execCommand "java -jar $PLAY_AREA/avro/avro-tools.jar compile schema $resources/mr/reviews/fsstruct/avro/model/ $srcLoc/java/"


avroMapRedConf="$resources/mr/reviews/fsstruct/avro/AvroMapRed.xml"
execCommand "yarn jar $samplesJar mr.reviews.fsstruct.avro.ReviewAvroJob -libjars $avroMapRedJar -conf $avroMapRedConf"
hdfs dfs -rm -r /training/out/reviews-avro/

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.avro.AvroReaderGenericWithSchema examples_input/reviews-avro/reviews.avro  \
    $resources/mr/reviews/fsstruct/avro/readModel/review_newField.avsc | more"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.avro.AvroSorter -libjars $avroMapRedJar examples_input/reviews-avro/reviews.avro \
    examples_input/reviews-avro-sorted $resources/mr/reviews/fsstruct/avro/readModel/review_sort.avsc"
hdfs dfs -rm -r examples_input/reviews-avro-sorted

rm avro-tools.jar

printStats