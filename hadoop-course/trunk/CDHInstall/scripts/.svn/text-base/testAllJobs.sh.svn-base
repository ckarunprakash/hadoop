#/bin/bash

totalJobs=0
numFailed=0

startTime=`date '+%Y%m%d%H%M%S'`
logBase="/home/hadoop/Training/scripts/"
log=$logBase"log-allJobs.txt"
errorLog=$logBase"log-failedCommands.txt"

echo "Logs: [$log]"
echo "Errors: [$errorLog]"



function log(){
   echo "$1" >> /home/hadoop/Training/scripts/log-allJobs.txt
}
function logFailure(){
   echo "$1" >> /home/hadoop/Training/scripts/log-failedCommands.txt
   log "$1"
}

function execCommand(){
   execCommandExpectReturnCode "$1" 0
}

function execCommandExpectReturnCode(){
   log "------------------------------------"
   log "Executing: [$1]"
   eval $1
   result=$?
   if [ $result -ne $2 ]; then
       logFailure "  Failed to execute [$1], return code [$result] but expected [$2]"
       numFailed=`expr $numFailed + 1`
   fi
   log "Executed: [$1] return-code=[$result]"
   log "------------------------------------"
   totalJobs=`expr $totalJobs + 1`
}

function execStep(){
   hdfs dfs -rm -r "$2"
   cd "$3"
   execCommand "$1"
   hdfs dfs -rm -r "$2"
}

function printStats(){
   currTime=`date '+%Y%m%d%H%M%S'`
   runTime=`expr $currTime - $startTime`
   printMsg="Ran [$totalJobs] Hadoop jobs with [$numFailed] failures in [$runTime] seconds"
   log "$printMsg"
   echo "##########################################################"
   echo "#  $printMsg"
   echo "#  Logs: [$log]"
   echo "#  Errors: [$errorLog]"
   echo "##########################################################"
}


log "############################################
# HDFS Samples
############################################"
execStep "yarn jar $PLAY_AREA/Exercises.jar \
mapRed.workflows.CountDistinctTokens \
/training/data/hamlet.txt \
/training/playArea/firstJob" "/training/playArea/firstJob" "$PLAY_AREA/"

execCommand "java -cp $PLAY_AREA/HadoopSamples.jar:$HADOOP_HOME/share/hadoop/common/hadoop-common-2.0.0-cdh4.0.0.jar:$HADOOP_HOME/share/hadoop/common/lib/* hdfs.LoadConfigurations"

execCommand "java -cp $PLAY_AREA/HadoopSamples.jar:$HADOOP_HOME/share/hadoop/common/hadoop-common-2.0.0-cdh4.0.0.jar:$HADOOP_HOME/share/hadoop/common/lib/* hdfs.SimpleLs"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SimpleLs"

hdfs dfs -rm /training/playArea/writeMe.txt
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.WriteToFile"
execCommandExpectReturnCode "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.BadWriteToFile" 1
hdfs dfs -rm /training/playArea/writeMe.txt

hdfs dfs -rm /training/playArea/hamlet.txt
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.CopyToHdfs"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.MkDir"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SimpleLs"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.LsWithPathFilter"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.SimpleGlobbing /training/data/glob/201*"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hdfs.BadRename"

log "############################################
# HBase Samples
############################################"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ConstructHTable"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.PutExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.GetExample"

echo "put 'HBaseSamples', 'anotherRow', 'metrics:loan', 'deleteme'" | hbase shell
echo "put 'HBaseSamples', 'rowToDelete', 'metrics:loan', 'deleteme'" | hbase shell
echo "put 'HBaseSamples', 'anotherRow', 'metrics:keepMe', 'keepMe'" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.DeleteExample"

echo "disable 'NewTable'" | hbase shell
echo "drop 'NewTable'" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.CreateTableExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.DropTableExample"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ScanExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ScanCachingExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ValueFilterExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.FilterListExample"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar \
hbase.tableDesign.TableDesignExample"

log "############################################
# MapReduce Samples
############################################"
execStep "yarn jar $PLAY_AREA/HadoopSamples.jar \
mr.wordcount.StartsWithCountJob \
/training/data/hamlet.txt \
/training/playArea/wordCount" "/training/playArea/wordCount" "$PLAY_AREA/"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar \
mr.wordcount.StartWithCountJob_HBase \
-libjars $HBASE_HOME/hbase-0.92.1-cdh4.0.0-security.jar"

rm -rf $PLAY_AREA/wordCountOutput/
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar \
mr.wordcount.StartsWithCountJob \
-D mapreduce.framework.name=local \
-fs file:/// \
/home/hadoop/Training/exercises/sample_data \
$PLAY_AREA/wordCountOutput/"
rm -rf $PLAY_AREA/wordCountOutput/

