package it.unibo.mobileuser.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Collections;
import java.util.Set;

/**
 * Class with specific utils for mobileuser module.
 */
public class Utils {

    /**
     * Get MaterialEditText typed text.
     *
     * @param editText the material edit text
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
     * Gets user data of the logged user from shared preferences.
     *
     * @param context the context of activity
     * @param key     the name of preference to retrieve
     * @return the preference value if it exists, otherwise empty string.
     */
    public static String getUserDataFromSharedPreferences(final Context context, final PreferencesKey key) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key.getKeyName(), "");
    }

    /**
     * Gets data set of the logged user from shared preferences.
     *
     * @param context the context of activity
     * @return the preferences value if they exist, otherwise empty set.
     */
    public static Set<String> getDataSetFromSharedPreferences(final Context context) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getStringSet(PreferencesKey.EVENT_TYPE.getKeyName(), Collections.emptySet());
    }

    /**
     * Sets the data who performed login into shared preferences.
     *
     * @param context  the context of activity
     * @param key      the name of preference to insert
     * @param valueSet the set of values for the preferences
     */
    public static void setDataInSharedPreferences(final Context context, final PreferencesKey key, final Set<String> valueSet) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!valueSet.isEmpty()) {
            if (key == PreferencesKey.EVENT_TYPE) {
                sharedPreferences.edit().putStringSet(key.getKeyName(), valueSet).apply();
            } else {
                final String value = valueSet.iterator().next();
                sharedPreferences.edit().putString(key.getKeyName(), value).apply();
            }
        }
    }

    /**
     * Removes all value from the preferences.
     *
     * @param context the context of activity
     */
    public static void deleteSharedPreferences(final Context context) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().clear().apply();
    }

}
