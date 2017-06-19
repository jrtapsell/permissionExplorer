package uk.co.jrtapsell.appinfo.data.permission;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.jrtapsell.appinfo.R;
import uk.co.jrtapsell.appinfo.utils.ColorUtils;

import static android.content.pm.PermissionInfo.*;

public class MyPermission implements Comparable<MyPermission>{
    @NotNull private final String name;
    @Nullable private final String description;
    private final int level;

    MyPermission(@NotNull String name, @Nullable String description, int level) {
        this.name = name;
        this.description = description;
        this.level = level;
    }

    @Override
    public int compareTo(@NonNull MyPermission o) {
        if (isPrivileged() && !o.isPrivileged()) {
            return -1;
        }
        if (!isPrivileged() && o.isPrivileged()) {
            return 1;
        }
        if (isDangerous() && !o.isDangerous()) {
            return -1;
        }
        if (!isDangerous() && o.isDangerous()) {
            return 1;
        }
        return name.compareTo(o.name);
    }

    public boolean isDangerous() {
        return checkFlag(PROTECTION_DANGEROUS);
    }

    @SuppressWarnings("deprecation")
    public boolean isPrivileged() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkFlag(PROTECTION_FLAG_PRIVILEGED);
        } else {
            return checkFlag(PROTECTION_FLAG_SYSTEM);
        }
    }

    public boolean checkFlag(int check) {
        return (check & level) != 0;
    }

    @Nullable public String getDescription() {
        return description;
    }

    @NonNull public String getName() {
        return name;
    }

    public int getColour(Context context) {
        if (checkFlag(PROTECTION_FLAG_PRIVILEGED)) {
            return ColorUtils.getColor(context, R.color.privilegedPermission);
        } else if (checkFlag(PROTECTION_DANGEROUS)) {
            return  ColorUtils.getColor(context, R.color.dangerousPermission);
        }
        return  ColorUtils.getColor(context, R.color.safePermission);
    }
}
