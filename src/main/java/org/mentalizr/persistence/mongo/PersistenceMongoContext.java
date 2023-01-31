package org.mentalizr.persistence.mongo;

import org.mentalizr.backend.config.infraUser.InfraUserConfiguration;

public class PersistenceMongoContext {

    private static MongoDB mongoDB = null;

    private static boolean isInitialized = false;


    public static void initialize(InfraUserConfiguration infraUserConfiguration) {
        mongoDB = new MongoDB(infraUserConfiguration);
        isInitialized = true;
    }

    public static MongoDB getMongoDB() {
        assertInitialized();
        return mongoDB;
    }

    private static void assertInitialized() {
        if (!isInitialized)
            throw new IllegalStateException(PersistenceMongoContext.class.getSimpleName() + " not initialized.");
    }

}
