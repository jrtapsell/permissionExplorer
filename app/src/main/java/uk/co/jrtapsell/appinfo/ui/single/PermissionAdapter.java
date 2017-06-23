package uk.co.jrtapsell.appinfo.ui.single;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.permission.MyPermission;
import uk.co.jrtapsell.appinfo.utils.ViewUtils;

import static android.content.pm.PermissionInfo.*;

class PermissionAdapter extends ArrayAdapter<MyPermission> {
    private final Context context = getContext();

    public static final List<Pair<String, Integer>> TEXT_LABELS = Arrays.asList(
            new Pair<>("PRIVILEGED", PROTECTION_FLAG_PRIVILEGED),
            new Pair<>("DANGEROUS", PROTECTION_DANGEROUS),
            new Pair<>("SIGNATURE", PROTECTION_SIGNATURE),
            new Pair<>("PREINSTALLED", PROTECTION_FLAG_PREINSTALLED),
            new Pair<>("SYSTEM", PROTECTION_FLAG_SYSTEM)
    );

    public PermissionAdapter(Context singleApp, List<MyPermission> app) {
        super(singleApp, R.layout.single_permission, app);
    }

    @Override
    @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final MyPermission perm = getItem(position);

        if (perm == null) {
            throw new AssertionError("Null permission");
        }

        if (convertView == null) {
            convertView = ViewUtils.createView(parent, context, R.layout.single_permission);
        }

        setPermissionName(convertView, perm);
        setAppDescription(convertView, perm);
        setAppLabel(convertView, perm);
        setAppColour(convertView, perm);

        return convertView;
    }

    private void setAppColour(@Nullable View convertView, MyPermission perm) {
        GridLayout gl = ViewUtils.getGridLayout(convertView, R.id.permissionBox);
        gl.setBackgroundColor(perm.getColour(context));
    }

    private void setAppLabel(@Nullable View convertView, MyPermission perm) {
        StringBuilder labeler = makeTextLabels(perm);
        TextView labelText = ViewUtils.getTextView(convertView, R.id.permissionLevel);
        labelText.setText(labeler.toString());
    }

    private void setAppDescription(@Nullable View convertView, MyPermission perm) {
        TextView desc = ViewUtils.getTextView(convertView, R.id.permissionDescription);
        if (perm.getDescription() == null) {
            desc.setText(R.string.missing_description);
        } else {
            desc.setText(perm.getDescription());
        }
    }

    private void setPermissionName(@Nullable View convertView, MyPermission perm) {
        TextView name = ViewUtils.getTextView(convertView, R.id.permissionName);
        char[] letters = convertName(perm.getName());
        name.setText(letters, 0, letters.length);
    }

    @NonNull
    private StringBuilder makeTextLabels(MyPermission perm) {
        StringBuilder labeler = new StringBuilder();
        for (Pair<String, Integer> level : TEXT_LABELS) {
            if (perm.checkFlag(level.second)) {
                labeler.append(level.first);
                labeler.append(" ");
            }
        }
        if (labeler.length() == 0) {
            labeler.append("NORMAL");
        }
        return labeler;
    }

    private char[] convertName(String name1) {
        char[] letters = name1.toCharArray();
        letters[name1.lastIndexOf('.')] = '\n';
        return letters;
    }

}
