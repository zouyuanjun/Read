package com.example.zou.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zou.start.HttpLoad;
import com.example.zou.start.Setting;
import com.example.zou.start.StartActivity;
import com.site.name.DldnameParse;
import com.site.name.QiushunameParse;

import java.io.UnsupportedEncodingException;

/**
 * Created by zou on 2016/9/7.
 */
public class NameActivity extends AppCompatActivity {
    String url;

    ListView new_novel_list;
    TextView textView;
    String siteurl;
    SwipeRefreshLayout swipeRefreshLayout;
    NewNovelAdapter newNovelAdapter;
    private  View loadmoreView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent l = getIntent();
        url = l.getStringExtra("url");
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.sr_layout);
        swipeRefreshLayout.setColorSchemeColors(R.color.gray,R.color.darkgoldenrod,R.color.colorPrimary,R.color.darkgrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init(url);
            }
        });
        init(url);
        loadmoreView= LayoutInflater.from(this).inflate(R.layout.loadmore,null);
        //解决listview和swipeRefreshLayout的滑动冲突问题，根据list view是否在第一行判定是否下拉刷新
        new_novel_list.addFooterView(loadmoreView);
        new_novel_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int visibleLastIndex = 0;   //最后的可视项索引
            public int visibleItemCount;       // 当前窗口可见项总数
            int index=1;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int itemsLastIndex =  newNovelAdapter.getCount() - 1;    //数据集最后一项的索引
                int lastIndex = itemsLastIndex + 1;
                String lasturl=url;//加上底部的loadMoreView项
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
                    //如果是自动加载,可以在这里放置异步加载数据的代码
                    if (Setting.SOURCE == 1) {
                        int id = url.indexOf("-");

                        int length = url.length();
                        lasturl = url.substring(0, id + 1) + String.valueOf(index + 1) + url.substring(id + 2, length);
                        index++;
                        init(lasturl);
                        Log.d("555555", "loading..." + lasturl);
                    } else {
                        Toast.makeText(StartActivity.getContext(), "没有更多啦", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
                this.visibleItemCount = visibleItemCount;
                visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        url = intent.getStringExtra("url");
        init(url);

        swipeRefreshLayout.setColorSchemeColors(R.color.gray,R.color.darkgoldenrod,R.color.colorPrimary,R.color.darkgrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init(url);
            }
        });
        super.onNewIntent(intent);
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qiushuwang, menu);
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
            Intent intent = new Intent(NameActivity.this, NameActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init(String url) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.name_activitytoolbar);
        if (Setting.SOURCE==1){
            toolbar.setTitle("求书网");
        }
        if (Setting.SOURCE==2){
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
                            if (Setting.SOURCE==1){
                                siteurl="http://www.qiushu.cc/ls/4-1.html";
                            }
                            else siteurl="http://www.doulaidu.com/dsort/3/1.html";
                            break;
                        }
                        case R.id.action_langmanyanqing:{
                            if (Setting.SOURCE==1){
                                siteurl="http://www.qiushu.cc/ls/24-1.html";
                            }
                            else siteurl="http://www.doulaidu.com/dsort/7/1.html";
                            break;
                        }

                        case R.id.action_dongfangxuanhuang:{
                            if (Setting.SOURCE==1) {
                                siteurl = "http://www.qiushu.cc/ls/12-1.html";
                            }
                            else siteurl="http://www.doulaidu.com/dsort/1/1.html";
                            break;
                        }
                        case R.id.action_xianxiaxiuzheng:{
                            if (Setting.SOURCE==1) {
                                siteurl = "http://www.qiushu.cc/ls/3-1.html";
                            }
                            else siteurl="http://www.doulaidu.com/dsort/2/1.html";
                            break;
                        }

                    }
                    Intent intent = new Intent(NameActivity.this, NameActivity.class);
                    intent.putExtra("url", siteurl);
                    startActivity(intent);
                    return true;
                }
            });

        }
        new_novel_list = (ListView) findViewById(R.id.lv_new_novel);
        textView = (TextView) findViewById(R.id.wait_newlist_tv);

        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) throws UnsupportedEncodingException {
                switch (Setting.SOURCE) {
                    case 1: {
                        Log.d("6666","解析代码为1");
                        QiushunameParse parse = new QiushunameParse(result);
                        newNovelAdapter = new NewNovelAdapter(parse.getNewnovelbean());
                        break;
                    }
                    case 2: {
                        Log.d("6666","解析代码为2");
                        DldnameParse dldnameParse = new DldnameParse(result);
                        newNovelAdapter = new NewNovelAdapter(dldnameParse.getNewnovelbean());
                        break;
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                new_novel_list.setAdapter(newNovelAdapter);
                newNovelAdapter.notifyDataSetChanged();
                textView.setVisibility(View.GONE);
            }

            @Override
            public void dataDownloadFailed() {
                textView.setText("O豁，服务器炸了，过一会再来或者下拉刷新试试");
            }
        });
        httpLoad.execute(url);
    }
}

