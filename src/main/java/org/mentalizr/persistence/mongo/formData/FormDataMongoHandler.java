package org.mentalizr.persistence.mongo.formData;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mentalizr.persistence.mongo.*;
import org.mentalizr.serviceObjects.frontend.patient.formData.ExerciseSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FeedbackSO;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FormDataMongoHandler {

    private static final Logger logger = LoggerFactory.getLogger(FormDataMongoHandler.class);

    private static final MongoCollection<Document> mongoCollection
            = PersistenceMongoContext.getMongoDB().getMongoCollection(M7RMongoCollection.FORM_DATA);

    public static Document fetch(String userId, String contentId) throws DocumentNotFoundException {

        Document queryDocument =
                new Document(FormDataSO.USER_ID, userId)
                        .append(FormDataSO.CONTENT_ID, contentId);

        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() == null)
            throw new DocumentNotFoundException("FormData not found for userId: " + userId + ", contentId: " + contentId);
        return iterable.first();
    }

    public static List<Document> fetchAll(String userId) {
        Document queryDocument =
                new Document(FormDataSO.USER_ID, userId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static void restore(Document document) throws DocumentPreexistingException {

//        if (!document.containsKey("_id"))
//            throw new IllegalArgumentException("Specified document for mongodb restore operation " +
//                    "does not contain _id attribute.");

        String userId = (String) document.get(FormDataSO.USER_ID);
        String contentId = (String) document.get(FormDataSO.CONTENT_ID);

        logger.debug("FormDataMongoHandler.restore ...");

        checkDocumentNotPreexisting(userId, contentId);
        logger.debug("Document not preexisting.");
//        checkDocumentNotPreexistingById(document);

//        document.append("_id", new ObjectId());

        logger.debug("Restore document ...");

        try {
            mongoCollection.insertOne(document);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }

        logger.debug("document inserted successfully!");


    }

    public static void createOrUpdate(Document document) {

        String userId = (String) document.get(FormDataSO.USER_ID);
        String contentId = (String) document.get(FormDataSO.CONTENT_ID);

        Document queryDocument = new Document(FormDataSO.USER_ID, userId)
                .append(FormDataSO.CONTENT_ID, contentId);

        Document updateDocument = new Document("$set", document);

        mongoCollection.updateOne(queryDocument, updateDocument, new UpdateOptions().upsert(true));
    }

    public static void mergeWithPreexisting(FormDataSO formDataSO) {

        String userId = formDataSO.getUserId();
        String contentId = formDataSO.getContentId();

        FormDataSO formDataSOPreexisting = FormDataDAO.obtain(userId, contentId);

        FormDataSO formDataSOMerged = FormDataMerger.merge(formDataSO, formDataSOPreexisting);

        Document document = FormDataConverter.convert(formDataSOMerged);
        FormDataMongoHandler.createOrUpdate(document);

        logger.debug("formData merged:\n" + formDataSOMerged);
    }

    public static void clean(String userId) {

        Document queryDocument = new Document(FormDataSO.USER_ID, userId);

        mongoCollection.deleteMany(queryDocument);
    }


    public static Document getLastExercise(String userId) throws DocumentNotFoundException {
        Document queryDocument =
                new Document(FormDataSO.USER_ID, userId)
                        .append(FormDataSO.EXERCISE, new BasicDBObject("$exists", true));

        FindIterable<Document> iterable = mongoCollection.find(queryDocument)
                .sort(new BasicDBObject(FormDataSO.EXERCISE + "." + ExerciseSO.LAST_MODIFIED_TIMESTAMP, -1))
                .limit(1);

        if (iterable.first() == null)
            throw new DocumentNotFoundException("No FormData with exercise found for user with id [" + userId + "].");
        return iterable.first();
    }

    public static Document getLastFeedback(String userId) throws DocumentNotFoundException {
        Document queryDocument =
                new Document(FormDataSO.USER_ID, userId)
                        .append(FormDataSO.FEEDBACK, new BasicDBObject("$exists", true));

        FindIterable<Document> iterable = mongoCollection.find(queryDocument)
                .sort(new BasicDBObject(FormDataSO.FEEDBACK + "." + FeedbackSO.CREATED_TIMESTAMP, -1))
                .limit(1);

        if (iterable.first() == null)
            throw new DocumentNotFoundException("No FormData with feedback found for user with id [" + userId + "].");
        return iterable.first();
    }

    public static List<Document> getAllExercises(String userId) {
        Document queryDocument =
                new Document(FormDataSO.USER_ID, userId)
                        .append(FormDataSO.EXERCISE, new BasicDBObject("$exists", true));

        FindIterable<Document> iterable = mongoCollection.find(queryDocument);

        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    private static void checkDocumentNotPreexisting(String userId, String contentId) throws DocumentPreexistingException {
        Document queryDocument = new Document(FormDataSO.USER_ID, userId)
                .append(FormDataSO.CONTENT_ID, contentId);
        FindIterable<Document> iterable = mongoCollection.find(queryDocument);
        if (iterable.first() != null)
            throw new DocumentPreexistingException("FormData is preexisting for userId: " + userId + ", contentId: " + contentId);
    }

    private static void checkDocumentNotPreexistingById(Document document) throws DocumentPreexistingException {
        ObjectId objectId = (ObjectId) document.get("_id");

        BasicDBObject basicDBObjectQuery = new BasicDBObject();
        basicDBObjectQuery.put("_id", objectId);

        Document documentPre = mongoCollection.find(basicDBObjectQuery).first();
        if (documentPre != null)
            throw new DocumentPreexistingException("FormData document is preexisting for _id: " + objectId.toString());
    }

}


