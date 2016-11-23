#!/bin/bash

script=$(readlink -f "$0")
scriptPath=$(dirname "$script")
srcLoc="$scriptPath/../../main"
resources="${srcLoc}/resources/"
javaLoc="${srcLoc}/java/"


samplesJar=$scriptPath/../../../target/HadoopSamples-*.jar
repo="$HOME/.m2/repository"
avroMapRedJar="$repo/org/apache/avro/avro-mapred/1.7.4/avro-mapred-1.7.4-hadoop2.jar"

echo "--------------"
echo "Artifact Locations:"
echo "HadoopSamples.jar: $samplesJar"
echo "Resources path: $resources"
echo "Repo path: $repo"
echo "--------------"