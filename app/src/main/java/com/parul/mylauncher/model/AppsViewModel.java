package com.parul.mylauncher.model;

import android.app.Application;
import android.content.Context;

import com.libs.parul.mylauncherlibrary.AppInfo;
import com.libs.parul.mylauncherlibrary.AppsInfoProvider;

import java.util.ArrayList;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AppsViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<AppInfo>> appLiveData;
    ArrayList<AppInfo> appArrayList;
    Context appContext;


    public AppsViewModel(Application application) {
        super(application);
        appLiveData = new MutableLiveData<>();
        appContext = application.getApplicationContext();
        init();
    }

    public void init(){ //Rest API or SDK data can be fetched from here
        populateList();
        appLiveData.setValue(appArrayList);
    }

    public void populateList(){

        appArrayList = new ArrayList<>(AppsInfoProvider.getInstance(appContext).getInstalledApps());

      /*  appArrayList.add(new AppModel("com.sec.chrome"));
        appArrayList.add(new AppModel("abc"));
        appArrayList.add(new AppModel("xyz"));
        appArrayList.add(new AppModel("asd"));*/
        
    }

    public MutableLiveData<ArrayList<AppInfo>> getAppsList(){
            return appLiveData;
    }


}
