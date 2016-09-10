package com.example.zou.start;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.zou.chapter.ActivityChapter;
import com.example.zou.read.NameActivity;
import com.example.zou.read.R;
import com.example.zou.sql.Novel;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by zou on 2016/7/15.
 */
public class StartActivity extends AppCompatActivity{
    public static Context getContext() {
        return context;
    }

    public static Context context;
    public static StartActivity startActivity;
    private SharedPreferences pref;
    Button last_read;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//toolbar支持
        if (toolbar != null) {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int menuItemId = item.getItemId();
                    if (menuItemId == R.id.action_doushiyanqing) {
                        Intent intent = new Intent(StartActivity.this, NameActivity.class);
                        intent.putExtra("url","http://www.qiushu.cc/ls/4-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_wangjie) {
                        Intent intent = new Intent(StartActivity.this, NameActivity.class);
                        intent.putExtra("url","http://www.bxwx8.org/modules/article/index.php?fullflag=1");
                        startActivity(intent);
                        return true;
                    }
                    return true;
                }
            });
        }
        context=this;
        startActivity=this;
        SQLiteDatabase db= Connector.getDatabase();
        List<Novel> favoritenovellist =DataSupport.findAll(Novel.class);
        listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(new FavoriteListAdapter(favoritenovellist,listView));
        last_read= (Button) findViewById(R.id.bt_lastread);
        last_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref=getSharedPreferences("lastread",MODE_PRIVATE);
                    String chapterurl=pref.getString("chapter","0");
                String baseurl=pref.getString("baseurl","0");
                    Log.d("5555",chapterurl);
                if (chapterurl.equals("0")){
                    Intent intent=new Intent(StartActivity.this,NameActivity.class);
                    intent.putExtra("url","http://www.qiushu.cc/ls/4-1.html");
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

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_startactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_doushiyanqing) {
            Intent intent = new Intent(StartActivity.this, NameActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
