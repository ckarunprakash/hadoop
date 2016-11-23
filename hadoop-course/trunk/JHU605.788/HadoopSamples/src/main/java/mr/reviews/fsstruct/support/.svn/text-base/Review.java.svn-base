package mr.reviews.fsstruct.support;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement
public class Review {
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
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
