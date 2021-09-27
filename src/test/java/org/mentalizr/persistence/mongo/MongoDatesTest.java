package org.mentalizr.persistence.mongo;

import org.junit.jupiter.api.Test;

import java.util.Date;

class MongoDatesTest {

    @Test
    void getISOForNow() {

        String isoNow = MongoDates.currentTimestampAsISO();
        System.out.println(isoNow);

        Date now = MongoDates.dateForISO(isoNow);
        System.out.println(now);

    }
}