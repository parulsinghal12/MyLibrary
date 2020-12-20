package com.parul.mylauncher.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.libs.parul.mylauncherlibrary.AppInfo;
import com.parul.mylauncher.R;
import com.parul.mylauncher.databinding.RowItemBinding;
import com.parul.mylauncher.model.AppClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  implements Filterable {
    private List<AppInfo> dataModelList;
    private List<AppInfo> dataModelListCopy;
    private Context context;
    private AppClickListener clickListener;

    public MyAdapter(Context ctx,AppClickListener listener ) {
        context =  ctx;
        clickListener = listener;
    }

    public void setData(List<AppInfo> appsList){
        dataModelList = appsList;
        dataModelListCopy = new ArrayList<>(dataModelList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AppInfo dataModel = dataModelList.get(position);
        holder.bind(dataModel,clickListener);

    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<AppInfo> filteredList = new ArrayList<>();
                if(TextUtils.isEmpty(charSequence))
                    filteredList = (ArrayList<AppInfo>) dataModelListCopy;
                else{
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (AppInfo app: dataModelListCopy
                         ) {
                        if(app.getAppName().toLowerCase().contains(filterPattern)){
                            filteredList.add(app);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataModelList.clear();
                dataModelList.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public RowItemBinding binding;
        public ViewHolder(@NonNull RowItemBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.binding = itemViewBinding;

        }

        public void bind(AppInfo dataModel, AppClickListener clickListener) {
            binding.setDataModel(dataModel);
            binding.setItemClickListener(clickListener);
            binding.executePendingBindings();

        }
    }

}
