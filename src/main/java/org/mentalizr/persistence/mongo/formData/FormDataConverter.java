package org.mentalizr.persistence.mongo.formData;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormElementDataSO;

public class FormDataConverter {

    public static Document convert(FormDataSO formDataSO) {

        Document document = new Document(FormDataSO.USER_ID, formDataSO.getUserId())
                .append(FormDataSO.CONTENT_ID, formDataSO.getContentId())
                .append(FormDataSO.EDITABLE, formDataSO.isEditable());

        List<Document> formElementDataList = new ArrayList<>();

        for (FormElementDataSO formElementDataSO : formDataSO.getFormElementDataList()) {

            Document formElementDataDocument = new Document(FormElementDataSO.FORM_ELEMENT_TYPE, formElementDataSO.getFormElementType())
                    .append(FormElementDataSO.FORM_ELEMENT_ID, formElementDataSO.getFormElementId())
                    .append(FormElementDataSO.FORM_ELEMENT_VALUE, formElementDataSO.getFormElementValue());
            formElementDataList.add(formElementDataDocument);

        }

        document.append(FormDataSO.FORM_ELEMENT_DATA_LIST, formElementDataList);

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

        List<Document> formElementDataDocumentList = document.getList(FormDataSO.FORM_ELEMENT_DATA_LIST, Document.class);
        List<FormElementDataSO> formElementDataSOList = new ArrayList<>();
        formDataSO.setFormElementDataList(formElementDataSOList);

        for (Document formElementDataDocument : formElementDataDocumentList) {
            FormElementDataSO formElementDataSO = new FormElementDataSO();
            formElementDataSO.setFormElementType(formElementDataDocument.getString(FormElementDataSO.FORM_ELEMENT_TYPE));
            formElementDataSO.setFormElementId(formElementDataDocument.getString(FormElementDataSO.FORM_ELEMENT_ID));
            formElementDataSO.setFormElementValue(formElementDataDocument.getString(FormElementDataSO.FORM_ELEMENT_VALUE));
            formElementDataSOList.add(formElementDataSO);
        }

        return formDataSO;
    }

}
