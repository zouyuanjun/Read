package com.example.zou.start;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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
                    if (menuItemId == R.id.action_langmanyanqing) {
                        Intent intent = new Intent(StartActivity.this, NameActivity.class);
                        intent.putExtra("url","http://www.qiushu.cc/ls/24-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_fengyutongren) {
                        Intent intent = new Intent(StartActivity.this, NameActivity.class);
                        intent.putExtra("url","http://www.qiushu.cc/ls/11-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_dongfangxuanhuang) {
                        Intent intent = new Intent(StartActivity.this, NameActivity.class);
                        intent.putExtra("url","http://www.qiushu.cc/ls/12-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_xianxiaxiuzheng) {
                        Intent intent = new Intent(StartActivity.this, NameActivity.class);
                        intent.putExtra("url","http://www.qiushu.cc/ls/3-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_guoshuwuxia) {
                        Intent intent = new Intent(StartActivity.this, NameActivity.class);
                        intent.putExtra("url","http://www.qiushu.cc/ls/15-1.html");
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
