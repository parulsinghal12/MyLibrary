package com.parul.mylauncher.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.libs.parul.mylauncherlibrary.AppInfo;
import com.parul.mylauncher.R;
import com.parul.mylauncher.databinding.ActivityMainBinding;
import com.parul.mylauncher.model.AppClickListener;
import com.parul.mylauncher.model.AppsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements AppClickListener {

    ActivityMainBinding binding;
    AppsViewModel model ;
    MyAdapter myRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        model = new ViewModelProvider(this).get(AppsViewModel.class);
        myRecyclerViewAdapter = new MyAdapter(this,this);
        model.getAppsList().observe(this, new Observer<List<AppInfo>>() {
            @Override
            public void onChanged(@Nullable final List<AppInfo> appModelList) {
                // Update the cached copy of the apps in the adapter.
                ArrayList<AppInfo> appArrayList = new ArrayList<AppInfo>(appModelList);
                if(appArrayList == null || appArrayList.size()<=0){
                    //app list empty case
                }else{
                    //myRecyclerViewAdapter.notifyDataSetChanged();
                    myRecyclerViewAdapter.setData(appArrayList);
                    binding.setMyAdapter(myRecyclerViewAdapter);
                }

            }
        });



    }

    @Override
    public void onAppItemClicked(AppInfo app) { //TODO : should be moved in viewmodel.
        //launch act
        //Toast.makeText(this,app.getAppName(),LENGTH_SHORT).show();
        Intent launchIntent = app.getLauncherIntent();
        startActivity(launchIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                myRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}