package org.mentalizr.persistence.mongo;

public class DocumentPreexistingException extends Exception {

    public DocumentPreexistingException() {
    }

    public DocumentPreexistingException(String message) {
        super(message);
    }

    public DocumentPreexistingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentPreexistingException(Throwable cause) {
        super(cause);
    }

    public DocumentPreexistingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
