package uk.co.jrtapsell.appinfo.data.permission;

import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PermissionFactory {

    @Nullable private static PermissionFactory INSTANCE;
    @NotNull private final PackageManager packageManager;

    private PermissionFactory(@NonNull final PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    @NotNull public static PermissionFactory getInstance(@NotNull final PackageManager packageManager) {
        if (INSTANCE == null) {
            INSTANCE = new PermissionFactory(packageManager);
        }
        return INSTANCE;
    }

    @NotNull private static final Map<String, MyPermission> seen = new HashMap<>();

    @NotNull public MyPermission get(PermissionInfo info) {
        if (seen.containsKey(info.name)) {
            return seen.get(info.name);
        }
        CharSequence description = info.loadDescription(packageManager);
        MyPermission perm = new MyPermission(
                info.name,
                description != null ? Objects.toString(description) : null,
                info.protectionLevel
        );
        seen.put(info.name, perm);
        return perm;
    }

    public MyPermission unknownPermission(String permName) {
        return new MyPermission(permName, "Permission unknown by system", PermissionInfo.PROTECTION_DANGEROUS);
    }

    @NonNull
    public MyPermission getPermission(String permName) {
        try {
            PermissionInfo perm = packageManager.getPermissionInfo(permName, PackageManager.GET_META_DATA);
            return get(perm);
        } catch (PackageManager.NameNotFoundException ex) {
            return  (unknownPermission(permName));
        }
    }
}
