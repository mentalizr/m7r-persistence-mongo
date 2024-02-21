package org.mentalizr.persistence.mongo.activityStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.frontend.application.ActivityStatusMessageSO;

public class ActivityStatusMessageDAO {

    public static void createOrUpdate(ActivityStatusMessageSO activityStatusMessageSO) {
        Document document = ActivityStatusMessageConverter.convert(activityStatusMessageSO);
        ActivityStatusMessageMongoHandler.insertOne(document);
    }
}
