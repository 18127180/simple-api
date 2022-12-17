package com.safetrust.simpleapi.exception;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static javax.servlet.http.HttpServletResponse.*;

/**
 * Holds default and constant exception messages.
 */
public enum ErrorKey {
    /**
     * Generic Access Error Messages
     */
    VALIDATION_ERROR("validation.rest.error", SC_BAD_REQUEST),
    VALIDATION_INPUT_ERROR("validation.input.error", SC_BAD_REQUEST);

    /**
     * Holds the message key.
     */
    private final String messageKey;
    ResourceBundle defaultBundle = ResourceBundle.getBundle("messages");
    /**
     * The status code associated with the error.
     */
    private int statusCode;

    /**
     * The constructor for this enum, sets the value of message key and the HTTP status code.
     */
    ErrorKey(String messageKey, int statusCode) {
        this.messageKey = messageKey;
        this.statusCode = statusCode;
    }

    /**
     * Returns the error key for the message key reference.
     */
    public static ErrorKey fromMessageKey(String messageKey) {
        for (ErrorKey errorKey : values()) {
            if (errorKey.messageKey.equals(messageKey)) {
                return errorKey;
            }
        }

        return null;
    }

    /**
     * Checks if the passed name matches with one of the listed enum values.
     */
    public boolean equalsName(String otherName) {
        return otherName != null && messageKey.equals(otherName);
    }

    /**
     * Returns the string value of the enum.
     */
    @Override
    public String toString() {
        return messageKey;
    }

    /**
     * Returns the message key.
     */
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * Returns the status code for the error.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the default message for the error.
     */
    public String getMessage() {
        try {
            return defaultBundle.getString(messageKey);
        } catch (MissingResourceException e) {
            return "A REST error occurred";
        }
    }
}