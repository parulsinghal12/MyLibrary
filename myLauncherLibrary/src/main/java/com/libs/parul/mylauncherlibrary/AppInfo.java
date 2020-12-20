package com.libs.parul.mylauncherlibrary;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class AppInfo {


    String appName;
    String pkgName;
    Drawable icon;
    Intent launcherIntent;
    int versionCode;
    String versionName;

    public AppInfo(String appName, Drawable icon, String pkgName) {
        this.appName = appName;
        this.icon = icon;
        this.pkgName = pkgName;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Intent getLauncherIntent() {
        return launcherIntent;
    }

    public void setLauncherIntent(Intent launcherIntent) {
        this.launcherIntent = launcherIntent;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

}
