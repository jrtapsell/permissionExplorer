package uk.co.jrtapsell.appinfo.utils;

import android.view.View;

/**
 * Created by james on 11/06/17.
 */

public class ViewUtils {
    private ViewUtils() {}
    public static <T extends View> T getById(View view, int id) {
        return (T) view.findViewById(id);
    }
}
