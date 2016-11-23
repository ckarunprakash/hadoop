#/bin/bash

# purpose of this script is to delete "temporary" directories
# in order to minimize the size of VM
rm -rf /home/hadoop/.m2/repository
rm -rf /home/hadoop/Training/hadoop_work/mapred/nodemanager
rm -rf /home/hadoop/Training/logs/*

# Oozie
# change the following prop in oozie-site.xml to 0 then (1) start oozie (2) wait for clean up (3) stopt oozie (4) change property back
#  <property>
#        <name>oozie.service.PurgeService.older.than</name>
#        <value>0</value>
#        <description>
#            Jobs older than this value, in days, will be purged by the PurgeService.
#        </description>
#    </property>

# history server
# change the following property in mapred-site.xml then (1) start history server (2) wait for it to clean its history (3) stop it
# <property>
#     <name>mapreduce.jobhistory.joblist.cache.size</name>
#     <value>100</value>
#     <description>Number of Jobs Histor Server keeps</description>
#   </property>

# Clean up stuff in the /tmp directory on hdfs
hdfs dfs -rm -r /tmp/t*
hdfs dfs -rm -r /tmp/hive*
hdfs dfs -rm -r /tmp/hadoop-yarn

