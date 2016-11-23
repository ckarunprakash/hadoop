package mr.reviews.fsstruct;

import java.io.IOException;
import java.util.List;

import mr.reviews.fsstruct.support.ConfHelper;
import mr.reviews.fsstruct.support.Review;
import mr.reviews.fsstruct.support.XmlHelper;
import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class XmlProcessMapper extends Mapper<NullWritable, BytesWritable, ReviewKeyWritable, ReviewWritable> {
    private final ReviewKeyWritable keyWritable = new ReviewKeyWritable();
    private final ReviewWritable reviewWritable = new ReviewWritable();
    
    private List<String> valuesToLookFor;
    private final XmlHelper helper = new XmlHelper();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        ConfHelper confHelper = new ConfHelper(context.getConfiguration());
        valuesToLookFor = confHelper.getSearchTerms();
    }

    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        Review review = helper.convert(value.copyBytes());
        process(review, context);
    }
    public void process(Review review, Context context) throws IOException, InterruptedException {
        for (String valueToLookFor : valuesToLookFor) {
            if (isValueIn(valueToLookFor, review.getText())) {
                updateWritables(valueToLookFor, review);
                context.write(keyWritable, reviewWritable);
            }
        }
    }

    private boolean isValueIn(String valueToLookFor, String text) {
        // this logic can be as intricate as it needs to be
        return text.contains(valueToLookFor);
    }

    private void updateWritables(String valueToLookFor, Review review) {
        keyWritable.setKeyword(valueToLookFor);
        keyWritable.setAuthor(review.getUser());
        
        reviewWritable.setAuthor(review.getUser());
        reviewWritable.setContent(review.getText());
        reviewWritable.setPostedTimestamp(review.getTimestamp());
    }
}