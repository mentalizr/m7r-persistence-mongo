package org.mentalizr.persistence.mongo;

import de.arthurpicht.utils.core.strings.Strings;
import org.bson.Document;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * TODO Move to commons/utils, same for m7r-backend-test/Dates
 */
public class MongoDates {

    public static String currentTimestampAsISO() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public static Date dateForISO(String iso) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(iso);
        return Date.from(zonedDateTime.toInstant());
    }

    public static String toIsoString(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public static String epochAsISO() {
        return "1970-01-01T00:00:00Z";
    }

    public static void append(Document document, String key, String isoDate) {
        Date date;
        if (Strings.isUnspecified(isoDate)) {
            date = Date.from(Instant.ofEpochMilli(0));
        } else {
            date = dateForISO(isoDate);
        }
        document.append(key, date);
    }

}
