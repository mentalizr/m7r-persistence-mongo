package org.mentalizr.persistence.mongo.formData;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.junit.jupiter.api.Test;
import org.mentalizr.commons.Dates;
import org.mentalizr.serviceObjects.frontend.patient.formData.ExerciseSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FeedbackSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormElementDataSO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormDataConverterTest {

    private static final boolean outputJson = true;

    private FormDataSO createSimpleFormDataSO() {
        FormDataSO formDataSO = new FormDataSO();
        formDataSO.setUserId("userId");
        formDataSO.setContentId("contentId");

        List<FormElementDataSO> formElementDataSOList = new ArrayList<>();
        FormElementDataSO formElementDataSO = new FormElementDataSO();
        formElementDataSO.setFormElementId("elementId");
        formElementDataSO.setFormElementType("elementType");
        formElementDataSO.setFormElementValue("elementValue");
        formElementDataSOList.add(formElementDataSO);

        formDataSO.setFormElementDataList(formElementDataSOList);

        return formDataSO;
    }

    private void assertSimpleFormDataSO(FormDataSO formDataSO) {
        assertEquals("userId", formDataSO.getUserId());
        assertEquals("contentId", formDataSO.getContentId());
        assertNotNull(formDataSO.getFormElementDataList());
        List<FormElementDataSO> formElementDataSOList = formDataSO.getFormElementDataList();
        assertEquals(1, formElementDataSOList.size());
        FormElementDataSO formElementDataSO = formElementDataSOList.get(0);
        assertEquals("elementId", formElementDataSO.getFormElementId());
        assertEquals("elementType", formElementDataSO.getFormElementType());
        assertEquals("elementValue", formElementDataSO.getFormElementValue());
    }

    private String getSimpleFormDataSOAsJson() {
        return "{\n" +
                "  \"userId\": \"userId\",\n" +
                "  \"contentId\": \"contentId\",\n" +
                "  \"formElementDataList\": [\n" +
                "    {\n" +
                "      \"formElementId\": \"elementId\",\n" +
                "      \"formElementType\": \"elementType\",\n" +
                "      \"formElementValue\": \"elementValue\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

    }

    private FormDataSO createExerciseFormDataSO() {
        FormDataSO formDataSO = createSimpleFormDataSO();
        ExerciseSO exerciseSO = new ExerciseSO();
        exerciseSO.setSent(true);
        exerciseSO.setLastModifiedTimestamp("2021-09-27T10:47:01.443866Z");
        exerciseSO.setSeenByTherapist(true);
        exerciseSO.setSeenByTherapistTimestamp("");
        formDataSO.setExercise(exerciseSO);
        return formDataSO;
    }

    private void assertExerciseFormDataSO(FormDataSO formDataSO) {
        assertSimpleFormDataSO(formDataSO);
        assertNotNull(formDataSO.getExercise());
        ExerciseSO exerciseSO = formDataSO.getExercise();
        assertTrue(exerciseSO.isSent());
        assertEquals("2021-09-27T10:47:01.443Z", exerciseSO.getLastModifiedTimestamp());
        assertTrue(exerciseSO.isSeenByTherapist());
        assertEquals(Dates.epochAsISO(), exerciseSO.getSeenByTherapistTimestamp());
    }

    private void assertExerciseDocument(Document document) {
        Object exerciseObject = document.get("exercise");
        assertInstanceOf(Document.class, exerciseObject);
        Document exerciseDocument = (Document) exerciseObject;
        Object lastModifiedTimestampObject = exerciseDocument.get("lastModifiedTimestamp");
        assertInstanceOf(Date.class, lastModifiedTimestampObject);
    }

    private String getExerciseFormDataSOAsJson() {
        return "{\n" +
                "  \"userId\": \"userId\",\n" +
                "  \"contentId\": \"contentId\",\n" +
                "  \"exercise\": {\n" +
                "    \"sent\": true,\n" +
                "    \"lastModifiedTimestamp\": {\n" +
                "      \"$date\": \"2021-09-27T10:47:01.443Z\"\n" +
                "    },\n" +
                "    \"seenByTherapist\": true,\n" +
                "    \"seenByTherapistTimestamp\": {\n" +
                "      \"$date\": \"1970-01-01T00:00:00Z\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"formElementDataList\": [\n" +
                "    {\n" +
                "      \"formElementId\": \"elementId\",\n" +
                "      \"formElementType\": \"elementType\",\n" +
                "      \"formElementValue\": \"elementValue\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    private FormDataSO createFeedbackFormDataSO() {
        FormDataSO formDataSO = createExerciseFormDataSO();
        FeedbackSO feedbackSO = new FeedbackSO();
        feedbackSO.setText("text");
        feedbackSO.setCreatedTimestamp("2021-09-28T12:08:24.377406Z");
        feedbackSO.setTherapistId("therapistId");
        feedbackSO.setSeenByPatient(true);
        feedbackSO.setSeenByPatientTimestamp("");
        formDataSO.setFeedback(feedbackSO);
        return formDataSO;
    }

    private void assertFeedbackFormDataSO(FormDataSO formDataSO) {
        assertExerciseFormDataSO(formDataSO);
        assertNotNull(formDataSO.getFeedback());
        FeedbackSO feedbackSO = formDataSO.getFeedback();
        assertEquals("text", feedbackSO.getText());
        assertEquals("2021-09-28T12:08:24.377Z", feedbackSO.getCreatedTimestamp());
        assertEquals("therapistId", feedbackSO.getTherapistId());
        assertTrue(feedbackSO.isSeenByPatient());
        assertEquals(Dates.epochAsISO(), feedbackSO.getSeenByPatientTimestamp());
    }

    private String getFeedbackFormDataSOAsJson() {
        return "{\n" +
                "  \"userId\": \"userId\",\n" +
                "  \"contentId\": \"contentId\",\n" +
                "  \"exercise\": {\n" +
                "    \"sent\": true,\n" +
                "    \"lastModifiedTimestamp\": {\n" +
                "      \"$date\": \"2021-09-27T10:47:01.443Z\"\n" +
                "    },\n" +
                "    \"seenByTherapist\": true,\n" +
                "    \"seenByTherapistTimestamp\": {\n" +
                "      \"$date\": \"1970-01-01T00:00:00Z\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"formElementDataList\": [\n" +
                "    {\n" +
                "      \"formElementId\": \"elementId\",\n" +
                "      \"formElementType\": \"elementType\",\n" +
                "      \"formElementValue\": \"elementValue\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"feedback\": {\n" +
                "    \"text\": \"text\",\n" +
                "    \"createdTimestamp\": {\n" +
                "      \"$date\": \"2021-09-28T12:08:24.377Z\"\n" +
                "    },\n" +
                "    \"therapistId\": \"therapistId\",\n" +
                "    \"seenByPatient\": true,\n" +
                "    \"seenByPatientTimestamp\": {\n" +
                "      \"$date\": \"1970-01-01T00:00:00Z\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private String getPrettyJson(Document document) {
        JsonWriterSettings.Builder settingsBuilder = JsonWriterSettings.builder().indent(true);
        return document.toJson(settingsBuilder.build());
    }

    @Test
    void simple() {
        FormDataSO formDataSO = createSimpleFormDataSO();

        Document document = FormDataConverter.convert(formDataSO);
        String json = getPrettyJson(document);
        if (outputJson) System.out.println(json);
        String expectedJson = getSimpleFormDataSOAsJson();
        assertEquals(expectedJson, json);

        formDataSO = FormDataConverter.convert(document);
        assertSimpleFormDataSO(formDataSO);
    }

    @Test
    void exercise() {
        FormDataSO formDataSO = createExerciseFormDataSO();

        Document document = FormDataConverter.convert(formDataSO);

        assertExerciseDocument(document);

        String json = getPrettyJson(document);
        if (outputJson) System.out.println(json);
        String expectedJson = getExerciseFormDataSOAsJson();
        assertEquals(expectedJson, json);

        formDataSO = FormDataConverter.convert(document);
        assertExerciseFormDataSO(formDataSO);
    }

    @Test
    void feedback() {
        FormDataSO formDataSO = createFeedbackFormDataSO();

        Document document = FormDataConverter.convert(formDataSO);
        String json = getPrettyJson(document);
        if (outputJson) System.out.println(json);
        String expectedJson = getFeedbackFormDataSOAsJson();
        assertEquals(expectedJson, json);

        formDataSO = FormDataConverter.convert(document);
        assertFeedbackFormDataSO(formDataSO);
    }

}