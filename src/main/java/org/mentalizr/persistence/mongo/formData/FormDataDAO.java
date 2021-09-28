package org.mentalizr.persistence.mongo.formData;

import org.bson.Document;
import org.mentalizr.persistence.mongo.DocumentNotFoundException;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;

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
            formDataSO.setEditable(true);
            return formDataSO;
        }
    }

    public static void createOrUpdate(FormDataSO formDataSO) {
        Document document = FormDataConverter.convert(formDataSO);
        FormDataMongoHandler.createOrUpdate(document);
    }

}
