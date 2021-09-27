package org.mentalizr.persistence.mongo;

import de.arthurpicht.utils.core.strings.Strings;
import org.bson.Document;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MongoDates {

    public static String currentTimestampAsISO() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public static Date dateForISO(String iso) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(iso);
        return Date.from(zonedDateTime.toInstant());
    }

    public static void append(Document document, String key, String isoDate) {
        if (Strings.isUnspecified(isoDate)) {
            document.append(key, "");
        } else {
            Date date = dateForISO(isoDate);
            document.append(key, date);
        }
    }


}
