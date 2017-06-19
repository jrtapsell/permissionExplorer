package uk.co.jrtapsell.appinfo.ui.multiple;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.app.AppFactory;
import uk.co.jrtapsell.appinfo.data.app.MyApp;

public class AppList extends AppCompatActivity {

    private AppFactory appFactory;
    private AppAdapter adapter;
    private final List<MyApp> apps = new ArrayList<>();
    private ProgressBar progressBar;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appFactory = AppFactory.getInstance(getPackageManager());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        lv = (ListView) findViewById(R.id.appList);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        adapter = new AppAdapter(this, apps);
        lv.setAdapter(adapter);
        new ScreenUpdater().execute();
    }

    class ScreenUpdater extends AsyncTask<Void, Integer, List<MyApp>> {
        @Override
        protected List<MyApp> doInBackground(Void... params) {
            List<MyApp> results = appFactory.getApps();
            Collections.sort(results);
            Collections.reverse(results);
            return results;
        }

        @Override
        protected void onPostExecute(List<MyApp> myApps) {
            apps.clear();
            apps.addAll(myApps);
            adapter.notifyDataSetChanged();
            lv.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
