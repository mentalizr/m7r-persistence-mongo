package org.mentalizr.persistence.mongo;

public enum M7RMongoCollection {

    FORM_DATA("formData"),
    PATIENT_STATUS("patientStatus"),
    FEEDBACK_DATA("feedbackData"),
    EXERCISE_STATUS__SUBMITTED("exerciseStatusSubmitted"),
    REST_ENDPOINT_LOGGING("restEndPointLogging"),
    TEST("testCollection");

    private final String name;

    M7RMongoCollection(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

//    public static boolean contains(String collectionName) {
//        for (M7RMongoCollection m7RMongoCollection : M7RMongoCollection.values()) {
//            if (m7RMongoCollection.getName().equals(collectionName)) return true;
//        }
//        return false;
//    }

}
