package com.zou.suibian.novel.start;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by li on 2016/9/12.
 */
public class Setting {
    public  int SOURCE=1;//1，求书网；2都来读
    public static int getSource(){
        SharedPreferences sharedPreferences=StartActivity.getContext().getSharedPreferences("setting",MODE_PRIVATE);
        int source=sharedPreferences.getInt("source",1);
        return source;
    }
    public static void setSource(int source){
        SharedPreferences sharedPreferences=StartActivity.getContext().getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("source",source);
        editor.commit();
    }

    public static void writedata(String table,String key,String data){
        SharedPreferences sharedPreferences=StartActivity.getContext().getSharedPreferences(table,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,data);
        editor.commit();
    }
    public static String getdata(String table,String key){
        SharedPreferences sharedPreferences=StartActivity.getContext().getSharedPreferences(table,MODE_PRIVATE);
        String data=sharedPreferences.getString(key,"获取失败");
        return  data;
    }

}
