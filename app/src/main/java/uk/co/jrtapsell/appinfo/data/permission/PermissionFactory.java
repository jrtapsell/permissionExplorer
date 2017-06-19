package uk.co.jrtapsell.appinfo.data.permission;

import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by james on 07/06/17.
 */

public class PermissionFactory {

    private static final PermissionFactory INSTANCE = new PermissionFactory();

    private PermissionFactory() {}

    public static PermissionFactory getInstance() {
        return INSTANCE;
    }

    private static final Map<String, MyPermission> seen = new HashMap<>();

    public MyPermission get(PermissionInfo info, PackageManager p) {
        if (seen.containsKey(info.name)) {
            return seen.get(info.name);
        }
        Drawable icon = info.loadIcon(p);
        CharSequence description = info.loadDescription(p);
        MyPermission perm = new MyPermission(
                info.name,
                description != null ? Objects.toString(description) : null,
                icon,
                info.protectionLevel
        );
        seen.put(info.name, perm);
        return perm;
    }

}
