package org.mentalizr.persistence.mongo.activityStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.userManagement.ActivityStatusMessageCollectionSO;
import org.mentalizr.serviceObjects.userManagement.ActivityStatusMessageSO;

import java.util.ArrayList;
import java.util.List;

public class ActivityStatusMessageConverter {

    public static Document convert(ActivityStatusMessageSO activityStatusMessageSO) {
        Document document = new Document();
        document.append(ActivityStatusMessageSO.TIMESTAMP, activityStatusMessageSO.getTimestamp());
        document.append(ActivityStatusMessageSO.USER_ID, activityStatusMessageSO.getUserId());
        document.append(ActivityStatusMessageSO.REST_ID, activityStatusMessageSO.getRestId());
        document.append(ActivityStatusMessageSO.ROLE, activityStatusMessageSO.getRole());
        document.append(ActivityStatusMessageSO.MESSAGE, activityStatusMessageSO.getMessage());

        return document;
    }

    public static ActivityStatusMessageSO convert(Document document) {
        ActivityStatusMessageSO activityStatusMessageSO = new ActivityStatusMessageSO();
        activityStatusMessageSO.setTimestamp(document.getLong(ActivityStatusMessageSO.TIMESTAMP));
        activityStatusMessageSO.setUserId(document.getString(ActivityStatusMessageSO.USER_ID));
        activityStatusMessageSO.setRestId(document.getString(ActivityStatusMessageSO.REST_ID));
        activityStatusMessageSO.setRole(document.getString(ActivityStatusMessageSO.ROLE));
        activityStatusMessageSO.setMessage(document.getString(ActivityStatusMessageSO.MESSAGE));

        return activityStatusMessageSO;
    }

    public static List<Document> convertActivityList(List<ActivityStatusMessageSO> activityStatusMessageSOList) {
        List<Document> documentList = new ArrayList<>();

        activityStatusMessageSOList.forEach(activityStatusMessageSO -> {
            Document document = new Document();
            document.append(ActivityStatusMessageSO.TIMESTAMP, activityStatusMessageSO.getTimestamp());
            document.append(ActivityStatusMessageSO.USER_ID, activityStatusMessageSO.getUserId());
            document.append(ActivityStatusMessageSO.REST_ID, activityStatusMessageSO.getRestId());
            document.append(ActivityStatusMessageSO.ROLE, activityStatusMessageSO.getRole());
            document.append(ActivityStatusMessageSO.MESSAGE, activityStatusMessageSO.getMessage());
        });

        return documentList;
    }

    public static ActivityStatusMessageCollectionSO convertDocumentListToCollection(List<Document> documentList) {
        ActivityStatusMessageCollectionSO messageCollectionSO = new ActivityStatusMessageCollectionSO();
        documentList.forEach(document -> messageCollectionSO.getCollection().add(convert(document)));

        return messageCollectionSO;
    }
}
