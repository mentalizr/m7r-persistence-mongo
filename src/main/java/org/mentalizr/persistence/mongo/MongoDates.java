package org.mentalizr.persistence.mongo;

import de.arthurpicht.utils.core.strings.Strings;
import org.bson.Document;
import org.mentalizr.commons.Dates;

import java.time.Instant;
import java.util.Date;

public class MongoDates {

    public static void append(Document document, String key, String isoDate) {
        Date date;
        if (Strings.isUnspecified(isoDate)) {
            date = Date.from(Instant.ofEpochMilli(0));
        } else {
            date = Dates.dateForISO(isoDate);
        }
        document.append(key, date);
    }

}
