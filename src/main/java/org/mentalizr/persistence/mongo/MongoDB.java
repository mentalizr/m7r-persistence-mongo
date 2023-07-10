package org.mentalizr.persistence.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mentalizr.backend.config.infraUser.InfraUserConfiguration;

public class MongoDB {


    private final MongoDatabase mongoDatabase;

    public MongoDB(InfraUserConfiguration infraUserConfiguration) {
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

        @SuppressWarnings("resource")
        // try-with-resources-statement leads to unexpected behaviour of resulting MongoDatabase
        // TODO: try again after upgrade of mongo driver
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        this.mongoDatabase = mongoClient.getDatabase(DATABASE);

//        try (MongoClient mongoClient = MongoClients.create(mongoClientSettings)) {
//            this.mongoDatabase = mongoClient.getDatabase(DATABASE);
//        } catch (RuntimeException e) {
//
//        }
    }

    public MongoDatabase getMongoDatabase() {
        return this.mongoDatabase;
    }

    public MongoCollection<Document> getMongoCollection(M7RMongoCollection m7RMongoCollection) {
        return this.mongoDatabase.getCollection(m7RMongoCollection.getName());
    }

}
