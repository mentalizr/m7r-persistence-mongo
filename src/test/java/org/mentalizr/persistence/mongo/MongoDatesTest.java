package org.mentalizr.persistence.mongo;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MongoDatesTest {

    @Test
    void getISOForNow() {
        Instant nowInstantPre = Instant.now();

        String isoNow = MongoDates.currentTimestampAsISO();
        System.out.println(isoNow);

        Date now = MongoDates.dateForISO(isoNow);
        System.out.println(now);

        Instant nowInstantPost = now.toInstant();

        assertTrue(nowInstantPost.isAfter(nowInstantPre));
        Duration duration = Duration.between(nowInstantPre, nowInstantPost);
        assertTrue(duration.toMinutes() < 1);
    }

}