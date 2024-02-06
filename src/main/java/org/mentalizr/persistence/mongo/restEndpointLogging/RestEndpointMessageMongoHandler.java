package org.mentalizr.persistence.mongo.restEndpointLogging;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mentalizr.persistence.mongo.M7RMongoCollection;
import org.mentalizr.persistence.mongo.PersistenceMongoContext;
import org.mentalizr.persistence.mongo.patientStatus.PatientStatusMongoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class RestEndpointMessageMongoHandler {

    private static final Logger logger = LoggerFactory.getLogger(PatientStatusMongoHandler.class);
    private static final MongoCollection<Document> mongoCollection
            = PersistenceMongoContext.getMongoDB().getMongoCollection(M7RMongoCollection.REST_ENDPOINT_LOGGING);

    public static List<Document> fetchAll() {
       FindIterable<Document> iterable = mongoCollection.find();
       return StreamSupport
               .stream(iterable.spliterator(), false)
               .collect(Collectors.toList());
    }

    public static void insertOne(Document document) {
        mongoCollection.insertOne(document);
        logger.info("it really happend!!!! Ive done it!!!");
    }
}
