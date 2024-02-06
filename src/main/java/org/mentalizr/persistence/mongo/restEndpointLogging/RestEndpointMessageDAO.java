package org.mentalizr.persistence.mongo.restEndpointLogging;

import org.bson.Document;
import org.mentalizr.serviceObjects.frontend.application.RestEndpointMessageSO;

public class RestEndpointMessageDAO {

    public static void createOrUpdate(RestEndpointMessageSO restEndpointMessageSO) {
        Document document = RestEndpointMessageConverter.convert(restEndpointMessageSO);
        RestEndpointMessageMongoHandler.insertOne(document);
    }
}
