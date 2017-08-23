package it.unibo.drescue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utiliry class for strings.
 */
public class StringUtils {

    private static final String EMAIL_REGEX = "^[-\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,}$";

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
}
