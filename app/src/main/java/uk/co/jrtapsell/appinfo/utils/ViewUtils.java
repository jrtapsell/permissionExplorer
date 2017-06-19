package uk.co.jrtapsell.appinfo.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;


public class ViewUtils {

    private ViewUtils() {}

    /**
     * Gets a view with a given ID.
     *
     * @param view
     *  The view to get the view from.
     * @param id
     *  The ID of the view to get.
     * @param expected
     *  The expected type of the view.
     * @param <T>
     *  The type of the returned view.
     * @return
     *  The requested view.
     */
    @NotNull public static <T extends View> T getViewById(@NotNull View view, int id, Class<T> expected) {
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

    @NotNull public static TextView getTextView(@NotNull View view, int id) {
        return getViewById(view, id, TextView.class);
    }

    @NotNull public static ImageView getImageView(@NotNull View view, int id) {
        return getViewById(view, id, ImageView.class);
    }

    @NotNull public static GridLayout getGridLayout(@NotNull View view, int id) {
        return getViewById(view, id, GridLayout.class);
    }

    public static View createView(@NonNull ViewGroup parent, Context context, int layoutID) {
        return LayoutInflater.from(context).inflate(layoutID, parent, false);
    }
}
