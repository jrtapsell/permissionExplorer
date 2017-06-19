package uk.co.jrtapsell.appinfo.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ColorUtils {
    public static int getColor(Context permissionAdapter, int name) {
        return ContextCompat.getColorStateList(permissionAdapter, name).getDefaultColor();
    }
}
