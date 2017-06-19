package uk.co.jrtapsell.appinfo.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.app.MyApp;
import uk.co.jrtapsell.appinfo.utils.ViewUtils;

class AppAdapter extends ArrayAdapter<MyApp> {
    @NotNull private Context context;

    public AppAdapter(@NotNull Context context, @NotNull List<MyApp> apps) {
        super(context, R.layout.app_list_item, apps);
        this.context = context;
    }

    @Override
    @NotNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        @Nullable final MyApp myApp = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_list_item, parent, false);
        }

        @NotNull final TextView appName = ViewUtils.getTextView(convertView, R.id.appName);
        @NotNull final TextView appPackage = ViewUtils.getTextView(convertView, R.id.appPackage);
        @NotNull final TextView appType = ViewUtils.getTextView(convertView, R.id.appType);
        @NotNull final ImageView appIcon = ViewUtils.getImageView(convertView, R.id.appIcon);
        @NotNull final GridLayout layout = ViewUtils.getGridLayout(convertView, R.id.appOuter);

        if (myApp == null) {
            appName.setText("NULL APP");
            appIcon.setImageDrawable(null);
            appPackage.setText("NULL PACKAGE");
            appType.setText("NULL TYPE");
            return convertView;
        }

        appIcon.setImageDrawable(myApp.getIcon());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SingleApp.class);
                i.putExtra("app", myApp.getPackageName());
                context.startActivity(i);
            }
        });

        appName.setText(myApp.getName());
        appPackage.setText(myApp.getPackageName());
        appType.setText(myApp.getPerms());
        return convertView;
    }

}
