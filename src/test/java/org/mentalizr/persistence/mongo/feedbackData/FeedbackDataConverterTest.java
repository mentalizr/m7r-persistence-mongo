package org.mentalizr.persistence.mongo.feedbackData;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackDataConverterTest {

    @Test
    void convert() {

        FeedbackData feedbackData = new FeedbackData("myUserId", "myContentId", "myFeddback", 123);
        Document document = FeedbackDataConverter.convert(feedbackData);
        System.out.println(document.toJson());


    }

    @Test
    void testConvert() {
    }
}