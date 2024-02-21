package org.mentalizr.persistence.mongo.activityStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.frontend.application.ActivityStatusMessageSO;

public class ActivityStatusMessageConverter {

    public static Document convert(ActivityStatusMessageSO activityStatusMessageSO) {
        Document document = new Document(ActivityStatusMessageSO.TIMESTAMP, activityStatusMessageSO.getTimestamp());
        document.append(ActivityStatusMessageSO.USER_ID, activityStatusMessageSO.getUserId());
        document.append(ActivityStatusMessageSO.REST_ID, activityStatusMessageSO.getRestId());
        document.append(ActivityStatusMessageSO.ROLE, activityStatusMessageSO.getRole());
        document.append(ActivityStatusMessageSO.MESSAGE, activityStatusMessageSO.getMessage());

        return document;
    }

    public static ActivityStatusMessageSO convert(Document document) {
        ActivityStatusMessageSO activityStatusMessageSO = new ActivityStatusMessageSO();
        activityStatusMessageSO.setTimestamp(document.getString(ActivityStatusMessageSO.TIMESTAMP));
        activityStatusMessageSO.setUserId(document.getString(ActivityStatusMessageSO.USER_ID));
        activityStatusMessageSO.setRestId(document.getString(ActivityStatusMessageSO.REST_ID));
        activityStatusMessageSO.setRole(document.getString(ActivityStatusMessageSO.ROLE));
        activityStatusMessageSO.setMessage(document.getString(ActivityStatusMessageSO.MESSAGE));

        return activityStatusMessageSO;
    }
}
