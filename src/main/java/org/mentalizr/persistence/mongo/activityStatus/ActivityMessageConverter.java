package org.mentalizr.persistence.mongo.activityStatus;

import org.bson.Document;
import org.mentalizr.serviceObjects.userManagement.ActivityRecordCollectionSO;
import org.mentalizr.serviceObjects.userManagement.ActivityRecordSO;

import java.util.ArrayList;
import java.util.List;

public class ActivityMessageConverter {

    public static Document convert(ActivityRecordSO activityRecordSO) {
        Document document = new Document();
        document.append(ActivityRecordSO.ID, activityRecordSO.getId());
        document.append(ActivityRecordSO.TIMESTAMP, activityRecordSO.getTimestamp());
        document.append(ActivityRecordSO.USER_ID, activityRecordSO.getUserId());
        document.append(ActivityRecordSO.REST_ID, activityRecordSO.getRestId());
        document.append(ActivityRecordSO.ROLE, activityRecordSO.getRole());
        document.append(ActivityRecordSO.MESSAGE, activityRecordSO.getMessage());
        return document;
    }

    public static ActivityRecordSO convert(Document document) {
        ActivityRecordSO activityRecordSO = new ActivityRecordSO();
        activityRecordSO.setId(document.getString(ActivityRecordSO.ID));
        activityRecordSO.setTimestamp(document.getLong(ActivityRecordSO.TIMESTAMP));
        activityRecordSO.setUserId(document.getString(ActivityRecordSO.USER_ID));
        activityRecordSO.setRestId(document.getString(ActivityRecordSO.REST_ID));
        activityRecordSO.setRole(document.getString(ActivityRecordSO.ROLE));
        activityRecordSO.setMessage(document.getString(ActivityRecordSO.MESSAGE));
        return activityRecordSO;
    }

    public static List<Document> convertActivityList(ActivityRecordCollectionSO activityRecordCollectionSO) {
        List<Document> documentList = new ArrayList<>();

        activityRecordCollectionSO.getCollection().forEach(activityStatusMessageSO -> {
            Document document = convert(activityStatusMessageSO);
            documentList.add(document);
        });

        return documentList;
    }

    public static ActivityRecordCollectionSO convertDocumentListToCollection(List<Document> documentList) {
        ActivityRecordCollectionSO messageCollectionSO = new ActivityRecordCollectionSO();
        documentList.forEach(document -> messageCollectionSO.getCollection().add(convert(document)));

        return messageCollectionSO;
    }
}
