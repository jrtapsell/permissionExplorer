package uk.co.jrtapsell.appinfo.ui.single;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.app.AppFactory;
import uk.co.jrtapsell.appinfo.data.app.MyApp;
import uk.co.jrtapsell.appinfo.data.permission.MyPermission;

public class SingleApp extends AppCompatActivity {

    private AppFactory appFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        appFactory = AppFactory.getInstance(getPackageManager());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_app);
        
        setupApp(getApp());
    }

    private void setupApp(MyApp app) {
        setupAppList(app);
        setAppPackage(app);
        setupTopBar(app);
        setupButtons(app);
    }

    private void setAppPackage(MyApp app) {
        TextView appPackage = (TextView) findViewById(R.id.appName);
        appPackage.setText(app.getPackageName());
    }

    private void setupAppList(MyApp app) {
        ListView listView = (ListView) findViewById(R.id.permissionsId);
        List<MyPermission> perms = app.getPermissions();
        Collections.sort(perms);
        listView.setAdapter(new PermissionAdapter(this, perms));
    }

    @NonNull
    private MyApp getApp() {
        Bundle intentExtras = getIntent().getExtras();
        String appName = intentExtras.getString("app");
        if (appName == null) {
            throw new AssertionError("Empty app name");
        }
        return appFactory.getApp(appName);
    }

    private void setupButtons(MyApp app) {
        setupManageButton(app);
        setupOpenButton(app);
    }

    private void setupManageButton(MyApp app) {
        final Intent manageIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        manageIntent.addCategory(Intent.CATEGORY_DEFAULT);
        manageIntent.setData(Uri.parse("package:" + app.getPackageName()));


        findViewById(R.id.manageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(manageIntent);
            }
        });
    }

    private void setupOpenButton(MyApp app) {
        final Intent openIntent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());

        Button openButton = (Button) findViewById(R.id.openButton);

        if (openIntent == null) {
            setupUnopenable(openButton);
        } else {
            setupOpenable(openIntent, openButton);
        }
    }

    private void setupUnopenable(Button openButton) {
        openButton.setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            openButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
        }
    }

    private void setupOpenable(final Intent openIntent, Button openButton) {
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openIntent);
            }
        });
    }

    private void setupTopBar(MyApp app) {
        ActionBar topBar = getSupportActionBar();

        if (topBar == null) {
            throw new AssertionError("getSupportActionBar() returned null");
        }

        topBar.setDisplayUseLogoEnabled(true);
        topBar.setDisplayShowHomeEnabled(true);
        topBar.setTitle(app.getName());
        topBar.setIcon(app.getIcon());
    }
}
