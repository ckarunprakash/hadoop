#!/bin/bash

# locations of logs
logBase="/home/hadoop/Training/play_area/jobs/"
log=$logBase"log-allJobs.txt"
errorLog=$logBase"log-failedCommands.txt"

# keep stats
startTime=`date '+%Y%m%d%H%M%S'`
totalJobs=0
numFailed=0

# prepare
mkdir -p $logBase
rm $log
rm $errorLog

# support functions
echo "-----------------------"
echo "Logs: [$log]"
echo "Errors: [$errorLog]"
echo "-----------------------"

function log(){
   echo "$1" >> $log
}
function logFailure(){
   echo "$1" >> $errorLog
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
