package com.test.singlerecyclerviewdemo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppListUtil {

    public static String[] filter = {"com.test.registernativemethodshook", "de.robv.android.xposed.installer"};

    /**
     * 获取所有非系统应用
     * 可以把代码中的判断去掉，获取所有的APP
     */
    public static List<Appinfo> getInstallApps(PackageManager packageManager){
        List<Appinfo> appinfoList = new ArrayList<>();
        //获取手机内所有应用
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for(int i=0; i<packageInfoList.size(); i++){
            PackageInfo packageInfo = packageInfoList.get(i);
            //判断是否为非系统预装的应用程序
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)<=0){
                Appinfo appinfo = new Appinfo();
                appinfo.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                appinfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                appinfo.setPackageName(packageInfo.packageName);
                appinfoList.add(appinfo);
            }
        }
        return appinfoList;
    }

    public static List<Appinfo> initFilterApps(List<Appinfo> appinfoList){
        Iterator iterator = appinfoList.iterator();
        while (iterator.hasNext()){
            Appinfo appinfo = (Appinfo) iterator.next();
            for(int i=0; i<filter.length; i++){
                if( appinfo.getPackageName().equals(filter[i])){
                    iterator.remove();
                }
            }
        }
        return appinfoList;
    }



    public static void refreshAppList(MainActivity mainActivity){
        RecyclerView recyclerView = (RecyclerView)mainActivity.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        List<Appinfo> appinfoList = initFilterApps(getInstallApps(mainActivity.getPackageManager()));
        recyclerView.setAdapter(new SingleAdapter(appinfoList, mainActivity.getApplicationContext(), recyclerView));
    }

    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

}
