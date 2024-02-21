package org.mentalizr.persistence.mongo.activityStatus;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mentalizr.persistence.mongo.DocumentNotFoundException;
import org.mentalizr.persistence.mongo.M7RMongoCollection;
import org.mentalizr.persistence.mongo.PersistenceMongoContext;
import org.mentalizr.serviceObjects.frontend.application.ActivityStatusMessageSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ActivityStatusMessageMongoHandler {

    private static final Logger logger = LoggerFactory.getLogger(ActivityStatusMessageMongoHandler.class);
    private static final MongoCollection<Document> mongoCollection
            = PersistenceMongoContext.getMongoDB().getMongoCollection(M7RMongoCollection.ACTIVITY_DATA);

    public static List<Document> fetchAll() {
       FindIterable<Document> iterable = mongoCollection.find();
       return StreamSupport
               .stream(iterable.spliterator(), false)
               .collect(Collectors.toList());
    }

    public static List<Document> fetchAllOfUserID(String userid) throws DocumentNotFoundException {
        Document queryDocument = new Document(ActivityStatusMessageSO.USER_ID, userid);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() == null) {
            throw new DocumentNotFoundException("No MessageSO found for userId: " + userid);
        }
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static List<Document> fetchAllOfServiceID(String serviceId) throws DocumentNotFoundException {
        Document queryDocument = new Document(ActivityStatusMessageSO.REST_ID, serviceId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() == null) {
            throw new DocumentNotFoundException("No MessageSO found for userId: " + serviceId);
        }
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static void insertOne(Document document) {
        try {
            mongoCollection.insertOne(document);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
