package com.ifsc.mylauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AppsDrawableAdapter extends RecyclerView.Adapter<AppsDrawableAdapter.ViewHolder> {

    private static Context context;
    private static List<AppInfo> appsList;
    public AppsDrawableAdapter(Context c){
        context = c;
        setUpApps();
    }

    public static void setUpApps(){
        PackageManager packageManager = context.getPackageManager();
        appsList = new ArrayList<AppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> allApps = packageManager.queryIntentActivities(i, 0);

        for(ResolveInfo ri: allApps){
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(packageManager);
            app.packageManager = ri.activityInfo.packageName;

            Log.i("Log package", app.packageManager.toString());
            app.icon = ri.activityInfo.loadIcon(packageManager);
            appsList.add(app);


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_view_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String appLabel = appsList.get(position).label.toString();
        String appPackage = appsList.get(position).label.toString();
        Drawable appIcon = appsList.get(position).icon;

        TextView textView = holder.textView;
        textView.setText(appLabel);
        ImageView imageView = holder.imageView;
        imageView.setImageDrawable(appIcon);
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;

        public ViewHolder (View itemView){
            super(itemView);

            textView = itemView.findViewById(R.id.tv_app_name);
            imageView = itemView.findViewById(R.id.app_icon);

        }
    }
}
