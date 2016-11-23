package various;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

public class GenXmlFiles {

    @XmlRootElement
    static class Review {
        private String user;
        private String text;
        private Long timestamp;

        public Review() {
        }

        public Review(String user, String text, Long timestamp) {
            this.user = user;
            this.text = text;
            this.timestamp = timestamp;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }

    /**
     * @param args
     * @throws JAXBException 
     * @throws IOException 
     */
    public static void main(String[] args) throws JAXBException, IOException {
        String from = "/home/hadoop/Training/play_area/data/reviews";
        File to = new File("/home/hadoop/Training/play_area/data/reviews-xml/");
        
        int i =0;
        for (File f : new File(from).listFiles()) {
            Scanner scanner = new Scanner(new FileInputStream(f));
            try {
                while (scanner.hasNextLine()) {
                    FileWriter fileWriter = new FileWriter(new File(to, "review-" + i + ".xml"));
                    String line = scanner.nextLine();
                    String[] split = line.split(",");
                    Review review = new Review(split[0], split[1], Long.parseLong(split[2]));
                    
                    JAXBContext jc = JAXBContext.newInstance(Review.class);
                    Marshaller m = jc.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    
                    m.marshal(review, fileWriter);
                    fileWriter.close();
                    i++;
                }
            } finally {
                scanner.close();
            }
        }

    }

}
