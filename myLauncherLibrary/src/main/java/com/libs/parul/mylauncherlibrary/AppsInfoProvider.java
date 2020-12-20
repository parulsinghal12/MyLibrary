package com.libs.parul.mylauncherlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppsInfoProvider {

    static Context mContext ;
    private AppsInfoProvider(){

    }

    private static class BillPughSingleton{
        private static final AppsInfoProvider INSTANCE = new AppsInfoProvider();
    }

    public static AppsInfoProvider getInstance(Context appCtx){
        mContext = appCtx;
        return BillPughSingleton.INSTANCE;
    }
    
    public static List<AppInfo> getInstalledApps() {


        List<AppInfo> packageNameHashSet = new ArrayList<>();
        PackageManager pm = mContext.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);

        for (ResolveInfo info : resolveInfos) {
            // add pkg
            ApplicationInfo applicationInfo;
            if (info == null || (applicationInfo = info.activityInfo.applicationInfo) == null
                    || !applicationInfo.enabled || packageNameHashSet.contains(applicationInfo.packageName)) {
                continue;
            }
            String appName = applicationInfo.loadLabel(mContext.getPackageManager()).toString();
            Drawable icon = applicationInfo.loadIcon(mContext.getPackageManager());
            String packages = applicationInfo.packageName;
            AppInfo appInfo = new AppInfo(appName, icon, packages);

            final PackageInfo pInfo;
            try {
                pInfo = pm.getPackageInfo(mContext.getPackageName(), 0);
                appInfo.versionCode = (int) pInfo.getLongVersionCode();
                appInfo.versionName = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            appInfo.launcherIntent = pm.getLaunchIntentForPackage(packages);

            packageNameHashSet.add(appInfo);

        }
        //sort
        /*Collections.sort(
                packageNameHashSet, Comparator.comparing(AppInfo::appName));*/
        //packageNameHashSet.sort(Comparator.comparing(AppInfo::getAppName));
        for(AppInfo app : packageNameHashSet)
            Log.d("LIB",app.appName);
        //TODO : sorting
        Log.d("LIB -- ",packageNameHashSet.iterator().toString());
        return packageNameHashSet;

    }
}
