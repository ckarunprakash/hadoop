#!/bin/bash
###################################
#  Upload Oozie job to HDFS and invoke it
#  Example: $ ./runOozieWorkflow.sh $PLAY_AREA/oozie/oozie-exercise-workflow
###################################
if [ $# != 1 ]; then
   echo "Usage: runOozieWorkflow.sh workflowPath"
   echo "  ex:  runOozieWorkflow.sh $PLAY_AREA/oozie/oozie-exercise-workflow"
   exit
fi

oozieAppLocation=$1
appName=`basename $oozieAppLocation`
echo $appName

if [ -d $oozieAppLocation ]; then
   echo "Deploying and running [$appName] application"
   hdfs dfs -rm -r $appName
   hdfs dfs -put $oozieAppLocation $appName
   oozie job -config $oozieAppLocation/job.properties -run
else
   echo "Directory [$oozieAppLocation] does not exist"
   exit
fi

