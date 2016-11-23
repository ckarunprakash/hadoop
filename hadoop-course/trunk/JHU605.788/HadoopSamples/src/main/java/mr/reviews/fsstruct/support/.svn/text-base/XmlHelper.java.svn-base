package mr.reviews.fsstruct.support;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlHelper {

    
    
    public Review convert(byte [] xml) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Review.class);
            Unmarshaller un = jc.createUnmarshaller();
            Review review = (Review) un.unmarshal(new ByteArrayInputStream(xml));
            return review;
        } catch (JAXBException je) {
            throw new RuntimeException("Failed to parse [" + new String(xml) + "]", je);
        }
    }

//    public static void main(String[] args) throws JAXBException, IOException {
//        File f = new File("/home/hadoop/Training/play_area/data/reviews-xml/review-1398.xml");
//        String content = FileUtils.readFileToString(f);
//    }

}
