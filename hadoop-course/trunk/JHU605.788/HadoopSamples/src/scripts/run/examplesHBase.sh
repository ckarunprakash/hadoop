#!/bin/bash

source ./jobExecSupport.sh
source ./sharedProps.sh

#JAVA API BASICS LECTURE
yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ConstructHTable
echo "create 'HBaseSamples', {NAME=>'test'}, {NAME=>'metrics'}" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.PutExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.GetExample"

echo "put 'HBaseSamples', 'anotherRow', 'metrics:loan', 'deleteme'" | hbase shell
echo "put 'HBaseSamples', 'rowToDelete', 'metrics:loan', 'deleteme'" | hbase shell
echo "put 'HBaseSamples', 'anotherRow', 'metrics:keepMe', 'keepMe'" | hbase shell
echo "scan 'HBaseSamples', {COLUMNS=>['metrics:loan','metrics:keepMe']}" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.DeleteExample"
echo "scan 'HBaseSamples', {COLUMNS=>['metrics:loan','metrics:keepMe']}" | hbase shell

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.CreateTableExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.DropTableExample"
echo "disable 'HBaseSamples'" | hbase shell
echo "drop 'HBaseSamples'" | hbase shell


#JAVA SCAN API LECTURE
echo "create 'HBaseSamples', {NAME=>'test'}, {NAME=>'metrics'}, {NAME=>'columns'}" | hbase shell
echo "put 'HBaseSamples', 'row-01', 'metrics:counter', 'val1'" | hbase shell
echo "put 'HBaseSamples', 'row-02', 'metrics:counter', 'val2'" | hbase shell
echo "put 'HBaseSamples', 'row-03', 'metrics:counter', 'val3'" | hbase shell
echo "put 'HBaseSamples', 'row-04', 'metrics:counter', 'val4'" | hbase shell
echo "put 'HBaseSamples', 'row-05', 'metrics:counter', 'val5'" | hbase shell
echo "put 'HBaseSamples', 'row-06', 'metrics:counter', 'val6'" | hbase shell
echo "put 'HBaseSamples', 'row-07', 'metrics:counter', 'val7'" | hbase shell
echo "put 'HBaseSamples', 'row-08', 'metrics:counter', 'val8'" | hbase shell
echo "put 'HBaseSamples', 'row-09', 'metrics:counter', 'val9'" | hbase shell
echo "put 'HBaseSamples', 'row-10', 'metrics:counter', 'val10'" | hbase shell
echo "put 'HBaseSamples', 'row-11', 'metrics:counter', 'val11'" | hbase shell
echo "put 'HBaseSamples', 'row-12', 'metrics:counter', 'val12'" | hbase shell
echo "put 'HBaseSamples', 'row-13', 'metrics:counter', 'val13'" | hbase shell
echo "put 'HBaseSamples', 'row-14', 'metrics:counter', 'val14'" | hbase shell
echo "put 'HBaseSamples', 'row-15', 'metrics:counter', 'val15'" | hbase shell
echo "put 'HBaseSamples', 'row-16', 'metrics:counter', 'val16'" | hbase shell
echo "put 'HBaseSamples', 'row-17', 'metrics:counter', 'val17'" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ScanExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ScanCachingExample"

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ValueFilterExample"

echo "put 'HBaseSamples', 'batchExample_row_01', 'columns:col1', 'Row1col1'" | hbase shell
echo "put 'HBaseSamples', 'batchExample_row_01', 'columns:col2', 'Row1col2'" | hbase shell
echo "put 'HBaseSamples', 'batchExample_row_01', 'columns:col3', 'Row1col3'" | hbase shell
echo "put 'HBaseSamples', 'batchExample_row_01', 'columns:col4', 'Row1col4'" | hbase shell
echo "put 'HBaseSamples', 'batchExample_row_02', 'columns:col1', 'Row2col1'" | hbase shell
echo "put 'HBaseSamples', 'batchExample_row_02', 'columns:col2', 'Row2col2'" | hbase shell
echo "put 'HBaseSamples', 'batchExample_row_02', 'columns:col3', 'Row2col3'" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.ScanBatchingExample"
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.FilterListExample"
echo "disable 'HBaseSamples'" | hbase shell
echo "drop 'HBaseSamples'" | hbase shell

#HBASE Key Design
echo "create 'Blog_FlatAndWide', {NAME=>'entry'}" | hbase shell
echo "create 'Blog_TallAndNarrow', {NAME=>'entry'}" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar hbase.tableDesign.TableDesignExample"
echo "disable 'Blog_FlatAndWide'" | hbase shell
echo "drop 'Blog_FlatAndWide'" | hbase shell
echo "disable 'Blog_TallAndNarrow'" | hbase shell
echo "drop 'Blog_TallAndNarrow'" | hbase shell

#HBASE MapReduce
echo "create 'ReviewTable', {NAME=>'reviews',COMPRESSION=>'snappy'}" | hbase shell
echo "create 'ReviewReportTable', {NAME=>'reviewKeywordHits',COMPRESSION=>'snappy'}, {NAME=>'reviewKeywordReport',COMPRESSION=>'snappy'}" | hbase shell
echo "create 'ErrorTable', {NAME=>'errors',COMPRESSION=>'snappy'}" | hbase shell

#1st job
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.StoreReviewsToHBase -Dreport.input.path=examples_input/reviews-xml"
echo "count 'ReviewTable'" | hbase shell

#2nd job variations
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.FindReviewsByKeyword -Dreport.value='invaluable,affordable'"
echo "count 'ReviewReportTable'" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.ReviewsReportByUserKeyword -Dreport.value='invaluable,affordable'"
echo "count 'ReviewReportTable'" | hbase shell
echo "truncate 'ReviewReportTable'" | hbase shell
execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.fsstruct.hbase.ReviewsReportViaHTables -Dreport.value='invaluable,affordable'"
echo "count 'ReviewReportTable'" | hbase shell
echo "disable 'ReviewTable'" | hbase shell
echo "drop 'ReviewTable'" | hbase shell
echo "disable 'ReviewReportTable'" | hbase shell
echo "drop 'ReviewReportTable'" | hbase shell
echo "disable 'ErrorTable'" | hbase shell
echo "drop 'ErrorTable'" | hbase shell

printStats