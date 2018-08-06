/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author DangTin. Create on 2017/11/28
 * ******************************************************************************
 */

package com.grocery.utils;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

public class StringUtil {

    /**
     * Check format of email
     *
     * @param target is a email need checking
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Check field not empty and 6 word
     *
     * @param input text input
     */
    public static boolean isGoodField(String input) {
        if (input == null || input.isEmpty() || input.length() < 6)
            return false;
        return true;
    }

    /**
     * Check field not empty
     *
     * @param input text input
     */
    public static boolean isEmpty(String input) {
        if (input == null || input.isEmpty() || ("").equals(input.trim()))
            return true;
        return false;
    }

    public static boolean isQuantity(String input) {
        float quantity = Float.parseFloat(input);
        if (quantity > 0)
            return true;
        return false;
    }

    public static String getDoubleNumber(int number) {
        if (number < 10) {
            return "0" + number;
        } else return "" + number;
    }

    public static String getDoubleLongNumber(long number) {
        if (number < 10) {
            return "0" + number;
        } else return "" + number;
    }

    public static boolean isNumberPhone(String input) {
        if (input != null && !("").equals(input.trim())) {
            if ((9 < input.length()) && (input.length() < 13)) {
                return true;
            } else return false;
        } else return true;
    }

    public static String getFullFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1, url.length());
    }

    private static String getFullFileNameWithoutExtensionFromUrl(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    private static String getFileExtension(String url) {
        return url.substring(url.lastIndexOf("."));
    }

    /**
     * Merge all elements of a string array into a string
     *
     * @param strings
     * @param separator
     * @return
     */
    public static String join(String[] strings, String separator) {
        StringBuffer sb = new StringBuffer();
        int max = strings.length;
        for (int i = 0; i < max; i++) {
            if (i != 0)
                sb.append(separator);
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    public static SpannableString span(String str) {
        SpannableString strSpan = new SpannableString(str);
        strSpan.setSpan(new UnderlineSpan(), 0, strSpan.length(), 0);
        return strSpan;
    }
}
