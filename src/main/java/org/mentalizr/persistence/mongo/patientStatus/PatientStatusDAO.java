package org.mentalizr.persistence.mongo.patientStatus;

import org.bson.Document;
import org.mentalizr.persistence.mongo.DocumentNotFoundException;
import org.mentalizr.serviceObjects.frontend.patient.PatientStatusSO;

public class PatientStatusDAO {

    public static PatientStatusSO fetch(String userId) throws DocumentNotFoundException {
        Document document = PatientStatusMongoHandler.fetch(userId);
        return PatientStatusConverter.convert(document);
    }

    public static PatientStatusSO obtain(String userId) {
        try {
            return fetch(userId);
        } catch (DocumentNotFoundException e) {
            PatientStatusSO patientStatusSO = new PatientStatusSO();
            patientStatusSO.setUserId(userId);
            patientStatusSO.setLastContentId("");
            return patientStatusSO;
        }
    }

    public static void createOrUpdate(PatientStatusSO patientStatusSO) {
        Document document = PatientStatusConverter.convert(patientStatusSO);
        PatientStatusMongoHandler.createOrUpdate(document);
    }

    public static void updateLastContentId(String userId, String lastContentId) {
        PatientStatusMongoHandler.updateLastContentId(userId, lastContentId);
    }

}
