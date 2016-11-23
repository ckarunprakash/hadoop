#!/bin/sh

source ./jobExecSupport.sh
source ./sharedProps.sh

execCommand "yarn jar $PLAY_AREA/HadoopSamples.jar mr.reviews.ReviewJob -conf $resources/mr/reviews/ReviewsJob-smallInput.xml"

printStats