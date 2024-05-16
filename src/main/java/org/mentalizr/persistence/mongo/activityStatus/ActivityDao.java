package org.mentalizr.persistence.mongo.activityStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.userManagement.ActivityMessageSO;

public class ActivityDao {

    public static void createMessage(String serviceId, String userId, String roleName) {
        createMessage(serviceId, userId, roleName, "");
    }

    public static void createMessage(String serviceId, String userId, String roleName, String message) {
        ActivityMessageSO activityMessageSO = createActivityStatusMessageSO(serviceId, userId, roleName);
        activityMessageSO.setMessage(message);
        Document activityStatusMessageDocument = ActivityMessageConverter.convert(activityMessageSO);
        ActivityMessageMongoHandler.insertOne(activityStatusMessageDocument);
    }

    private static ActivityMessageSO createActivityStatusMessageSO(String serviceId, String userId, String roleName) {
        ActivityMessageSO activityMessageSO = new ActivityMessageSO();
        activityMessageSO.setTimestamp(System.currentTimeMillis());
        activityMessageSO.setUserId(userId);
        activityMessageSO.setRestId(serviceId);
        activityMessageSO.setRole(roleName);
        activityMessageSO.setMessage("");
        return activityMessageSO;
    }

}
