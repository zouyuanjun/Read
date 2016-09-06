package com.example.zou.read;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.zou.chapter.ActivityChapter;
import com.example.zou.sql.FavoriteBean;
import com.example.zou.sql.FavoriteDatabaseHelper;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/15.
 */
public class StartActivity extends Activity{
    public static Context getContext() {
        return context;
    }

    public static Context context;
    private SharedPreferences pref;
    Button last_read;
    Button newlist;
    Button grilnovel;
    FavoriteDatabaseHelper favoriteDatabaseHelper;
    ListView listView;
    ArrayList<FavoriteBean> favoriteBeanArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        context=this;
        favoriteDatabaseHelper=new FavoriteDatabaseHelper(this);
        favoriteBeanArrayList=favoriteDatabaseHelper.getAllContacts();
        listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(new GridViewAdapter(favoriteBeanArrayList,listView));
        last_read= (Button) findViewById(R.id.bt_lastread);
        last_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref=getSharedPreferences("lastread",MODE_PRIVATE);
                    String chapterurl=pref.getString("chapter","0");
                String baseurl=pref.getString("baseurl","0");
                    Log.d("5555",chapterurl);
                if (chapterurl.equals("0")){
                    Intent intent=new Intent(StartActivity.this,MainActivity.class);
                    intent.putExtra("url","http://www.doulaidu.com/dsort/3/1.html");
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(StartActivity.this, ActivityChapter.class);
                    intent.putExtra("newchapterurl", chapterurl);
                    intent.putExtra("baseurl",baseurl);
                    startActivity(intent);
                    finish();
                }
            }
        });
        newlist= (Button) findViewById(R.id.bt_newlist);
        newlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("url","http://www.doulaidu.com/dsort/3/1.html");
                startActivity(intent);
            }
        });
        grilnovel= (Button) findViewById(R.id.bt_grilnovel);
        grilnovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("url","http://www.doulaidu.com/dsort/7/1.html");
                startActivity(intent);
            }
        });
    }
}
