package org.mentalizr.persistence.mongo.patientStatus;

import org.bson.Document;
import org.mentalizr.persistence.mongo.DocumentNotFoundException;
import org.mentalizr.serviceObjects.backup.PatientStatusCollectionSO;
import org.mentalizr.serviceObjects.frontend.patient.PatientStatusSO;

import java.util.ArrayList;
import java.util.List;

public class PatientStatusDAO {

    public static PatientStatusSO fetch(String userId) throws DocumentNotFoundException {
        Document document = PatientStatusMongoHandler.fetch(userId);
        return PatientStatusConverter.convert(document);
    }

    public static PatientStatusCollectionSO fetchAll() {
        List<PatientStatusSO> patientStatusSOList = new ArrayList<>();
        List<Document> documentList = PatientStatusMongoHandler.fetchAll();
        documentList.forEach(document -> patientStatusSOList.add(PatientStatusConverter.convert(document)));
        PatientStatusCollectionSO patientStatusCollectionSO = new PatientStatusCollectionSO();
        patientStatusCollectionSO.setCollection(patientStatusSOList);
        return patientStatusCollectionSO;
    }

    public static void createOrUpdate(PatientStatusSO patientStatusSO) {
        Document document = PatientStatusConverter.convert(patientStatusSO);
        PatientStatusMongoHandler.createOrUpdate(document);
    }

    public static void updateLastContentId(String userId, String lastContentId) {
        PatientStatusMongoHandler.updateLastContentId(userId, lastContentId);
    }

}
