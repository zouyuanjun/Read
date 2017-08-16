package com.example.z.start;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z.chapter.ActivityChapter;
import com.example.z.novel.NameActivity;
import com.example.z.novellist.ChapterDirectoryBean;
import com.example.z.sql.Novel;
import com.example.z.util.MyItemDecoration;
import com.example.z.util.Myservice;
import com.example.zou.novel.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.site.chapterdirectory.QiushuDirectoryParse;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zou on 2016/7/15.
 */
public class StartActivity extends AppCompatActivity {
    public static Context getContext() {
        return context;
    }
    public static Context context;
    public static StartActivity startActivity;
    Toolbar toolbar;
    public  String  url;
    Fresco fresco;
    TextView tv_qiushuwang;
    TextView tv_doulaidu;
    TextView tv_about;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    List<Novel> favoritenovellist;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private Myservice.MyBinder myBinder;
    ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        startActivity=this;
        Fresco.initialize(context);
        setContentView(R.layout.start_activity);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.dl_left);
        if (Setting.getSource()==1){
            toolbar.setTitle("求书网");
        }
        if (Setting.getSource()==2){
            toolbar.setTitle("都来读");
        }
        setSupportActionBar(toolbar);//toolbar支持

        if (toolbar != null) {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int menuItemId = item.getItemId();
                                     switch (menuItemId){
                            case R.id.action_doushiyanqing:{
                                if (Setting.getSource()==1){
                                    url="http://www.qiushu.cc/ls/4-1.html";
                                }
                                else url="http://www.doulaidu.com/dsort/3/1.html";
                                break;
                            }
                            case R.id.action_langmanyanqing:{
                                if (Setting.getSource()==1){
                                    url="http://www.qiushu.cc/ls/24-1.html";
                                }
                                else url="http://www.doulaidu.com/dsort/7/1.html";
                                break;
                            }

                            case R.id.action_dongfangxuanhuang:{
                                if (Setting.getSource()==1) {
                                    url = "http://www.qiushu.cc/ls/12-1.html";
                                }
                                else url="http://www.doulaidu.com/dsort/1/1.html";
                                break;
                            }
                            case R.id.action_xianxiaxiuzheng:{
                                if (Setting.getSource()==1) {
                                    url = "http://www.qiushu.cc/ls/3-1.html";
                                }
                                else url="http://www.doulaidu.com/dsort/2/1.html";
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

                mDrawerLayout.setClickable(true);
                return;
            }
            @Override
            public void onDrawerClosed(View mDrawerLayout) {
                super.onDrawerClosed(mDrawerLayout);
                invalidateOptionsMenu();
                    return;
            }
        };
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听

        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
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

        tv_qiushuwang= (TextView) findViewById(R.id.dl_tv_qiushuwang);
        recyclerView=(RecyclerView) findViewById(R.id.start_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.addItemDecoration(new MyItemDecoration());
        tv_qiushuwang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle(tv_qiushuwang.getText());
                mDrawerLayout.closeDrawers();
                Setting.setSource(1);
                Toast.makeText(StartActivity.getContext(),"选择源：求书网",Toast.LENGTH_SHORT).show();
                }
        });
        tv_doulaidu= (TextView) findViewById(R.id.dl_tv_doulaidu);
        tv_doulaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle(tv_doulaidu.getText());
                mDrawerLayout.closeDrawers();
                Setting.setSource(2);
                Toast.makeText(StartActivity.getContext(),"选择源：都来读",Toast.LENGTH_SHORT).show();
            }
        });
        tv_about= (TextView) findViewById(R.id.dl_tv_about);
        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,About.class);
                startActivity(intent);
            }
        });
        favoriteAdapter=new FavoriteAdapter(favoritenovellist);
        recyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.setOnItemClickListener(new FavoriteAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent=new Intent(StartActivity.this, ActivityChapter.class);
                intent.putExtra("chapterurl",data);
                startActivity(intent);
            }
        });
       // init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qiushuwang, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        ServiceConnection connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder= (Myservice.MyBinder)service;
                String data=myBinder.httpconnet(favoritenovellist.get(0).getListurl());
                QiushuDirectoryParse directoryParse=new QiushuDirectoryParse(data);
                novelListbean=directoryParse.getNovelListbean();
                int account=novelListbean.size();
                Log.d("5555","sdfs"+account);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent bindIntent=new Intent(this, Myservice.class);
        bindService(bindIntent,connection, BIND_AUTO_CREATE);
    }

}
