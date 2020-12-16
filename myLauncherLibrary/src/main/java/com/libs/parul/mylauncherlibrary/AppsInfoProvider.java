package com.libs.parul.mylauncherlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppsInfoProvider {

    private AppsInfoProvider(){

    }

    private static class BillPughSingleton{
        private static final AppsInfoProvider INSTANCE = new AppsInfoProvider();
    }

    public static AppsInfoProvider getInstance(){
        return BillPughSingleton.INSTANCE;
    }
    
    public static List<AppInfo> getInstalledApps() {

        Context ctx = MyLibApp.getContext();
        List<AppInfo> packageNameHashSet = new ArrayList<>();
        PackageManager pm = ctx.getPackageManager();
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
            String appName = applicationInfo.loadLabel(ctx.getPackageManager()).toString();
            Drawable icon = applicationInfo.loadIcon(ctx.getPackageManager());
            String packages = applicationInfo.packageName;
            packageNameHashSet.add(new AppInfo(appName, icon, packages));

        }
        //sort
        packageNameHashSet.sort(Comparator.comparing(AppInfo::getAppName));
        Log.d("LIB -- ",packageNameHashSet.iterator().toString());
        return packageNameHashSet;

    }
}
