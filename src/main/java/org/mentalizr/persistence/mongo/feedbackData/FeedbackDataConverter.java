package org.mentalizr.persistence.mongo.feedbackData;

import org.bson.Document;
import org.mentalizr.persistence.mongo.formData.FormData;
import org.mentalizr.persistence.mongo.formData.FormElementData;

import java.util.ArrayList;
import java.util.List;

public class FeedbackDataConverter {

    public static Document convert(FeedbackData feedbackData) {

        return new Document(FeedbackData.USER_ID, feedbackData.getUserId())
                .append(FeedbackData.CONTENT_ID, feedbackData.getContentId())
                .append(FeedbackData.FEEDBACK, feedbackData.getFeedback())
                .append(FeedbackData.TIMESTAMP_SUBMITTED_BY_THERAPIS, feedbackData.getTimeStamp());
    }

    public static FeedbackData convert(Document document) {

        String userId = document.getString(FeedbackData.USER_ID);
        String contentId = document.getString(FeedbackData.CONTENT_ID);
        String feedback = document.getString(FeedbackData.FEEDBACK);
        long timestamp = document.getLong(FeedbackData.TIMESTAMP_SUBMITTED_BY_THERAPIS);

        return new FeedbackData(userId, contentId, feedback, timestamp);

    }


}
