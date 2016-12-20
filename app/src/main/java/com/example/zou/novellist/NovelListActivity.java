package com.example.zou.novellist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zou.read.R;
import com.example.zou.start.HttpLoad;
import com.example.zou.start.Setting;
import com.site.chapterdirectory.DldDirectorytParse;
import com.site.chapterdirectory.QiushuDirectoryParse;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NovelListActivity extends Activity{
    Intent intent;
    ListView listView;
    String url;
    TextView textView;
    NovelListAdapter novelListAdapter;
    ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        url=intent.getStringExtra("novelurl");
        setContentView(R.layout.activity_novel_list);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.direct_sr_layout);
        swipeRefreshLayout.setColorSchemeColors(R.color.gray,R.color.darkgoldenrod,R.color.colorPrimary,R.color.darkgrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        listView= (ListView) findViewById(R.id.lv_novel_list);
        textView= (TextView) findViewById(R.id.wait_list_tv);
        init();

        //解决listview和swipeRefreshLayout的滑动冲突问题，根据list view是否在第一行判定是否下拉刷新
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
            }
        });
    }
    public void init() {
        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                switch (Setting.SOURCE){
                    case 1:  {
                        QiushuDirectoryParse directoryParse=new QiushuDirectoryParse(result);
                        novelListbean=directoryParse.getNovelListbean();
                        break;
                    }
                    case 2:{
                        DldDirectorytParse dldDirectorytParse=new DldDirectorytParse(result);
                        novelListbean=dldDirectorytParse.getnovelListbean();
                        break;
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                novelListAdapter=new NovelListAdapter(novelListbean,url);
                listView.setAdapter(novelListAdapter);
                textView.setVisibility(View.GONE);
            }
            @Override
            public void dataDownloadFailed() {
                textView.setText("O豁，服务器炸了，等等再看或者下拉刷新试试");
            }
        });
        Log.d("66666","即将开始联网");
        httpLoad.execute(url);
    }
}
