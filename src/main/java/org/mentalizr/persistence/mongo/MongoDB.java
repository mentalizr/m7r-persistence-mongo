package org.mentalizr.persistence.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mentalizr.backend.config.infraUser.InfraUserConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class MongoDB {

    private static final String HOST = InfraUserConfiguration.getDocumentDbHost();
    private static final String DATABASE = InfraUserConfiguration.getDocumentDbName();
    private static final String USERNAME = InfraUserConfiguration.getDocumentDbUser();
    private static final String PASSWORD = InfraUserConfiguration.getDocumentDbPassword();

    private static final List<String> collectionList = Arrays.asList("formData", "therapistEvents");

    private static final Logger logger = LoggerFactory.getLogger(MongoDB.class);

    private static MongoDatabase mongoDatabase;

    static {
        initializeMongoDB();
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static MongoCollection<Document> getMongoCollection(M7RMongoCollection m7RMongoCollection) {
        return mongoDatabase.getCollection(m7RMongoCollection.getName());
    }

    private static void initializeMongoDB() {

        ConnectionString connectionString = new ConnectionString(
                "mongodb://" + USERNAME + ":" + PASSWORD + "@" + HOST + "/" + DATABASE);

        // TODO: Security PW im ConnectionString
        logger.debug("ConnectionString MongoDB: " + connectionString);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        mongoDatabase = mongoClient.getDatabase(DATABASE);
    }

}
