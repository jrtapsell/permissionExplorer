package uk.co.jrtapsell.appinfo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.app.AppFactory;
import uk.co.jrtapsell.appinfo.data.app.MyApp;

public class AppList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        ListView lv = (ListView) findViewById(R.id.appList);
        List<MyApp> apps = AppFactory.getApps(getPackageManager());
        Collections.sort(apps);
        Collections.reverse(apps);
        final AppList outer = this;
        lv.setAdapter(new AppAdapter(this, apps, outer));
    }

}
