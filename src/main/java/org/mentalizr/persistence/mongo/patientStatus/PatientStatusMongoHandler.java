package org.mentalizr.persistence.mongo.patientStatus;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.mentalizr.persistence.mongo.*;
import org.mentalizr.serviceObjects.frontend.patient.PatientStatusSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PatientStatusMongoHandler {

    private static final Logger logger = LoggerFactory.getLogger(PatientStatusMongoHandler.class);
    private static final MongoCollection<Document> mongoCollection 
            = PersistenceMongoContext.getMongoDB().getMongoCollection(M7RMongoCollection.PATIENT_STATUS);

    public static Document fetch(String userId) throws DocumentNotFoundException {
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() == null)
            throw new DocumentNotFoundException("PatientStatus not found for userId: " + userId);
        return iterable.first();
    }

    public static List<Document> fetchAll() {
        FindIterable<Document> iterable = mongoCollection.find();
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

    private static void checkDocumentNotPreexisting(String userId) throws DocumentPreexistingException {
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() != null)
            throw new DocumentPreexistingException("PatientStatus is preexisting for userId: " + userId);
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

    public static void delete(String userId) {
        Document queryDocument = new Document(PatientStatusSO.USER_ID, userId);
        mongoCollection.deleteMany(queryDocument);
    }

    public static Set<String> getDistinctUserIds() {
        MongoIterable<String> iterable = mongoCollection.distinct(PatientStatusSO.USER_ID, String.class);
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toSet());
    }

    public static long getNrOfDocuments() {
        return mongoCollection.countDocuments();
    }

    public static Set<String> getDuplicates() {
        AggregateIterable<Document> results = mongoCollection.aggregate(Arrays.asList(
                Aggregates.group("$" + PatientStatusSO.USER_ID, Accumulators.sum("count", 1)),
                Aggregates.match(Filters.gt("count", 1))
        ));
        Set<String> duplicates = new HashSet<>();
        for (Document document : results) {
            duplicates.add(document.getString(PatientStatusSO.USER_ID));
        }
        return duplicates;
    }

}


