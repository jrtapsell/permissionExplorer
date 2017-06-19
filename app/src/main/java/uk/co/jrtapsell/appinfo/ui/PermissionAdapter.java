package uk.co.jrtapsell.appinfo.ui;

import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.app.MyApp;
import uk.co.jrtapsell.appinfo.data.permission.MyPermission;

import static android.content.pm.PermissionInfo.*;

/**
 * Created by james on 07/06/17.
 */
class PermissionAdapter extends ArrayAdapter<MyPermission> {
    private SingleApp singleApp;

    public PermissionAdapter(SingleApp singleApp, List<MyPermission> app) {
        super(singleApp, R.layout.single_permission, app);
        this.singleApp = singleApp;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyPermission perm = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_permission, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.permissionName);
        String name1 = perm.getName();
        char[] letters = name1.toCharArray();
        letters[name1.lastIndexOf('.')] = '\n';
        name.setText(letters, 0, letters.length);
        TextView desc = (TextView) convertView.findViewById(R.id.permissionDescription);
        if (perm.getDescription() == null) {
            desc.setText("No description provided");
        } else {
            desc.setText(perm.getDescription());
        }

        TextView labelText = (TextView) convertView.findViewById(R.id.permissionLevel);
        StringBuilder labeler = new StringBuilder();
        for (Pair<String, Integer> level : new Pair[]{
                new Pair<>("PRIVILEGED", PROTECTION_FLAG_PRIVILEGED),
                new Pair<>("DANGEROUS", PROTECTION_DANGEROUS),
                new Pair<>("SIGNATURE", PROTECTION_SIGNATURE),
                new Pair<>("PREINSTALLED", PROTECTION_FLAG_PREINSTALLED)
        }) {
            if (perm.checkFlag(level.second)) {
                labeler.append(level.first);
                labeler.append(" ");
            }
        }
        if (labeler.length() == 0) {
            labeler.append("NORMAL");
        }
        labelText.setText(labeler.toString());

        GridLayout gl = (GridLayout) convertView.findViewById(R.id.permissionBox);
        int color;
        if (perm.checkFlag(PROTECTION_FLAG_PRIVILEGED)) {
            color = Color.rgb(200, 150, 150);
        } else if (perm.checkFlag(PROTECTION_DANGEROUS)) {
            color = Color.rgb(200, 200, 150);
        } else {
            color = Color.rgb(150, 200, 150);
        }
        gl.setBackgroundColor(color);
        return convertView;
    }
}
