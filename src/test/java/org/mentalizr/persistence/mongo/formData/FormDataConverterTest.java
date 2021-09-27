package org.mentalizr.persistence.mongo.formData;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.junit.jupiter.api.Test;
import org.mentalizr.serviceObjects.frontend.patient.formData.ExerciseSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FeedbackSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormElementDataSO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormDataConverterTest {

    private static final boolean outputJson = true;

    private FormDataSO createSimpleFormDataSO() {
        FormDataSO formDataSO = new FormDataSO();
        formDataSO.setUserId("userId");
        formDataSO.setContentId("contentId");
        formDataSO.setEditable(true);

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
        assertTrue(formDataSO.isEditable());
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
                "  \"editable\": true,\n" +
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
        formDataSO.setExerciseSO(exerciseSO);
        return formDataSO;
    }

    private void assertExerciseFormDataSO(FormDataSO formDataSO) {
        assertSimpleFormDataSO(formDataSO);
        assertNotNull(formDataSO.getExerciseSO());
        ExerciseSO exerciseSO = formDataSO.getExerciseSO();
        assertTrue(exerciseSO.isSent());
        assertEquals("2021-09-27T10:47:01.443866Z", exerciseSO.getLastModifiedTimestamp());
        assertTrue(exerciseSO.isSeenByTherapist());
        assertEquals("", exerciseSO.getSeenByTherapistTimestamp());
    }

    private String getExerciseFormDataSOAsJson() {
        return "{\n" +
                "  \"userId\": \"userId\",\n" +
                "  \"contentId\": \"contentId\",\n" +
                "  \"editable\": true,\n" +
                "  \"exercise\": {\n" +
                "    \"sent\": true,\n" +
                "    \"lastModifiedTimestamp\": \"2021-09-27T10:47:01.443866Z\",\n" +
                "    \"seenByTherapist\": true,\n" +
                "    \"seenByTherapistTimestamp\": \"\"\n" +
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
        feedbackSO.setCreatedTimestamp("createdTimestamp");
        feedbackSO.setTherapistId("therapistId");
        feedbackSO.setSeenByPatient(true);
        feedbackSO.setSeenByPatientTimestamp("seenByPatientTimestamp");
        formDataSO.setFeedbackSO(feedbackSO);
        return formDataSO;
    }

    private void assertFeedbackFormDataSO(FormDataSO formDataSO) {
        assertExerciseFormDataSO(formDataSO);
        assertNotNull(formDataSO.getFeedbackSO());
        FeedbackSO feedbackSO = formDataSO.getFeedbackSO();
        assertEquals("text", feedbackSO.getText());
        assertEquals("createdTimestamp", feedbackSO.getCreatedTimestamp());
        assertEquals("therapistId", feedbackSO.getTherapistId());
        assertTrue(feedbackSO.isSeenByPatient());
        assertEquals("seenByPatientTimestamp", feedbackSO.getSeenByPatientTimestamp());
    }

    private String getFeedbackFormDataSOAsJson() {
        return "{\n" +
                "  \"userId\": \"userId\",\n" +
                "  \"contentId\": \"contentId\",\n" +
                "  \"editable\": true,\n" +
                "  \"exercise\": {\n" +
                "    \"sent\": true,\n" +
                "    \"lastModifiedTimestamp\": \"2021-09-27T10:47:01.443866Z\",\n" +
                "    \"seenByTherapist\": true,\n" +
                "    \"seenByTherapistTimestamp\": \"\"\n" +
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
                "    \"createdTimestamp\": \"createdTimestamp\",\n" +
                "    \"therapistId\": \"therapistId\",\n" +
                "    \"seenByPatient\": true,\n" +
                "    \"seenByPatientTimestamp\": \"seenByPatientTimestamp\"\n" +
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