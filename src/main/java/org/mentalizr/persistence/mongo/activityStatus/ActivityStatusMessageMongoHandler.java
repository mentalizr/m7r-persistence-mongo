package org.mentalizr.persistence.mongo.activityStatus;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mentalizr.persistence.mongo.M7RMongoCollection;
import org.mentalizr.persistence.mongo.PersistenceMongoContext;
import org.mentalizr.serviceObjects.userManagement.ActivityStatusMessageSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    public static List<Document> fetchAllOfUserID(String userId) {
        Document queryDocument = new Document(ActivityStatusMessageSO.USER_ID, userId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);

        if (iterable.first() == null) {
            return new ArrayList<>();
        }
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }

    public static List<Document> fetchAllOfUserIDFrom(String userId, Long timestamp) {
        Bson filter = Filters.and(Filters.gte(ActivityStatusMessageSO.TIMESTAMP, timestamp),
                Filters.eq(ActivityStatusMessageSO.USER_ID, userId));

        FindIterable<Document> iterable = mongoCollection.find()
                .filter(filter)
                .sort(Sorts.ascending(ActivityStatusMessageSO.TIMESTAMP));
        if (iterable.first() == null) {
            return new ArrayList<>();
        }
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }

    public static List<Document> fetchAllOfUserIDUntil(String userId, Long timestamp) {
        Bson filter = Filters.and(Filters.lte(ActivityStatusMessageSO.TIMESTAMP, timestamp),
                Filters.eq(ActivityStatusMessageSO.USER_ID, userId));

        FindIterable<Document> iterable = mongoCollection.find()
                .filter(filter)
                .sort(Sorts.ascending(ActivityStatusMessageSO.TIMESTAMP));
        if (iterable.first() == null) {
            return new ArrayList<>();
        }
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }

    public static List<Document> fetchAllOfUserIDBetween(String userId, Long fromTimestamp, Long untilTimestamp) {
        Bson filter = Filters.and(Filters.eq(ActivityStatusMessageSO.USER_ID, userId),
                Filters.gte(ActivityStatusMessageSO.TIMESTAMP, fromTimestamp),
                Filters.lte(ActivityStatusMessageSO.TIMESTAMP, untilTimestamp));

        FindIterable<Document> iterable = mongoCollection.find()
                .filter(filter)
                .sort(Sorts.ascending(ActivityStatusMessageSO.TIMESTAMP));
        if (iterable.first() == null) {
            return new ArrayList<>();
        }
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }

    public static void insertOne(Document document) {
        try {
            mongoCollection.insertOne(document);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void deleteOne(ActivityStatusMessageSO activityStatusMessageSO) {
        Bson filter = Filters.and(Filters.eq(ActivityStatusMessageSO.USER_ID, activityStatusMessageSO.getUserId()),
                Filters.eq(ActivityStatusMessageSO.TIMESTAMP, activityStatusMessageSO.getTimestamp()));
        try {
            mongoCollection.deleteOne(filter);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
