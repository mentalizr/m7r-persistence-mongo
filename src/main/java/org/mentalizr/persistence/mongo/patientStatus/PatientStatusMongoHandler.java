package org.mentalizr.persistence.mongo.patientStatus;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.mentalizr.persistence.mongo.DocumentNotFoundException;
import org.mentalizr.persistence.mongo.DocumentPreexistingException;
import org.mentalizr.persistence.mongo.M7RMongoCollection;
import org.mentalizr.persistence.mongo.MongoDB;
import org.mentalizr.serviceObjects.frontend.patient.PatientStatusSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PatientStatusMongoHandler {

    private static final Logger logger = LoggerFactory.getLogger(PatientStatusMongoHandler.class);
    private static final MongoCollection<Document> mongoCollection 
            = MongoDB.getMongoCollection(M7RMongoCollection.PATIENT_STATUS);

    public static Document fetch(String userId) throws DocumentNotFoundException {
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() == null)
            throw new DocumentNotFoundException("PatientStatus not found for userId: " + userId);
        return iterable.first();
    }

    public static List<Document> fetchAll(String userId) {
        Document queryDocument =
                new Document(PatientStatusSO.USER_ID, userId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static void restore(Document document) throws DocumentPreexistingException {
        String userId = (String) document.get(PatientStatusSO.USER_ID);
        checkDocumentNotPreexisting(userId);
        try {
            mongoCollection.insertOne(document);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void createOrUpdate(Document document) {
        String userId = (String) document.get(PatientStatusSO.USER_ID);
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        Document updateDocument = new Document("$set", document);
        mongoCollection.updateOne(queryDocument, updateDocument, new UpdateOptions().upsert(true));
    }

    public static void updateLastContentId(String userId, String lastContentId) {
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        BasicDBObject updateFields = new BasicDBObject();
        updateFields.append(PatientStatusSO.LAST_CONTENT_ID, lastContentId);
        Document updateDocument = new Document("$set", updateFields);
        mongoCollection.updateOne(queryDocument, updateDocument, new UpdateOptions().upsert(true));
    }

    public static void clean(String userId) {
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        mongoCollection.deleteMany(queryDocument);
    }

    private static void checkDocumentNotPreexisting(String userId) throws DocumentPreexistingException {
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() != null)
            throw new DocumentPreexistingException("PatientStatus is preexisting for userId: " + userId);
    }

}


