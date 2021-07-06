package org.mentalizr.persistence.mongo.formData;

import java.util.HashMap;
import java.util.Map;

public class FormElementDataMap {

    private final Map<String, FormElementData> formElementDataMap = new HashMap<>();

    public FormElementDataMap(FormData formData) {

        for (FormElementData formElementData : formData.getFormElementDataList()) {
            this.formElementDataMap.put(formElementData.getFormElementId(), formElementData);
        }
    }

    public boolean containsFormElementId(String formElementId) {
        return this.formElementDataMap.containsKey(formElementId);
    }

    public FormElementData getFormElementData(String formElementId) {
        return this.formElementDataMap.get(formElementId);
    }

}
