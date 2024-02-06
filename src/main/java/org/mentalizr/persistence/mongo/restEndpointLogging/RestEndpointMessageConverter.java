package org.mentalizr.persistence.mongo.restEndpointLogging;

import org.bson.Document;
import org.mentalizr.serviceObjects.frontend.application.RestEndpointMessageSO;

public class RestEndpointMessageConverter {

    public static Document convert(RestEndpointMessageSO restEndpointMessageSO) {
        Document document = new Document(restEndpointMessageSO.TIMESTAMP, restEndpointMessageSO.getTimestamp());
        document.append(restEndpointMessageSO.USER_ID, restEndpointMessageSO.getUserId());
        document.append(restEndpointMessageSO.REST_ID, restEndpointMessageSO.getRestId());
        document.append(restEndpointMessageSO.ROLE, restEndpointMessageSO.getRole());
        document.append(restEndpointMessageSO.MESSAGE, restEndpointMessageSO.getMessage());

        return document;
    }

    public static RestEndpointMessageSO convert(Document document) {
        RestEndpointMessageSO restEndpointMessageSO = new RestEndpointMessageSO();
        restEndpointMessageSO.setTimestamp(document.getString(RestEndpointMessageSO.TIMESTAMP));
        restEndpointMessageSO.setUserId(document.getString(RestEndpointMessageSO.USER_ID));
        restEndpointMessageSO.setRestId(document.getString(RestEndpointMessageSO.REST_ID));
        restEndpointMessageSO.setRole(document.getString(RestEndpointMessageSO.ROLE));
        restEndpointMessageSO.setMessage(document.getString(RestEndpointMessageSO.MESSAGE));

        return restEndpointMessageSO;
    }
}
