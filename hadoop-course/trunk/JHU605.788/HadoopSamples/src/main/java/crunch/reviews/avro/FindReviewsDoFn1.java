package crunch.reviews.avro;

import java.util.Arrays;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;
import org.apache.crunch.Pair;

public class FindReviewsDoFn1 extends DoFn<ReviewAvro, Pair<ReviewKeyAvro, ReviewReportAvro>> {
    private static final long serialVersionUID = 1L;

    transient private ReviewKeyAvro reviewKey;
    transient private ReviewReportAvro report;
    private List<String> valuesToLookFor;
    public FindReviewsDoFn1(List<String> valuesToLookFor){
        this.valuesToLookFor = valuesToLookFor;
    }
    
    @Override
    public void initialize() {
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
