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

public class MongoDBSave {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBSave.class);
    private static boolean isInitialized = false;

    private static MongoDatabase mongoDatabase;

    public static MongoDatabase getMongoDatabase() {
        assertInitialized();
        return mongoDatabase;
    }

    public static MongoCollection<Document> getMongoCollection(M7RMongoCollection m7RMongoCollection) {
        assertInitialized();
        return mongoDatabase.getCollection(m7RMongoCollection.getName());
    }

    public static void initialize(InfraUserConfiguration infraUserConfiguration) {
        String HOST = infraUserConfiguration.getDocumentDbHost();
        String DATABASE = infraUserConfiguration.getDocumentDbName();
        String USERNAME = infraUserConfiguration.getDocumentDbUser();
        String PASSWORD = infraUserConfiguration.getDocumentDbPassword();

        ConnectionString connectionString = new ConnectionString(
                "mongodb://" + USERNAME + ":" + PASSWORD + "@" + HOST + "/" + DATABASE);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .build();
        try (MongoClient mongoClient = MongoClients.create(mongoClientSettings)) {
            mongoDatabase = mongoClient.getDatabase(DATABASE);
        }

        isInitialized = true;
    }

    private static void assertInitialized() {
        if (!isInitialized)
            throw new IllegalStateException(MongoDBSave.class.getCanonicalName() + " not initialized.");
    }

}
