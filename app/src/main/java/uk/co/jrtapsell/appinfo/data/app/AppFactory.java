package uk.co.jrtapsell.appinfo.data.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AppFactory {

    private HashMap<String, MyApp> apps = null;

    private static final AppFactory INSTANCE = new AppFactory();

    private AppFactory() {}

    public static AppFactory getInstance() {
        return INSTANCE;
    }

    public List<MyApp> getApps(final PackageManager manager) {
        if (apps == null) {
            apps = new HashMap<>();
            List<ApplicationInfo> apps2 = manager.getInstalledApplications(0);
            for (ApplicationInfo app : apps2) {
                MyApp temp = spawn(app, manager);
                apps.put(temp.getPackageName(), temp);
            }
        }
        Collection<MyApp> items = apps.values();
        List<MyApp> all = new ArrayList<>(items.size());
        all.addAll(items);
        return all;
    }

    public MyApp getApp(String packageName, PackageManager pm) {
        if (apps != null && apps.containsKey(packageName)) {
            return apps.get(packageName);
        }
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return spawn(info, pm);
        } catch (PackageManager.NameNotFoundException ex) {
            return null;
        }
    }

    public MyApp spawn(ApplicationInfo info, PackageManager p) {
        return new MyApp(info, p);
    }
}
