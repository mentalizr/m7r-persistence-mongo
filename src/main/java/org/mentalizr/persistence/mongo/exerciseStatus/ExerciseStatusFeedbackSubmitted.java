package org.mentalizr.persistence.mongo.exerciseStatus;

import java.util.List;

public class ExerciseStatusFeedbackSubmitted extends ExerciseStatusBase {

    private final String feedback;

    public ExerciseStatusFeedbackSubmitted(String userId, String contentId, String feedback) {
        super(userId, contentId);
        this.feedback = feedback;
    }

    public ExerciseStatusFeedbackSubmitted(String contentId, String userId, long ts, String tsString, List<String> notificationTs, String feedback) {
        super(userId, contentId, ts, tsString, notificationTs);
        this.feedback = feedback;
    }

    public String getFeedback() {
        return this.feedback;
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
        return "ExerciseStatusFeedbackSubmitted{" +
                "feedback='" + feedback + '\'' +
                ", userId='" + userId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", ts=" + ts +
                ", tsString='" + tsString + '\'' +
                ", notificationTs=" + notificationTs +
                '}';
    }
}
