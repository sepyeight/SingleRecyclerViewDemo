package com.test.singlerecyclerviewdemo;

import android.os.Environment;
import android.util.Log;

import java.io.*;

public class Config {

    public final static String FILENAME = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ConfigHook.txt";
    public final static  String TAG = "CONFIGERROR";

    public static void write2File(String packagename){
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(FILENAME)));
            bufferedWriter.write(packagename);

        } catch (IOException e) {
            Log.e(TAG, "文件写入失败");
        }finally {
            try {
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "文件写入失败");
            }
        }
    }

    public static String readFromFile(){
        File file = new File(FILENAME);
        BufferedReader bufferedReader = null;

        try {
            if(file.exists()){
                bufferedReader = new BufferedReader(new FileReader(new File(FILENAME)));
                return bufferedReader.readLine();
            }

        }catch (FileNotFoundException e) {
            Log.e(TAG, "文件没有找到");
        } catch (IOException e) {
            Log.e(TAG, "文件读取失败");
        }finally {
            try {
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
