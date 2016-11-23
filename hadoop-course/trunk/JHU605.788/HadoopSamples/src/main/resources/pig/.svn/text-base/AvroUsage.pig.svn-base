--AvroUsage.pig
REGISTER /usr/lib/pig/*.jar;
REGISTER /home/hadoop/Training/play_area/avroLibs/*.jar;

reviews = LOAD 'examples_input/reviews-avro/reviews.avro' USING org.apache.pig.piggybank.storage.avro.AvroStorage();
found = FILTER reviews BY text matches '.*affordable.*';
STORE found INTO '/training/playArea/pigAvro' USING org.apache.pig.piggybank.storage.avro.AvroStorage();