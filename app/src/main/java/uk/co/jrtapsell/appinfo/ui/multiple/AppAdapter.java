package uk.co.jrtapsell.appinfo.ui.multiple;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import uk.co.jrtapsell.appinfo.ui.single.SingleApp;
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

        if (myApp == null) {
            throw new AssertionError("Null app detected");
        }

        if (convertView == null) {
            convertView = ViewUtils.createView(parent, context, R.layout.app_list_item);
        }

        setAppName(convertView, myApp);
        setAppPackage(convertView, myApp);
        setAppPerms(convertView, myApp);
        setAppIcon(convertView, myApp);
        setupLayout(convertView, myApp);
        return convertView;
    }

    private void setAppIcon(@Nullable View convertView, MyApp myApp) {
        @NotNull final ImageView appIcon = ViewUtils.getImageView(convertView, R.id.appIcon);
        appIcon.setImageDrawable(myApp.getIcon());
    }

    private void setupLayout(@Nullable View convertView, final MyApp myApp) {
        @NotNull final GridLayout layout = ViewUtils.getGridLayout(convertView, R.id.appOuter);
        layout.setBackgroundColor(myApp.getColour(context));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SingleApp.class);
                i.putExtra("app", myApp.getPackageName());
                context.startActivity(i);
            }
        });
    }

    private void setAppPerms(@Nullable View convertView, MyApp myApp) {
        @NotNull final TextView appPerms = ViewUtils.getTextView(convertView, R.id.appPerms);
        appPerms.setText(myApp.getPerms());
    }

    private void setAppPackage(@Nullable View convertView, MyApp myApp) {
        @NotNull final TextView appPackage = ViewUtils.getTextView(convertView, R.id.appPackage);
        appPackage.setText(myApp.getPackageName());
    }

    private void setAppName(@Nullable View convertView, MyApp myApp) {
        @NotNull final TextView appName = ViewUtils.getTextView(convertView, R.id.appName);
        appName.setText(myApp.getName());
    }

}
