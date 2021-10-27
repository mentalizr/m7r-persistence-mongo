package org.mentalizr.persistence.mongo.formData;

import org.bson.Document;
import org.mentalizr.persistence.mongo.DocumentNotFoundException;
import org.mentalizr.serviceObjects.backup.FormDataCollectionSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FormDataDAO {

    public static FormDataSO fetch(String userId, String contentId) throws DocumentNotFoundException {
        Document document = FormDataMongoHandler.fetch(userId, contentId);
        return FormDataConverter.convert(document);
    }

    public static FormDataSO obtain(String userId, String contentId) {
        try {
            return fetch(userId, contentId);
        } catch (DocumentNotFoundException e) {
            FormDataSO formDataSO = new FormDataSO();
            formDataSO.setUserId(userId);
            formDataSO.setContentId(contentId);
            return formDataSO;
        }
    }

    public static FormDataCollectionSO fetchAll(String userId) {
        List<FormDataSO> formDataSOList = new ArrayList<>();
        List<Document> documentList = FormDataMongoHandler.fetchAll(userId);
        documentList.forEach(document -> formDataSOList.add(FormDataConverter.convert(document)));
        FormDataCollectionSO formDataCollectionSO = new FormDataCollectionSO();
        formDataCollectionSO.setCollection(formDataSOList);

        return formDataCollectionSO;
    }

    public static void createOrUpdate(FormDataSO formDataSO) {
        Document document = FormDataConverter.convert(formDataSO);
        FormDataMongoHandler.createOrUpdate(document);
    }

    public static Optional<FormDataSO> getLastExercise(String userId) {
        try {
            Document document = FormDataMongoHandler.getLastExercise(userId);
            FormDataSO formDataSO = FormDataConverter.convert(document);
            return Optional.of(formDataSO);
        } catch (DocumentNotFoundException e) {
            return Optional.empty();
        }
    }

    public static Optional<FormDataSO> getLastFeedback(String userId) {
        try {
            Document document = FormDataMongoHandler.getLastFeedback(userId);
            FormDataSO formDataSO = FormDataConverter.convert(document);
            return Optional.of(formDataSO);
        } catch (DocumentNotFoundException e) {
            return Optional.empty();
        }
    }

    public static List<FormDataSO> getAllExercises(String userId) {
        List<Document> documentList = FormDataMongoHandler.getAllExercises(userId);
        List<FormDataSO> formDataSOList = new ArrayList<>();
        documentList.forEach(document -> formDataSOList.add(FormDataConverter.convert(document)));
        return formDataSOList;
    }

}
