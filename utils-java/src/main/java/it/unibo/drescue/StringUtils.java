package it.unibo.drescue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String EMAIL_REGEX = "^[-\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,}$";

    public static boolean isAValidString(final String string) {
        return string != null && !string.trim().isEmpty();
    }

    public static boolean isAValidEmail(final String email) {
        final CharSequence charSequenceEmail = email;

        final Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(charSequenceEmail);

        return matcher.matches();
    }
}
