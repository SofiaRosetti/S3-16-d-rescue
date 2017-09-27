package it.unibo.mobileuser.utils;

/**
 * Enum of names of preferences to use in shared preferences.
 */
public enum PreferencesKey {
    USER_ID("userID"),
    USER_NAME("userName"),
    USER_SURNAME("userSurname"),
    USER_EMAIL("userEmail"),
    USER_PHONE("userPhone"),
    EVENT_TYPE("eventType");

    private final String keyName;

    /**
     * Create a name of preference well formed.
     *
     * @param keyName the name of preference
     */
    PreferencesKey(final String keyName) {
        this.keyName = keyName;
    }

    /**
     * Gets the name of preference.
     *
     * @return the name of preference
     */
    protected String getKeyName() {
        return this.keyName;
    }


}
