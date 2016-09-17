package com.example.zou.start;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    Toolbar toolbar;
    public  String  url;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    List<Novel> favoritenovellist;
    FavoriteListAdapter favoriteListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.dl_left);
        setSupportActionBar(toolbar);//toolbar支持

        if (toolbar != null) {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int menuItemId = item.getItemId();
                    switch (menuItemId){
                        case R.id.action_doushiyanqing:{
                            url="http://www.qiushu.cc/ls/4-1.html";
                            break;
                        }
                        case R.id.action_langmanyanqing:{
                            url="http://www.qiushu.cc/ls/24-1.html";
                            break;
                        }
                        case R.id.action_fengyutongren:{
                            url="http://www.qiushu.cc/ls/11-1.html";
                            break;
                        }
                        case R.id.action_dongfangxuanhuang:{
                            url="http://www.qiushu.cc/ls/12-1.html";
                            break;
                        }
                        case R.id.action_xianxiaxiuzheng:{
                            url="http://www.qiushu.cc/ls/3-1.html";
                            break;
                        }
                        case R.id.action_guoshuwuxia:{
                            url="http://www.doulaidu.com/dsort/3/1.html";
                            break;
                        }

                    }
                    Intent intent = new Intent(StartActivity.this, NameActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                    return true;
                }
            });
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.close,R.string.app_name) {
            @Override
            public void onDrawerOpened(View mDrawerLayout) {
                super.onDrawerOpened(mDrawerLayout);
                invalidateOptionsMenu();
                return;
            }
            @Override
            public void onDrawerClosed(View mDrawerLayout) {
                super.onDrawerClosed(mDrawerLayout);
                    return;
            }
        };
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        context=this;
        startActivity=this;
     //   init();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
     return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db= Connector.getDatabase();
        favoritenovellist =DataSupport.findAll(Novel.class);
        listView= (ListView) findViewById(R.id.listview);
        favoriteListAdapter=new FavoriteListAdapter(favoritenovellist,listView);
        listView.setAdapter(favoriteListAdapter);

        // init();
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

    public void init(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String name=favoritenovellist.get(position).getTitle();
                new AlertDialog.Builder(StartActivity.this).setPositiveButton("删除？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Novel.class,"title=?",name);
                        favoriteListAdapter.notifyDataSetChanged();
                    }
                }).show();
                return false;
            }
        });

    }
}
