package uk.co.jrtapsell.appinfo.data.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.jrtapsell.appinfo.data.permission.MyPermission;
import uk.co.jrtapsell.appinfo.data.permission.PermissionFactory;

public class MyApp implements Comparable<MyApp> {
    @NotNull private final String name;
    @NotNull private final Drawable icon;
    @NotNull private final String packageName;

    @NotNull public List<MyPermission> getPermissions() {
        return permissions;
    }

    @NotNull private final List<MyPermission> permissions;

    MyApp(ApplicationInfo info, PackageManager p) {
        PermissionFactory pf = PermissionFactory.getInstance(p);
        this.name = Objects.toString(info.loadLabel(p));
        this.icon = info.loadIcon(p);
        this.packageName = info.packageName;
        List<MyPermission> temp;
        try {
            PackageInfo pi = p.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
            temp = new ArrayList<>();
            if (pi.requestedPermissions != null) {
                for (String permName : pi.requestedPermissions) {
                    try {
                        PermissionInfo perm = p.getPermissionInfo(permName, PackageManager.GET_META_DATA);
                        MyPermission myPermission = pf.get(perm);
                        temp.add(myPermission);
                    } catch (PackageManager.NameNotFoundException ex) {
                        temp.add(pf.unknownPermission(permName));
                    }
                }
            } else {
                temp = new ArrayList<>();
            }
        } catch (PackageManager.NameNotFoundException ex) {
            temp = new ArrayList<>();
        }
        this.permissions = temp;
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
}
