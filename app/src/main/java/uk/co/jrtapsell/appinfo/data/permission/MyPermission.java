package uk.co.jrtapsell.appinfo.data.permission;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import static android.content.pm.PermissionInfo.*;

/**
 * Created by james on 07/06/17.
 */

public class MyPermission implements Comparable<MyPermission>{
    private final String name;
    private final String description;
    private final Drawable icon;
    private final int level;

    public MyPermission(String name, String description, Drawable icon, int level) {
        this.name = name;
        this.description = description;
        this.icon = icon;
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

    public boolean isPrivileged() {
        return checkFlag(PROTECTION_FLAG_PRIVILEGED);
    }


    public boolean checkFlag(int check) {
        return (check & level) != 0;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
