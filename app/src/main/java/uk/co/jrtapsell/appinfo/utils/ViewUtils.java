package uk.co.jrtapsell.appinfo.utils;

import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ViewUtils {
    private ViewUtils() {}
    public static <T extends View> T getById(View view, int id, Class<T> expected) {
        View foundView = view.findViewById(id);
        try {
            return expected.cast(foundView);
        } catch (ClassCastException ex) {
            throw new AssertionError(String.format(
                    "Bad type: %s, expected %s",
                    foundView.getClass().getCanonicalName(),
                    expected.getCanonicalName()));
        }
    }

    public static TextView getTextView(View view, int id) {
        return getById(view, id, TextView.class);
    }

    public static ImageView getImageView(View view, int id) {
        return getById(view, id, ImageView.class);
    }

    public static GridLayout getGridLayout(View view, int id) {
        return getById(view, id, GridLayout.class);
    }

    public static ListView getListView(View view, int id) {
        return getById(view, id, ListView.class);
    }
}
