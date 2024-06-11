package org.mentalizr.persistence.mongo.activityStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.userManagement.ActivityRecordSO;

public class ActivityDAO {

    public static void createMessage(String serviceId, String userId, String roleName) {
        createMessage(serviceId, userId, roleName, "");
    }

    public static void createMessage(String serviceId, String userId, String roleName, String message) {
        ActivityRecordSO activityRecordSO = createActivityStatusMessageSO(serviceId, userId, roleName);
        activityRecordSO.setMessage(message);
        Document activityStatusMessageDocument = ActivityMessageConverter.convert(activityRecordSO);
        ActivityMessageMongoHandler.insertOne(activityStatusMessageDocument);
    }

    private static ActivityRecordSO createActivityStatusMessageSO(String serviceId, String userId, String roleName) {
        ActivityRecordSO activityRecordSO = new ActivityRecordSO();
        activityRecordSO.setTimestamp(System.currentTimeMillis());
        activityRecordSO.setUserId(userId);
        activityRecordSO.setRestId(serviceId);
        activityRecordSO.setRole(roleName);
        activityRecordSO.setMessage("");
        return activityRecordSO;
    }

}
