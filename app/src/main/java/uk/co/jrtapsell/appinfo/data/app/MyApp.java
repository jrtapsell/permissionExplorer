package uk.co.jrtapsell.appinfo.data.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import uk.co.jrtapsell.appinfo.data.permission.MyPermission;
import uk.co.jrtapsell.appinfo.data.permission.PermissionFactory;

public class MyApp implements Comparable<MyApp> {
    private final String name;
    private final Drawable icon;
    private final String packageName;
    private static final PermissionFactory pf = PermissionFactory.getInstance();

    public List<MyPermission> getPermissions() {
        return permissions;
    }

    private final List<MyPermission> permissions;

    public MyApp(ApplicationInfo info, PackageManager p) {
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
                        MyPermission myPermission = pf.get(perm, p);
                        temp.add(myPermission);
                    } catch (PackageManager.NameNotFoundException ex) {

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

    @NonNull
    public String getPerms() {
        return permissions.size() + " Permissions";
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPackageName() {
        return packageName;
    }
}
