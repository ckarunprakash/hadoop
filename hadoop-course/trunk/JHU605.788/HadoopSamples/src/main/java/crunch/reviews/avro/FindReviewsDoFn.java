package crunch.reviews.avro;

import static mr.reviews.ReviewJob.PROP_FIND_VALUE;

import java.util.Arrays;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.commons.lang.Validate;
import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;
import org.apache.crunch.Pair;

public class FindReviewsDoFn extends DoFn<ReviewAvro, Pair<ReviewKeyAvro, ReviewReportAvro>> {
    private static final long serialVersionUID = 1L;

    transient private ReviewKeyAvro reviewKey;
    transient private ReviewReportAvro report;
    transient private List<String> valuesToLookFor;
    
    @Override
    public void initialize() {
        String keywords = getConfiguration().get(PROP_FIND_VALUE);
        Validate.notEmpty(keywords, "You must provide value to find via [" + PROP_FIND_VALUE + "]");
        valuesToLookFor = Arrays.asList(keywords.split(","));
        reviewKey  = new ReviewKeyAvro();
        report = new ReviewReportAvro();
    }

    @Override
    public void process(ReviewAvro input, Emitter<Pair<ReviewKeyAvro, ReviewReportAvro>> emitter) {
        String inputText = input.getText().toString();
        for (String valueToLookFor : valuesToLookFor) {
            if (isValueIn(valueToLookFor, inputText)) {
                updateKey(input, valueToLookFor);
                updateValue(input, valueToLookFor);
                emitter.emit(Pair.of(reviewKey, report));
            }
        }
    }
    
    private void updateValue(ReviewAvro review, String valueToLookFor) {
        report.setNumReviews(1);
        report.setKeyword(valueToLookFor);
        report.setReviews(Arrays.asList(review));
        report.setUser(review.getUser());
    }

    private void updateKey(ReviewAvro review, String valueToLookFor) {
        reviewKey.setKeyword(valueToLookFor);
        reviewKey.setUser(review.getUser());
    }
    
    private boolean isValueIn(String valueToLookFor, String content) {
        // this logic can be as intricate as it needs to be
        return content.contains(valueToLookFor);
    }

}
