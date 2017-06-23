package uk.co.jrtapsell.appinfo.data.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AppFactory {

    @NotNull private final PackageManager manager;
    @Nullable private HashMap<String, MyApp> apps = null;

    @Nullable private static AppFactory INSTANCE;

    private AppFactory(@NotNull final PackageManager manager) {
        this.manager = manager;
    }

    @NotNull public static AppFactory getInstance(final PackageManager manager) {
        if (INSTANCE == null) {
            INSTANCE = new AppFactory(manager);
        }
        return INSTANCE;
    }

    @NotNull public List<MyApp> getApps() {
        if (apps == null) {
            spawnApps();
        }
        return cloneApps();
    }

    @NonNull
    private List<MyApp> cloneApps() {
        Collection<MyApp> items = apps.values();
        List<MyApp> all = new ArrayList<>(items.size());
        all.addAll(items);
        return all;
    }

    private void spawnApps() {
        apps = new HashMap<>();
        List<ApplicationInfo> apps2 = manager.getInstalledApplications(0);
        for (ApplicationInfo app : apps2) {
            MyApp temp = new MyApp(app, manager);
            apps.put(temp.getPackageName(), temp);
        }
    }

    @NotNull public MyApp getApp(@NotNull String packageName) {
        if (apps != null && apps.containsKey(packageName)) {
            return apps.get(packageName);
        }
        try {
            ApplicationInfo info = manager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return new MyApp(info, manager);
        } catch (PackageManager.NameNotFoundException ex) {
            throw new AssertionError(ex);
        }
    }

}
