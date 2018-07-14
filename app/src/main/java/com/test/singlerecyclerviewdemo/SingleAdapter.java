package com.test.singlerecyclerviewdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter {

    private List<Appinfo> appinfoList; // 数据源
    private Context context; // 上下文

    private RecyclerView recyclerView; 
    private LayoutInflater layoutInflater; ;//布局解析器 用来解析布局生成View的
    private int selection;//保存当前选中的position

    public SingleAdapter(List<Appinfo> appinfoList, Context context, RecyclerView recyclerView) {
        this.appinfoList = appinfoList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.recyclerView = recyclerView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleViewHolder(layoutInflater.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof SingleViewHolder){
            final SingleViewHolder singleViewHolder = (SingleViewHolder) holder;
            String appname = appinfoList.get(position).getAppName();
            String packagename = appinfoList.get(position).getPackageName();
            Drawable icon = appinfoList.get(position).getIcon();
            boolean isCheck = appinfoList.get(position).isChecked();
            singleViewHolder.appName.setText(appname);
            singleViewHolder.packageName.setText(packagename);

            //统一icon大小
            Drawable newIcon = AppListUtil.zoomDrawable(icon, DisplayUtil.dip2px(context, 128), DisplayUtil.dip2px(context, 128));
            singleViewHolder.icon.setImageDrawable(newIcon);
            singleViewHolder.checkbox.setChecked(isCheck);

            singleViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingleViewHolder svh = (SingleViewHolder) recyclerView.findViewHolderForLayoutPosition(selection);

                    svh.checkbox.setSelected(false);
                    appinfoList.get(selection).setChecked(false);
                    notifyItemChanged(selection);
                    selection = position;
                    appinfoList.get(selection).setChecked(true);
                    singleViewHolder.checkbox.setSelected(true);
                    Config.write2File(appinfoList.get(selection).getPackageName());

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return appinfoList.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder{
        private TextView appName;
        private TextView packageName;
        private ImageView icon;
        private CheckBox checkbox;

        public SingleViewHolder(View itemView) {
            super(itemView);
            appName = (TextView)itemView.findViewById(R.id.appname);
            packageName = (TextView)itemView.findViewById(R.id.packagename);
            icon = (ImageView)itemView.findViewById(R.id.icon);
            checkbox = (CheckBox)itemView.findViewById(R.id.checkbox);
        }
    }
}
