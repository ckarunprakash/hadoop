#!/bin/sh

source ./jobExecSupport.sh
source ./sharedProps.sh

echo "create 'ReviewTable', {NAME=>'reviews',COMPRESSION=>'snappy'}" | hbase shell
echo "create 'ReviewJoinedTable', {NAME=>'reviews',COMPRESSION=>'snappy'}" | hbase shell

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.StoreReviewsToHBase -Dreport.input.path=examples_input/reviews-xml"
echo "count 'ReviewTable'" | hbase shell

hdfs dfs -get examples_input/various/userToState.txt
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReplicatedJoin -Dreplicated.join.file=userToState.txt -files userToState.txt"
rm userToState.txt

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReduceSideJoin examples_input/postAndLikes/user-posts.txt examples_input/postAndLikes/user-likes.txt /training/playArea/reduceSideJoin/"
execCommand "hdfs dfs -stat /training/playArea/reduceSideJoin/inner-r-00000"
execCommand "hdfs dfs -stat /training/playArea/reduceSideJoin/leftOuter-r-00000"
execCommand "hdfs dfs -stat /training/playArea/reduceSideJoin/rightOuter-r-00000"
execCommand "hdfs dfs -stat /training/playArea/reduceSideJoin/fullOuter-r-00000"
execCommand "hdfs dfs -stat /training/playArea/reduceSideJoin/anti-r-00000"
hdfs dfs -rm -r /training/playArea/reduceSideJoin

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReduceSideJoinWithBloomFilter \
    -files $resources/mr/reviews/joins/userBloomFilter.bloom \
    -D bloom.filter.file=userBloomFilter.bloom \
    examples_input/postAndLikes/user-posts.txt examples_input/postAndLikes/user-likes.txt /training/playArea/reduceSideJoin/"
hdfs dfs -rm -r /training/playArea/reduceSideJoin

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.joins.ReduceSideJoinWithSecondarySort \
    examples_input/postAndLikes/user-posts.txt examples_input/postAndLikes/user-likes.txt \
    /training/playArea/reduceSideJoin/"
hdfs dfs -rm -r /training/playArea/reduceSideJoin

echo "disable 'ReviewTable'" | hbase shell
echo "drop 'ReviewTable'" | hbase shell
echo "disable 'ReviewJoinedTable'" | hbase shell
echo "drop 'ReviewJoinedTable'" | hbase shell

printStats