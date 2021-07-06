package org.mentalizr.persistence.mongo.formData;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class FormData {

    public static final String USER_ID = "userId";
    public static final String CONTENT_ID = "contentId";
    public static final String EDITABLE = "editable";
    public static final String FORM_ELEMENT_DATA_LIST = "formElementDataList";

    private String userId;
    private String contentId;
    private boolean editable;
    private List<FormElementData> formElementDataList;

    public FormData() {
        this.userId = "";
        this.contentId = "";
        this.editable = true;
        this.formElementDataList = new ArrayList<>();
    }

    public FormData(String userId, String contentId, boolean editable, List<FormElementData> formElementDataList) {
        this.userId = userId;
        this.contentId = contentId;
        this.editable = editable;
        this.formElementDataList = formElementDataList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<FormElementData> getFormElementDataList() {
        return formElementDataList;
    }

    public void setFormElementDataList(List<FormElementData> formElementDataList) {
        this.formElementDataList = formElementDataList;
    }

    @Override
    public String toString() {

        String formElementDataListString = "FormElementDataList{";
        boolean first=true;
        for (FormElementData formElementData : this.formElementDataList) {
            formElementDataListString += formElementData.toString();
            if (first) formElementDataListString += ", ";
            first = false;
        }
        formElementDataListString += "}";

        return "FormData{" +
                "userId='" + userId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", editable=" + editable +
                ", formElementDataList=" + formElementDataListString +
                '}';
    }

}
