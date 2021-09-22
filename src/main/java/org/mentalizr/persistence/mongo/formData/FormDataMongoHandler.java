package org.mentalizr.persistence.mongo.formData;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.mentalizr.persistence.mongo.M7RMongoCollection;
import org.mentalizr.persistence.mongo.MongoDB;
import org.mentalizr.serviceObjects.frontend.patient.formData.FormDataSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormDataMongoHandler {

    private static final Logger logger = LoggerFactory.getLogger(FormDataMongoHandler.class);

    private static final MongoCollection<Document> mongoCollection = MongoDB.getMongoCollection(M7RMongoCollection.FORM_DATA);

    public static Document fetch(String userId, String contentId) {

        Document queryDocument =
                new Document(FormDataSO.USER_ID, userId)
                .append(FormDataSO.CONTENT_ID, contentId);

        try {
            FindIterable<Document> iterable = mongoCollection.find(queryDocument);
            // TODO besser als null Rückgabe
            return iterable.first() != null ? iterable.first() : null;

        } catch (Exception e) {

            // TODO Exception-Konzept
            logger.error(e.getMessage(), e);
            return null;
        }
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

        Document documentPreexisting = FormDataMongoHandler.fetch(userId, contentId);
        FormDataSO formDataSOPreexisting = documentPreexisting == null ? new FormDataSO() : FormDataConverter.convert(documentPreexisting);

        FormDataSO formDataSOMerged = FormDataMerger.merge(formDataSO, formDataSOPreexisting);

        Document document = FormDataConverter.convert(formDataSOMerged);
        FormDataMongoHandler.createOrUpdate(document);

        logger.debug("formData merged:\n" + formDataSOMerged);
    }

    public static void clean(String userId) {

        Document queryDocument = new Document(FormDataSO.USER_ID, userId);

        mongoCollection.deleteMany(queryDocument);
    }

}