rm -rf $PLAY_AREA/wordCountOutput/
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar \
mr.wordcount.StartsWithCountJob \
-D mapreduce.framework.name=local \
file:/home/hadoop/Training/exercises/sample_data \
file:$PLAY_AREA/wordCountOutput/"
rm -rf $PLAY_AREA/wordCountOutput/

rm -rf $PLAY_AREA/wordCountOutput/
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar \
mr.wordcount.StartsWithCountJob \
-conf $PLAY_AREA/local/run-local-config.xml \
/home/hadoop/Training/exercises/sample_data \
$PLAY_AREA/wordCountOutput/"
rm -rf $PLAY_AREA/wordCountOutput/

execCommand "yarn jar $PLAY_AREA/Solutions.jar \
mapRed.inputAndOutput.UniqueCounterTool \
-Dmapred.map.child.log.level=DEBUG"

execStep "yarn jar $PLAY_AREA/HadoopSamples.jar
mr.wordcount.StartWithCountJob_HBaseInput /training/playArea/wordCount" "/training/playArea/wordCount" "$PLAY_AREA"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.wordcount.StartWithCountJob_HBase"

execStep "yarn jar $PLAY_AREA/HadoopSamples.jar
mr.wordcount.StartsWithCountJob_UserCounters
/training/data/hamlet.txt /training/playArea/wordCount/" "/training/playArea/wordCount/" "$PLAY_AREA"

execStep "yarn jar $PLAY_AREA/HadoopSamples.jar \
mr.wordcount.StartsWithCountJob_DistCache \
-files $PLAY_AREA/data/startWithExcludeFile.txt \
/training/data/hamlet.txt \
/training/playArea/wordCount/" "/training/playArea/wordCount/" "$PLAY_AREA"

execStep "yarn jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-*.jar \
-D mapred.job.name='Count Job via Streaming' \
-files $HADOOP_SAMPLES_SRC/scripts/countMap.py,\
$HADOOP_SAMPLES_SRC/scripts/countReduce.py \
-input /training/data/hamlet.txt \
-output /training/playArea/wordCount/ \
-mapper countMap.py \
-combiner countReduce.py \
-reducer countReduce.py" "/training/playArea/wordCount/" "$PLAY_AREA"

execStep "yarn jar $PLAY_AREA/HadoopSamples.jar \
mr.workflows.MostSeenStartLetterJobControl \
/training/data/hamlet.txt \
/training/playArea/wordCount" "/training/playArea/wordCount" "$PLAY_AREA"

log "############################################
# Oozie Samples
############################################"
cd $PLAY_AREA/oozie/mostSeenLetter-oozieWorkflow
execCommand "runOozieWorkflow.sh mostSeenLetter-oozieWorkflow"
log "Kicked off Oozie workflow, will wait for completion for 60 seconds"
sleep 60

log "############################################
# Pig Samples
############################################"

execStep "pig $PLAY_AREA/pig/scripts-samples/MostSeenStartLetter.pig" "/training/playArea/pig/mostSeenLetterOutput" "$PLAY_AREA"
execCommand "pig $PLAY_AREA/pig/scripts-samples/InnerJoin.pig"
execCommand "pig $PLAY_AREA/pig/scripts-samples/InnerJoinWithMultipleKeys.pig"
execCommand "pig $PLAY_AREA/pig/scripts-samples/LeftOuterJoin.pig"
execCommand "pig $PLAY_AREA/pig/scripts-samples/Cogroup.pig"
execCommand "pig $PLAY_AREA/pig/scripts-samples/CogroupInner.pig"
cd $PLAY_AREA/
execCommand "pig $PLAY_AREA/pig/scripts-samples/CustomFilter.pig"
execCommand "pig $PLAY_AREA/pig/scripts-samples/CustomFilter-NoSchema.pig"
execCommand "pig $PLAY_AREA/pig/scripts-samples/CustomFilter-WithSchema.pig"


log "############################################
# Solutions
############################################"

#HDFS
execCommand "yarn jar $PLAY_AREA/Solutions.jar hdfs.javaAPI.Exercise1"
execCommand "yarn jar $PLAY_AREA/Solutions.jar hdfs.javaAPI.Exercise1a"
execCommand "yarn jar $PLAY_AREA/Solutions.jar hdfs.javaAPI.Exercise2"

#HBase
execCommand "yarn jar $PLAY_AREA/Solutions.jar hbase.javaAPI.JavaAPISolution"
execCommand "yarn jar $PLAY_AREA/Solutions.jar hbase.javaAPIAdvanced.JavaAPIAdvancedSolution"
execCommand "yarn jar $PLAY_AREA/Solutions.jar hbase.keyDesign.TableDesignDriver"

#MapRed
execStep "yarn jar $PLAY_AREA/Solutions.jar \
mapRed.firstJob.WordCountTool \
/training/data/war_and_peace.txt \
/training/exercises/mapRed/firstJob/ex1" "/training/exercises/mapRed/firstJob/ex1" "$PLAY_AREA"

