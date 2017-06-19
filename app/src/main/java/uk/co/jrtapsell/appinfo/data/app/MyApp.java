package uk.co.jrtapsell.appinfo.data.app;

import android.content.Context;
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

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.data.permission.MyPermission;
import uk.co.jrtapsell.appinfo.data.permission.PermissionFactory;
import uk.co.jrtapsell.appinfo.utils.ColorUtils;

import static android.content.pm.PermissionInfo.PROTECTION_DANGEROUS;
import static android.content.pm.PermissionInfo.PROTECTION_FLAG_PRIVILEGED;

public class MyApp implements Comparable<MyApp> {
    @NotNull private final String name;
    @NotNull private final Drawable icon;
    @NotNull private final String packageName;
    private final boolean isInstant;
    private int color;

    @NotNull public List<MyPermission> getPermissions() {
        return permissions;
    }

    @NotNull private final List<MyPermission> permissions = new ArrayList<>();

    MyApp(ApplicationInfo info, PackageManager p) {
        boolean isInstant = false;
        PermissionFactory pf = PermissionFactory.getInstance(p);
        this.name = Objects.toString(info.loadLabel(p));
        this.icon = info.loadIcon(p);
        this.packageName = info.packageName;
        try {
            PackageInfo pi = p.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
            if (pi.requestedPermissions != null) {
                for (String permName : pi.requestedPermissions) {
                    try {
                        PermissionInfo perm = p.getPermissionInfo(permName, PackageManager.GET_META_DATA);
                        MyPermission myPermission = pf.get(perm);
                        permissions.add(myPermission);
                    } catch (PackageManager.NameNotFoundException ex) {
                        permissions.add(pf.unknownPermission(permName));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException ex) {
            isInstant = true;
        }
        this.isInstant = isInstant;
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

    public boolean isInstant() {
        return isInstant;
    }

    public int getColour(Context context) {
        if (isInstant) {
            return  ColorUtils.getColor(context, R.color.dangerousPermission);
        }
        return  ColorUtils.getColor(context, R.color.safePermission);
    }
}
