package org.mentalizr.persistence.mongo.formData;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import org.mentalizr.persistence.mongo.MongoDates;
import org.mentalizr.serviceObjects.frontend.patient.formData.*;

public class FormDataConverter {

    private static class ExerciseConverter {

        public static Document convert(ExerciseSO exerciseSO) {
            Document document = new Document(ExerciseSO.SENT, exerciseSO.isSent());
            MongoDates.append(document, ExerciseSO.LAST_MODIFIED_TIMESTAMP, exerciseSO.getLastModifiedTimestamp());
            document.append(ExerciseSO.LAST_MODIFIED_TIMESTAMP, exerciseSO.getLastModifiedTimestamp())
                    .append(ExerciseSO.SEEN_BY_THERAPIST, exerciseSO.isSeenByTherapist())
                    .append(ExerciseSO.SEEN_BY_THERAPIST_TIMESTAMP, exerciseSO.getSeenByTherapistTimestamp());
            return document;

//            return new Document(ExerciseSO.SENT, exerciseSO.isSent())
//                    .append(ExerciseSO.LAST_MODIFIED_TIMESTAMP, ISO)
//                    .append(ExerciseSO.LAST_MODIFIED_TIMESTAMP, exerciseSO.getLastModifiedTimestamp())
//                    .append(ExerciseSO.SEEN_BY_THERAPIST, exerciseSO.isSeenByTherapist())
//                    .append(ExerciseSO.SEEN_BY_THERAPIST_TIMESTAMP, exerciseSO.getSeenByTherapistTimestamp());
        }

        public static ExerciseSO convert(Document document) {
            boolean sent = document.getBoolean(ExerciseSO.SENT);
            String lastModifiedTimestamp = document.getString(ExerciseSO.LAST_MODIFIED_TIMESTAMP);
            boolean seenByTherapist = document.getBoolean(ExerciseSO.SEEN_BY_THERAPIST);
            String seenByTherapistTimestamp = document.getString(ExerciseSO.SEEN_BY_THERAPIST_TIMESTAMP);

            return new ExerciseSO(sent, lastModifiedTimestamp, seenByTherapist, seenByTherapistTimestamp);
        }
    }

    private static class FormElementDataConverter {

        public static Document convert(FormElementDataSO formElementDataSO) {
            return new Document(FormElementDataSO.FORM_ELEMENT_ID, formElementDataSO.getFormElementId())
                    .append(FormElementDataSO.FORM_ELEMENT_TYPE, formElementDataSO.getFormElementType())
                    .append(FormElementDataSO.FORM_ELEMENT_VALUE, formElementDataSO.getFormElementValue());
        }

        public static FormElementDataSO convert(Document document) {
            FormElementDataSO formElementDataSO = new FormElementDataSO();
            formElementDataSO.setFormElementType(document.getString(FormElementDataSO.FORM_ELEMENT_TYPE));
            formElementDataSO.setFormElementId(document.getString(FormElementDataSO.FORM_ELEMENT_ID));
            formElementDataSO.setFormElementValue(document.getString(FormElementDataSO.FORM_ELEMENT_VALUE));
            return formElementDataSO;
        }
    }

    private static class FeedbackConverter {

        public static Document convert(FeedbackSO feedbackSO) {
            return new Document(FeedbackSO.TEXT, feedbackSO.getText())
                    .append(FeedbackSO.CREATED_TIMESTAMP, feedbackSO.getCreatedTimestamp())
                    .append(FeedbackSO.THERAPIST_ID, feedbackSO.getTherapistId())
                    .append(FeedbackSO.SEEN_BY_PATIENT, feedbackSO.isSeenByPatient())
                    .append(FeedbackSO.SEEN_BY_PATIENT_TIMESTAMP, feedbackSO.getSeenByPatientTimestamp());
        }

        public static FeedbackSO convert(Document document) {
            FeedbackSO feedbackSO = new FeedbackSO();
            feedbackSO.setText(document.getString(FeedbackSO.TEXT));
            feedbackSO.setCreatedTimestamp(document.getString(FeedbackSO.CREATED_TIMESTAMP));
            feedbackSO.setTherapistId(document.getString(FeedbackSO.THERAPIST_ID));
            feedbackSO.setSeenByPatient(document.getBoolean(FeedbackSO.SEEN_BY_PATIENT));
            feedbackSO.setSeenByPatientTimestamp(document.getString(FeedbackSO.SEEN_BY_PATIENT_TIMESTAMP));
            return feedbackSO;
        }
    }

    public static Document convert(FormDataSO formDataSO) {

        Document document = new Document(FormDataSO.USER_ID, formDataSO.getUserId())
                .append(FormDataSO.CONTENT_ID, formDataSO.getContentId())
                .append(FormDataSO.EDITABLE, formDataSO.isEditable());

        if (FormDataSOs.isExercise(formDataSO)) {
            Document exerciseDocument = ExerciseConverter.convert(formDataSO.getExerciseSO());
            document.append(FormDataSO.EXERCISE, exerciseDocument);
        }

        List<Document> formElementDataList = new ArrayList<>();
        for (FormElementDataSO formElementDataSO : formDataSO.getFormElementDataList()) {
            Document formElementDataDocument = FormElementDataConverter.convert(formElementDataSO);
            formElementDataList.add(formElementDataDocument);
        }
        document.append(FormDataSO.FORM_ELEMENT_DATA_LIST, formElementDataList);

        if (FormDataSOs.hasFeedback(formDataSO)) {
            Document feedbackDocument = FeedbackConverter.convert(formDataSO.getFeedbackSO());
            document.append(FormDataSO.FEEDBACK, feedbackDocument);
        }

        return document;
    }

    public static FormDataSO convert(Document document) {

        String userId = document.getString(FormDataSO.USER_ID);
        String contentId = document.getString(FormDataSO.CONTENT_ID);
        boolean editable = document.getBoolean(FormDataSO.EDITABLE);

        FormDataSO formDataSO = new FormDataSO();
        formDataSO.setUserId(userId);
        formDataSO.setContentId(contentId);
        formDataSO.setEditable(editable);

        Document exerciseDocument = document.get(FormDataSO.EXERCISE, Document.class);
        if (exerciseDocument != null) {
            ExerciseSO exerciseSO = ExerciseConverter.convert(exerciseDocument);
            formDataSO.setExerciseSO(exerciseSO);
        }

        List<Document> formElementDataDocumentList = document.getList(FormDataSO.FORM_ELEMENT_DATA_LIST, Document.class);
        List<FormElementDataSO> formElementDataSOList = new ArrayList<>();
        formDataSO.setFormElementDataList(formElementDataSOList);

        for (Document formElementDataDocument : formElementDataDocumentList) {
            FormElementDataSO formElementDataSO = FormElementDataConverter.convert(formElementDataDocument);
            formElementDataSOList.add(formElementDataSO);
        }

        Document feedbackDocument = document.get(FormDataSO.FEEDBACK, Document.class);
        if (feedbackDocument != null) {
            FeedbackSO feedbackSO = FeedbackConverter.convert(feedbackDocument);
            formDataSO.setFeedbackSO(feedbackSO);
        }

        return formDataSO;
    }

}
