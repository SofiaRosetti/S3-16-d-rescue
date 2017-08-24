package it.unibo.mobileuser.utils;

import android.content.Context;
import android.content.res.TypedArray;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Class with specific utils for mobileuser module.
 */
public class Utils {

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

}
