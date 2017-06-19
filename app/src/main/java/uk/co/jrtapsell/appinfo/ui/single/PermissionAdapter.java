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

    public static final List<Pair<String, Integer>> TEXT_LABELS = Arrays.asList(
            new Pair<>("PRIVILEGED", PROTECTION_FLAG_PRIVILEGED),
            new Pair<>("DANGEROUS", PROTECTION_DANGEROUS),
            new Pair<>("SIGNATURE", PROTECTION_SIGNATURE),
            new Pair<>("PREINSTALLED", PROTECTION_FLAG_PREINSTALLED)
    );

    public PermissionAdapter(Context singleApp, List<MyPermission> app) {
        super(singleApp, R.layout.single_permission, app);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyPermission perm = getItem(position);

        if (perm == null) {
            throw new AssertionError("Null permission");
        }

        Context context = getContext();
        if (convertView == null) {
            convertView = ViewUtils.createView(parent, context, R.layout.single_permission);
        }

        TextView name = ViewUtils.getTextView(convertView, R.id.permissionName);
        TextView desc = ViewUtils.getTextView(convertView, R.id.permissionDescription);
        TextView labelText = ViewUtils.getTextView(convertView, R.id.permissionLevel);
        GridLayout gl = ViewUtils.getGridLayout(convertView, R.id.permissionBox);

        char[] letters = convertName(perm.getName());

        name.setText(letters, 0, letters.length);

        if (perm.getDescription() == null) {
            desc.setText(R.string.missing_description);
        } else {
            desc.setText(perm.getDescription());
        }

        StringBuilder labeler = makeTextLabels(perm);

        labelText.setText(labeler.toString());

        int color = perm.getColour(context);
        gl.setBackgroundColor(color);
        return convertView;
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
