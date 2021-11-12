package org.mentalizr.persistence.mongo.patientStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.frontend.patient.PatientStatusSO;

public class PatientStatusConverter {

    public static Document convert(PatientStatusSO patientStatusSO) {
        Document document = new Document(PatientStatusSO.USER_ID, patientStatusSO.getUserId());
        document.append(PatientStatusSO.LAST_CONTENT_ID, patientStatusSO.getLastContentId());
        return document;
    }

    public static PatientStatusSO convert(Document document) {
        String userId = document.getString(PatientStatusSO.USER_ID);
        String lastContentId = document.getString(PatientStatusSO.LAST_CONTENT_ID);

        PatientStatusSO patientStatusSO = new PatientStatusSO();
        patientStatusSO.setUserId(userId);
        patientStatusSO.setLastContentId(lastContentId);
        return patientStatusSO;
    }

}
