package org.mentalizr.persistence.mongo.exerciseStatus;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class ExerciseStatusBase {

    public static final String USER_ID = "userId";
    public static final String CONTENT_ID = "contentId";
    public static final String TS = "ts";
    public static final String TS_STRING = "tsString";
    public static final String NOTIFICATION_TS_LIST = "notificationTsList";

    protected final String userId;
    protected final String contentId;
    protected final long ts;
    protected final String tsString;
    protected List<String> notificationTs;


    public ExerciseStatusBase(String userId, String contentId) {
        this.userId = userId;
        this.contentId = contentId;
        this.ts = Instant.now().toEpochMilli();
        this.tsString = this.getTimestampAsISO();
        this.notificationTs = new ArrayList<>();
    }

    public ExerciseStatusBase(String contentId, String userId, long ts, String tsString, List<String> notificationTs) {
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
        return ExerciseStatusBase.class.getSimpleName() + "{"
                + "', userId='" + this.userId
                + "', contentId='" + this.contentId
                + "ts='" + this.ts
                + "', tsString='" + this.tsString
                + "', notificationTs={" + this.notificationTs.toString() + "}";
    }

}
