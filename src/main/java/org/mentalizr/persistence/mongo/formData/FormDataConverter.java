package org.mentalizr.persistence.mongo.formData;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class FormDataConverter {

    public static Document convert(FormData formData) {

        Document document = new Document(FormData.USER_ID, formData.getUserId())
                .append(FormData.CONTENT_ID, formData.getContentId())
                .append(FormData.EDITABLE, formData.isEditable());

        List<Document> formElementDataList = new ArrayList<>();

        for (FormElementData formElementData : formData.getFormElementDataList()) {

            Document formElementDataDocument = new Document(FormElementData.FORM_ELEMENT_TYPE, formElementData.getFormElementType())
                    .append(FormElementData.FORM_ELEMENT_ID, formElementData.getFormElementId())
                    .append(FormElementData.FORM_ELEMENT_VALUE, formElementData.getFormElementValue());
            formElementDataList.add(formElementDataDocument);

        }

        document.append(FormData.FORM_ELEMENT_DATA_LIST, formElementDataList);

        return document;
    }

    public static FormData convert(Document document) {

        String userId = document.getString(FormData.USER_ID);
        String contentId = document.getString(FormData.CONTENT_ID);
        boolean editable = document.getBoolean(FormData.EDITABLE);

        FormData formData = new FormData();
        formData.setUserId(userId);
        formData.setContentId(contentId);
        formData.setEditable(editable);

//        final Class<? extends List> clazz = new ArrayList<Document>().getClass();
        List<Document> formElementDataDocumentList = document.getList(FormData.FORM_ELEMENT_DATA_LIST, Document.class);
        List<FormElementData> formElementDataList = new ArrayList<>();
        formData.setFormElementDataList(formElementDataList);

        for (Document formElementDataDocument : formElementDataDocumentList) {
            FormElementData formElementData = new FormElementData();
            formElementData.setFormElementType(formElementDataDocument.getString(FormElementData.FORM_ELEMENT_TYPE));
            formElementData.setFormElementId(formElementDataDocument.getString(FormElementData.FORM_ELEMENT_ID));
            formElementData.setFormElementValue(formElementDataDocument.getString(FormElementData.FORM_ELEMENT_VALUE));
            formElementDataList.add(formElementData);
        }

        return formData;
    }


}
