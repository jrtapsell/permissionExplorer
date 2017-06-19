package uk.co.jrtapsell.appinfo.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.app.MyApp;
import uk.co.jrtapsell.appinfo.utils.ViewUtils;

class AppAdapter extends ArrayAdapter<MyApp> {
    private AppList appList;
    private final AppList outer;

    public AppAdapter(AppList appList, List<MyApp> apps, AppList outer) {
        super(appList, R.layout.app_list_item, apps);
        this.appList = appList;
        this.outer = outer;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final MyApp myApp = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_list_item, parent, false);
        }

        final TextView appName = ViewUtils.getById(convertView, R.id.appName);
        final TextView appPackage = ViewUtils.getById(convertView, R.id.appPackage);
        final TextView appType = ViewUtils.getById(convertView, R.id.appType);
        final ImageView appIcon = ViewUtils.getById(convertView, R.id.appIcon);
        final GridLayout layout = ViewUtils.getById(convertView, R.id.appOuter);

        appIcon.setImageDrawable(myApp.getIcon());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(outer, SingleApp.class);
                i.putExtra("app", myApp.getPackageName());
                appList.startActivity(i);
            }
        });

        appName.setText(myApp.getName());
        appPackage.setText(myApp.getPackageName());
        appType.setText(myApp.getPerms());
        return convertView;
    }

}
