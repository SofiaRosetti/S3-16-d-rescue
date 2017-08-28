package it.unibo.mobileuser.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Class with specific utils for mobileuser module.
 */
public class Utils {

    private static final String USER_ID = "userID";

    /**
     * Get MaterialEditText typed text.
     *
     * @param editText
     * @return the typed text
     */
    public static String getEditTextString(final MaterialEditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * Return an array of strings saved in resources.
     *
     * @param context the context of view
     * @param id      the id of the resource
     * @return an array of strings
     */
    public static String[] getStringArrayByResources(final Context context, final int id) {
        return context.getResources().getStringArray(id);
    }

    /**
     * Return an array of drawable saved in resources.
     *
     * @param context the context of view
     * @param id      the id of the resource
     * @return an array of drawable
     */
    public static TypedArray getDrawableArrayByResources(final Context context, final int id) {
        return context.getResources().obtainTypedArray(id);
    }

    /**
     * Gets logged user from shared preferences.
     *
     * @param context
     * @return the userID of the user
     */
    public static String getUserIDfromSharedPreferences(final Context context) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_ID, "");
    }

    /**
     * Sets the user who performed login into shared preferences.
     *
     * @param context
     * @param userID
     */
    public static void setUserIDinSharedPreferences(final Context context, final String userID) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(USER_ID, userID).apply();
    }

}
