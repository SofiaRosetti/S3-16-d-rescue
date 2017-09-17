package it.unibo.drescue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for strings.
 */
public class StringUtils {

    private static final String EMAIL_REGEX = "^[-\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,}$";
    private static final String MESSAGE_TYPE = "messageType";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]*$";
    private static final int MIN_LENGTH_PASSWORD = 6;

    /**
     * Checks if a string is null or empty
     *
     * @param string
     * @return true if is null or empty, false otherwise
     */
    public static boolean isAValidString(final String string) {
        return string != null && !string.trim().isEmpty();
    }

    /**
     * Checks if a string is in a valid email format
     *
     * @param email
     * @return true if matches a regex, false otherwise
     */
    public static boolean isAValidEmail(final String email) {
        final CharSequence charSequenceEmail = email;

        final Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(charSequenceEmail);

        return matcher.matches();
    }

    /**
     * Check if a string is in a valid password format.
     *
     * @param password password string
     * @return true if password lenght is correct and password matches a regex, otherwise false
     */
    public static boolean isAValidPassword(final String password) {
        final Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        final Matcher matcher = pattern.matcher(password);
        return password.length() >= MIN_LENGTH_PASSWORD && matcher.matches();
    }

    /**
     * Gets the message type of the given json message.
     *
     * @param jsonMessage
     * @return the json message type
     */
    public static String getMessageType(final String jsonMessage) {
        final JsonElement je = new JsonParser().parse(jsonMessage);
        final JsonObject jo = je.getAsJsonObject();
        return jo.get(MESSAGE_TYPE).getAsString();
    }

}
