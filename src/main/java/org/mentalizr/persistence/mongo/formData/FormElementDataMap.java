package org.mentalizr.persistence.mongo.formData;

import java.util.HashMap;
import java.util.Map;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormElementDataSO;

public class FormElementDataMap {

    private final Map<String, FormElementDataSO> formElementDataMap = new HashMap<>();

    public FormElementDataMap(FormDataSO formDataSO) {

        for (FormElementDataSO formElementDataSO : formDataSO.getFormElementDataList()) {
            this.formElementDataMap.put(formElementDataSO.getFormElementId(), formElementDataSO);
        }
    }

    public boolean containsFormElementId(String formElementId) {
        return this.formElementDataMap.containsKey(formElementId);
    }

    public FormElementDataSO getFormElementData(String formElementId) {
        return this.formElementDataMap.get(formElementId);
    }

}