execStep "yarn jar $PLAY_AREA/Solutions.jar \
mapRed.firstJob.LengthDividerCountTool \
/training/data/war_and_peace.txt \
/training/exercises/mapRed/firstJob/ex2" "/training/exercises/mapRed/firstJob/ex2" "$PLAY_AREA"

execCommand "yarn jar $PLAY_AREA/Exercises.jar mapRed.runningJobs.ExpectProperty -Dtraining.prop=hi"
execCommandExpectReturnCode "yarn jar $PLAY_AREA/Exercises.jar mapRed.runningJobs.ExpectClassOnClient" 1
execStep "yarn jar $PLAY_AREA/Exercises.jar mapRed.runningJobs.ExpectClassOnTask \
-libjars $PLAY_AREA/HadoopSamples.jar \
/training/data/hamlet.txt /training/playArea/ExpectClassOnTask" "/training/playArea/ExpectClassOnTask" "$PLAY_AREA"


# Solution for InputOutput Exercises
execStep "yarn jar $PLAY_AREA/Solutions.jar mapRed.inputAndOutput.CountTokens \
/training/data/hamlet.txt /training/playArea/InputOutput/CountTokens" "/training/playArea/InputOutput" "$PLAY_AREA/"

# Solution for InputOutput Exercises
execCommand "yarn jar $PLAY_AREA/Solutions.jar mapRed.inputAndOutput.UniqueCounterTool"
echo "truncate 'Exercise_InputAndOutput_Result'" | hbase shell

# Solution for features Exercises - counters
execCommand "yarn jar $PLAY_AREA/Solutions.jar mapRed.features.UniqueCounterTool"
echo "truncate 'Exercise_InputAndOutput_Result'" | hbase shell

# Solution for features Exercises - Distributed Cache
hdfs dfs -rm -r /training/playArea/LineSampler
execStep "yarn jar $PLAY_AREA/Solutions.jar mapRed.features.LineSamplerTool \
-files $PLAY_AREA/exercises/mapRed/tokensToRetain.txt \
/training/data/hamlet.txt /training/playArea/LineSampler" "/training/playArea/LineSampler" "$PLAY_AREA/"

# Jobs on YARN Exercises
hdfs dfs -rm -r /training/playArea/JobsOnYarn
execCommandExpectReturnCode "yarn jar $PLAY_AREA/Exercises.jar mapRed.jobOnYARN.JobWithFailures \
/training/data/hamlet.txt /training/playArea/JobsOnYarn" 1
hdfs dfs -rm -r /training/playArea/JobsOnYarn

# Streaming Job 1
execCommand "cat $HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/inputTest.txt | \
$HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/CountUniqueMapper.py | \
sort | $HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/CountUniqueReducer.py"

execStep "yarn jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-*.jar \
-D mapred.job.name='Count Job via Streaming' \
-files $HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/CountUniqueMapper.py,\
$HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/CountUniqueReducer.py \
-input /training/data/war_and_peace.txt \
-output /training/playArea/streaming/CountUnique \
-mapper CountUniqueMapper.py \
-combiner CountUniqueReducer.py \
-reducer CountUniqueReducer.py" "/training/playArea/streaming/CountUnique" "$PLAY_AREA/"

# Streaming Job 2
execCommand "cat $HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/inputTest.txt | \
$HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/LengthDividerMapper.py | \
sort | $HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/CountUniqueReducer.py"
execStep "yarn jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-*.jar \
-D mapred.job.name='Count Job via Streaming' \
-files $HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/LengthDividerMapper.py,\
$HADOOP_SOLUTIONS_SRC/resources/mapRed/streaming/CountUniqueReducer.py \
-input /training/data/war_and_peace.txt \
-output /training/playArea/streaming/LengthDivider \
-mapper LengthDividerMapper.py \
-combiner CountUniqueReducer.py \
-reducer CountUniqueReducer.py" "/training/playArea/streaming/LengthDivider" "$PLAY_AREA/"

# Worflow Exercise Solution - JobControl
execStep "yarn jar $PLAY_AREA/Solutions.jar mapRed.workflows.JobControlWorkflow \
/training/data/hamlet.txt /training/playArea/JobControlWorkflow" "/training/playArea/JobControlWorkflow" "$PLAY_AREA/"

# Oozie Workflow
execStep "/home/hadoop/Training/scripts/runOozieWorkflow.sh $PLAY_AREA/oozie/mostSeenLetter-oozieWorkflow" "/training/playArea/oozieWorkflow" "$PLAY_AREA/"

# pig first lecture solution
execStep "pig MostOccuredTokens.pig" "/training/playArea/pig/mostOccuredTokens" "$PLAY_AREA/pig/scripts-solutions"

# pig second lectuture solution
execStep "pig BookJoin.pig" "/training/exercises/pig/bookPurchases" "$PLAY_AREA/pig/scripts-solutions"
execStep "pig BookCogroup.pig" "/training/exercises/pig/bookPurchases" "$PLAY_AREA/pig/scripts-solutions"

printStats
