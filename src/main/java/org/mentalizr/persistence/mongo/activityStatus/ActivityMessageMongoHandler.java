package org.mentalizr.persistence.mongo.activityStatus;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mentalizr.persistence.mongo.M7RMongoCollection;
import org.mentalizr.persistence.mongo.PersistenceMongoContext;
import org.mentalizr.serviceObjects.userManagement.ActivityRecordSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class ActivityMessageMongoHandler {

    private static final Logger logger = LoggerFactory.getLogger(ActivityMessageMongoHandler.class);
    private static final MongoCollection<Document> mongoCollection
            = PersistenceMongoContext.getMongoDB().getMongoCollection(M7RMongoCollection.ACTIVITY_DATA);

    public static List<Document> fetchAllOfUserIDBetween(String userId, Long fromTimestamp, Long untilTimestamp) {
        Bson filter = Filters.and(Filters.eq(ActivityRecordSO.USER_ID, userId),
                Filters.gte(ActivityRecordSO.TIMESTAMP, fromTimestamp),
                Filters.lte(ActivityRecordSO.TIMESTAMP, untilTimestamp));

        FindIterable<Document> iterable = mongoCollection.find()
                .filter(filter)
                .sort(Sorts.ascending(ActivityRecordSO.TIMESTAMP));
        if (iterable.first() == null) {
            return new ArrayList<>();
        }
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }

    public static void removeActivities(String userId) {
        Bson filter = Filters.eq(ActivityRecordSO.USER_ID, userId);
        mongoCollection.deleteMany(filter);
    }

    public static void insertOne(Document document) {
        try {
            mongoCollection.insertOne(document);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void insertMany(List<Document> documents) {
        mongoCollection.insertMany(documents);
    }

    public static int wipe() {
        FindIterable<Document> list = mongoCollection.find();
        int counter = 0;
        for (Document doc : list) {
            mongoCollection.deleteOne(doc);
            counter++;
        }
        return counter;
    }

    public static long count() {
        return mongoCollection.countDocuments();
    }

    public static List<Document> fetchStatisticData(List<String> userIdList, List<String> restIdList, Long fromTimestamp, Long untilTimestamp) {
        Bson filter = Filters.and(Filters.in(ActivityRecordSO.USER_ID, userIdList),
                Filters.in(ActivityRecordSO.REST_ID, restIdList),
                Filters.gte(ActivityRecordSO.TIMESTAMP, fromTimestamp),
                Filters.lte(ActivityRecordSO.TIMESTAMP, untilTimestamp));

        FindIterable<Document> iterable = mongoCollection.find()
                .filter(filter)
                .sort(Sorts.ascending(ActivityRecordSO.TIMESTAMP));

        if (iterable.first() == null) {
            return new ArrayList<>();
        }

        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }
}
