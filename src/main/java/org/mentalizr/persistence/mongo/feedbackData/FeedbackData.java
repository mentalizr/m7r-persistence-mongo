package org.mentalizr.persistence.mongo.feedbackData;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeedbackData {

    public static final String USER_ID = "userId";
    public static final String CONTENT_ID = "contentId";
    public static final String FEEDBACK = "feedback";
    public static final String TIMESTAMP_SUBMITTED_BY_THERAPIS = "timestamp";

    private String userId;
    private String contentId;
    private String feedback;
    private long timestamp;

    public FeedbackData() {

    }

    public FeedbackData(String userId, String contentId, String feedback, long timestamp) {
        this.userId = userId;
        this.contentId = contentId;
        this.feedback = feedback;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getContentId() {
        return contentId;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public long getTimeStamp() {
        return this.timestamp;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
