#/bin/bash
cd $HADOOP_HOME/sbin
./start-dfs.sh

# wait for safe mode to turn off before starting the rest of the products
function checkSafemode(){
  hdfs dfsadmin -safemode get | grep ON
}
echo "Waiting for safemode to turn OFF"
while [[ "`checkSafemode`" != "" ]]; do
  echo "..."
  sleep 5
done
echo "Safemode is off! Continuing..."

./start-yarn.sh
./mr-jobhistory-daemon.sh start historyserver
cd $HBASE_HOME/bin
./start-hbase.sh
cd $OOZIE_HOME/bin
./oozie-start.sh
