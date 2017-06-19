package uk.co.jrtapsell.appinfo.ui.single;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

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

        ListView listView = (ListView) findViewById(R.id.permissionsId);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);

        final MyApp app = getApp();
        
        List<MyPermission> perms = app.getPermissions();
        Collections.sort(perms);

        listView.setAdapter(new PermissionAdapter(this, perms));

        setupTopBar(app);

        setupButtons(app);
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
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
        final Intent manageIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        manageIntent.addCategory(Intent.CATEGORY_DEFAULT);
        manageIntent.setData(Uri.parse("package:" + app.getPackageName()));


        final Intent openIntent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());


        findViewById(R.id.manageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(manageIntent);
            }
        });

        Button openButton = (Button) findViewById(R.id.openButton);

        if (openIntent == null) {
            openButton.setEnabled(false);
        }

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
