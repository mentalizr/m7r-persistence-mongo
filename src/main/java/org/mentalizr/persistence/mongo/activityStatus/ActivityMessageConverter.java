package org.mentalizr.persistence.mongo.activityStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.userManagement.ActivityStatusMessageCollectionSO;
import org.mentalizr.serviceObjects.userManagement.ActivityMessageSO;

import java.util.ArrayList;
import java.util.List;

public class ActivityMessageConverter {

    public static Document convert(ActivityMessageSO activityMessageSO) {
        Document document = new Document();
        document.append(ActivityMessageSO.ID, activityMessageSO.getId());
        document.append(ActivityMessageSO.TIMESTAMP, activityMessageSO.getTimestamp());
        document.append(ActivityMessageSO.USER_ID, activityMessageSO.getUserId());
        document.append(ActivityMessageSO.REST_ID, activityMessageSO.getRestId());
        document.append(ActivityMessageSO.ROLE, activityMessageSO.getRole());
        document.append(ActivityMessageSO.MESSAGE, activityMessageSO.getMessage());
        return document;
    }

    public static ActivityMessageSO convert(Document document) {
        ActivityMessageSO activityMessageSO = new ActivityMessageSO();
        activityMessageSO.setId(document.getString(ActivityMessageSO.ID));
        activityMessageSO.setTimestamp(document.getLong(ActivityMessageSO.TIMESTAMP));
        activityMessageSO.setUserId(document.getString(ActivityMessageSO.USER_ID));
        activityMessageSO.setRestId(document.getString(ActivityMessageSO.REST_ID));
        activityMessageSO.setRole(document.getString(ActivityMessageSO.ROLE));
        activityMessageSO.setMessage(document.getString(ActivityMessageSO.MESSAGE));
        return activityMessageSO;
    }

    public static List<Document> convertActivityList(ActivityStatusMessageCollectionSO activityStatusMessageCollectionSO) {
        List<Document> documentList = new ArrayList<>();

        activityStatusMessageCollectionSO.getCollection().forEach(activityStatusMessageSO -> {
            Document document = convert(activityStatusMessageSO);
            documentList.add(document);
        });

        return documentList;
    }

    public static ActivityStatusMessageCollectionSO convertDocumentListToCollection(List<Document> documentList) {
        ActivityStatusMessageCollectionSO messageCollectionSO = new ActivityStatusMessageCollectionSO();
        documentList.forEach(document -> messageCollectionSO.getCollection().add(convert(document)));

        return messageCollectionSO;
    }
}
