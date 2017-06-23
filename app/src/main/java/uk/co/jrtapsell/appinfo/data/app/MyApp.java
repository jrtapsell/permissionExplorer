package uk.co.jrtapsell.appinfo.data.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.permission.MyPermission;
import uk.co.jrtapsell.appinfo.data.permission.PermissionFactory;
import uk.co.jrtapsell.appinfo.utils.ColorUtils;

public class MyApp implements Comparable<MyApp> {
    @NotNull private final String name;
    @NotNull private final Drawable icon;
    @NotNull private final String packageName;
    private final boolean isInstant;

    @NotNull public List<MyPermission> getPermissions() {
        return permissions;
    }

    @NotNull private final List<MyPermission> permissions = new ArrayList<>();

    MyApp(ApplicationInfo info, PackageManager manager) {
        boolean isInstant = false;
        PermissionFactory pf = PermissionFactory.getInstance(manager);
        this.name = Objects.toString(info.loadLabel(manager));
        this.icon = info.loadIcon(manager);
        this.packageName = info.packageName;
        try {
            processPermissions(info, manager, pf);
        } catch (PackageManager.NameNotFoundException ex) {
            isInstant = true;
        }
        this.isInstant = isInstant;
    }

    private void processPermissions(ApplicationInfo info, PackageManager manager, PermissionFactory pf) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = manager.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
        if (packageInfo.requestedPermissions != null) {
            for (String permName : packageInfo.requestedPermissions) {
                permissions.add(pf.getPermission(permName));
            }
        }
    }

    @Override
    public int compareTo(@NonNull MyApp o) {
        return Integer.compare(permissions.size(), o.permissions.size());
    }

    @NonNull public String getPerms() {
        return permissions.size() + " Permissions";
    }

    @NonNull public String getName() {
        return name;
    }

    @NonNull public Drawable getIcon() {
        return icon;
    }

    @NonNull public String getPackageName() {
        return packageName;
    }

    public int getColour(Context context) {
        if (isInstant) {
            return  ColorUtils.getColor(context, R.color.instantApp);
        }
        return  ColorUtils.getColor(context, R.color.normalApp);
    }
}
