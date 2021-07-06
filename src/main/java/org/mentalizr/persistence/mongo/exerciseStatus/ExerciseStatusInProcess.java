package org.mentalizr.persistence.mongo.exerciseStatus;

import java.util.List;

public class ExerciseStatusInProcess extends ExerciseStatusBase {

    private String feedback;

    public ExerciseStatusInProcess(String userId, String contentId) {
        super(userId, contentId);
        this.feedback = "";
    }

    public ExerciseStatusInProcess(String contentId, String userId, long ts, String tsString, List<String> notificationTs, String feedback) {
        super(userId, contentId, ts, tsString, notificationTs);
        this.feedback = feedback;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

//    @Override
//    public String toString() {
//        return ExerciseStatusInProcess.class.getSimpleName() + "{"
//                + "', userId='" + this.userId
//                + "', contentId='" + this.contentId
//                + "ts='" + this.ts
//                + "', tsString='" + this.tsString
//                + "', notificationTs={" + this.notificationTs.toString() + "}";
//    }


//    @Override
//    public String toString() {
//        return "ExerciseStatusInProcess{" +
//                "userId='" + userId + '\'' +
//                ", contentId='" + contentId + '\'' +
//                ", ts=" + ts +
//                ", tsString='" + tsString + '\'' +
//                ", notificationTs=" + notificationTs +
//                ", feedback='" + feedback + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "ExerciseStatusInProcess{" +
                "feedback='" + feedback + '\'' +
                ", userId='" + userId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", ts=" + ts +
                ", tsString='" + tsString + '\'' +
                ", notificationTs=" + notificationTs +
                '}';
    }
}
