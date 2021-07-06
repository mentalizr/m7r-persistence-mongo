package org.mentalizr.persistence.mongo.exerciseStatus;

import org.bson.Document;
import org.mentalizr.persistence.mongo.formData.FormData;
import org.mentalizr.persistence.mongo.formData.FormDataConverter;

import java.util.ArrayList;
import java.util.List;

public class ExerciseStatusSubmittedConverter {

    public static Document convert(ExerciseStatusSubmitted exerciseStatusSubmitted) {

        Document document = new Document(ExerciseStatusSubmitted.USER_ID, exerciseStatusSubmitted.getUserId())
                .append(ExerciseStatusSubmitted.CONTENT_ID, exerciseStatusSubmitted.getContentId())
                .append(ExerciseStatusSubmitted.TS, exerciseStatusSubmitted.getTs())
                .append(ExerciseStatusSubmitted.TS_STRING, exerciseStatusSubmitted.getTsString())
                .append(ExerciseStatusSubmitted.NOTIFICATION_TS_LIST, exerciseStatusSubmitted.getNotificationTs());

        return document;
    }

    public static ExerciseStatusSubmitted convert(Document document) {

        String userId = document.getString(ExerciseStatusSubmitted.USER_ID);
        String contentId = document.getString(ExerciseStatusSubmitted.CONTENT_ID);
        Long ts = document.getLong(ExerciseStatusSubmitted.TS);
        String tsString = document.getString(ExerciseStatusSubmitted.TS_STRING);
        List<String> notificationTs = document.getList(ExerciseStatusSubmitted.NOTIFICATION_TS_LIST, String.class);

        return new ExerciseStatusSubmitted(
                contentId,
                userId,
                ts,
                tsString,
                notificationTs
        );

    }

//    public static void main(String[] args) {
//
//        ExerciseStatusSubmitted exerciseStatusSubmitted = new ExerciseStatusSubmitted();
//        Document document = convert(exerciseStatusSubmitted);
//        System.out.println(document);
//
//        ExerciseStatusSubmitted exerciseStatusSubmittedBack = convert(document);
//        System.out.println(exerciseStatusSubmittedBack);
//
//    }

}
