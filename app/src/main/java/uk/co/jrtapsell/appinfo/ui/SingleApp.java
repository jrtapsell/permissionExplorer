package uk.co.jrtapsell.appinfo.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.app.AppFactory;
import uk.co.jrtapsell.appinfo.data.app.MyApp;
import uk.co.jrtapsell.appinfo.data.permission.MyPermission;

public class SingleApp extends AppCompatActivity {

    private static final AppFactory af = AppFactory.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_app);
        final MyApp app = af.getApp(getIntent().getExtras().getString("app"), getPackageManager());
        ListView lv = (ListView) findViewById(R.id.permissionsId);
        List<MyPermission> perms = app.getPermissions();
        Collections.sort(perms);
        lv.setAdapter(new PermissionAdapter(this, perms));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(app.getName());
        getSupportActionBar().setIcon(app.getIcon());

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
}
