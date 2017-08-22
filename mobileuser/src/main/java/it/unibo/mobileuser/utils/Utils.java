package it.unibo.mobileuser.utils;

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

}
