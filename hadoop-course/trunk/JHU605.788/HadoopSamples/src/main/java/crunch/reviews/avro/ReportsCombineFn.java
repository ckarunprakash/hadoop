package crunch.reviews.avro;

import java.util.ArrayList;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.crunch.CombineFn;
import org.apache.crunch.Emitter;
import org.apache.crunch.Pair;

public class ReportsCombineFn extends CombineFn<ReviewKeyAvro, ReviewReportAvro> {
    private static final long serialVersionUID = 1L;

    transient private ReviewReportAvro combined;
    @Override
    public void initialize() {
        combined = new ReviewReportAvro();
    }
    @Override
    public void process(Pair<ReviewKeyAvro, Iterable<ReviewReportAvro>> toCombine,
            Emitter<Pair<ReviewKeyAvro, ReviewReportAvro>> emitter) {
        
        List<ReviewAvro> collected = new ArrayList<ReviewAvro>();
        for (ReviewReportAvro avroVal : toCombine.second()) {
            collected.addAll(avroVal.getReviews());
        }
        
        ReviewKeyAvro key = toCombine.first();
        
        combined.setKeyword(key.getKeyword());
        combined.setUser(key.getUser());
        combined.setNumReviews(collected.size());
        combined.setReviews(collected);
        
        emitter.emit(Pair.of(key, combined));
    }

}
