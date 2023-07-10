package org.mentalizr.persistence.mongo.exerciseStatus;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.mentalizr.persistence.mongo.M7RMongoCollection;
import org.mentalizr.persistence.mongo.MongoDB;
import org.mentalizr.persistence.mongo.PersistenceMongoContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExerciseStatusSubmittedMongoHandler {

    private static Logger logger = LoggerFactory.getLogger(ExerciseStatusSubmittedMongoHandler.class);

    private static MongoCollection<Document> mongoCollection
            = PersistenceMongoContext.getMongoDB().getMongoCollection(M7RMongoCollection.EXERCISE_STATUS__SUBMITTED);

//    public static Document fetch(String userId, String contentId) {
//
//        logger.debug("FormDataMongoHandler.fetch aufgerufen.");
//
//        Document queryDocument = new Document("userId", userId)
//                .append("contentId", contentId);
//
//
//        try {
//            FindIterable<Document> iterable = mongoCollection.find(queryDocument);
//
//            if (iterable.first() == null) {
//                // System.out.println("Kein Dokument in MongoDB gefunden.");
//                return null;
//            }
//
////            System.out.println("Dokument in MongoDB gefunden.");
//            return iterable.first();
//
//        } catch (Exception e) {
//
//            // TODO Execption-Konzept
//            logger.error(e.getMessage(), e);
//            return null;
//        }
//
//    }

    public static void createOrUpdate(Document document) {

//        System.out.println("FormDataMongoHandler.createOrUpdate aufgerufen.");

        String userId = (String) document.get("userId");
        String contentId = (String) document.get("contentId");

//        System.out.println(userId);
//        System.out.println(contentId);

        Document queryDocument = new Document("userId", userId)
                .append("contentId", contentId);

        Document updateDocument = new Document("$set", document);

        mongoCollection.updateOne(queryDocument, updateDocument, new UpdateOptions().upsert(true));
    }


}


