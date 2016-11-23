#/bin/bash
cd $OOZIE_HOME/bin
./oozie-stop.sh
cd $HBASE_HOME/bin
./stop-hbase.sh
sleep 2
cd $HADOOP_HOME/sbin
./mr-jobhistory-daemon.sh stop historyserver
./stop-yarn.sh
./stop-dfs.sh


