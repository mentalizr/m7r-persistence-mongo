package org.mentalizr.persistence.mongo.exerciseStatus;

import org.mentalizr.persistence.mongo.formData.FormData;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ExerciseStatusSubmitted {

    public static final String USER_ID = "userId";
    public static final String CONTENT_ID = "contentId";
    public static final String TS = "ts";
    public static final String TS_STRING = "tsString";
    public static final String NOTIFICATION_TS_LIST = "notificationTsList";

    private String userId;
    private String contentId;
    private long ts;
    private String tsString;
    private List<String> notificationTs;


    public ExerciseStatusSubmitted(String userId, String contentId) {
        this.userId = userId;
        this.contentId = contentId;
        this.ts = Instant.now().toEpochMilli();
        this.tsString = this.getTimestampAsISO();
        this.notificationTs = new ArrayList<>();
    }

    public ExerciseStatusSubmitted(String contentId, String userId, long ts, String tsString, List<String> notificationTs) {
        this.userId = userId;
        this.contentId = contentId;
        this.ts = ts;
        this.tsString = tsString;
        this.notificationTs = notificationTs;
    }

    private String getTimestampAsISO() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        return zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public long getTs() {
        return this.ts;
    }

    public String getTsString() {
        return tsString;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getContentId() {
        return this.contentId;
    }

    public List<String> getNotificationTs() {
        return this.notificationTs;
    }

    public void addNotificationTsNow() {
        this.notificationTs.add(this.getTimestampAsISO());
    }

    @Override
    public String toString() {
        return ExerciseStatusSubmitted.class.getSimpleName() + "{"
                + "', userId='" + this.userId
                + "', contentId='" + this.contentId
                + "ts='" + this.ts
                + "', tsString='" + this.tsString
                + "', notificationTs={" + this.notificationTs.toString() + "}";
    }

}
