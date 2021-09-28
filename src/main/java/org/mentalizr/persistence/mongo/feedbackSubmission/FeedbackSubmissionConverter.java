package org.mentalizr.persistence.mongo.feedbackSubmission;

import org.bson.Document;
import org.mentalizr.serviceObjects.frontend.therapist.feedbackSubmission.FeedbackSubmissionSO;

public class FeedbackSubmissionConverter {

    public static Document convert(FeedbackSubmissionSO feedbackData) {
        Document document = new Document(FeedbackSubmissionSO.USER_ID, feedbackData.getUserId());
        document.append(FeedbackSubmissionSO.CONTENT_ID, feedbackData.getContentId());
        document.append(FeedbackSubmissionSO.FEEDBACK, feedbackData.getFeedback());
        return document;
    }

    public static FeedbackSubmissionSO convert(Document document) {
        String userId = document.getString(FeedbackSubmissionSO.USER_ID);
        String contentId = document.getString(FeedbackSubmissionSO.CONTENT_ID);
        String feedback = document.getString(FeedbackSubmissionSO.FEEDBACK);

        FeedbackSubmissionSO feedbackSubmissionSO = new FeedbackSubmissionSO();
        feedbackSubmissionSO.setUserId(userId);
        feedbackSubmissionSO.setContentId(contentId);
        feedbackSubmissionSO.setFeedback(feedback);
        return feedbackSubmissionSO;
    }

}
