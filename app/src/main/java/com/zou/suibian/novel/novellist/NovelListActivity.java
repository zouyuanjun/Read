package com.zou.suibian.novel.novellist;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zou.suibian.novel.chapter.ActivityChapter;
import com.zou.suibian.novel.start.StartActivity;
import com.zou.suibian.novel.util.HttpLoad;
import com.zou.suibian.R;
import com.zou.suibian.novel.site.chapterdirectory.DldDirectorytParse;
import com.zou.suibian.novel.site.chapterdirectory.QiushuDirectoryParse;
import com.zou.suibian.novel.start.Setting;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NovelListActivity extends Activity{
    Intent intent;
    ListView listView;
    String listurl;
    String novelname;
    TextView textView;
    NovelListAdapter novelListAdapter;
    ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        listurl =intent.getStringExtra("listurl");
        novelname=intent.getStringExtra("novelname");
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
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
        Toolbar toolbar= (Toolbar) findViewById(R.id.novel_listtoolbar);
        toolbar.setTitle(novelname);
        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                switch (Setting.getSource()){
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
                novelListAdapter=new NovelListAdapter(novelListbean);
                listView.setAdapter(novelListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(StartActivity.getContext(), ActivityChapter.class);
                        intent.putExtra("chapterurl",listurl +novelListbean.get(position).chaptersurl);
                        Setting.writedata("DATA","listurl",listurl);
                        Setting.writedata("DATA","chapterurl",listurl +novelListbean.get(position).chaptersurl);
                        Setting.writedata("DATA","chaptertitle",novelListbean.get(position).chaptersname);
                        Setting.writedata("DATA","chapteraccount",Integer.toString(novelListbean.size()));
                        StartActivity.getContext().startActivity(intent);
                    }
                });
                textView.setVisibility(View.GONE);
            }
            @Override
            public void dataDownloadFailed() {
                textView.setText("O豁，服务器炸了，等等再看或者下拉刷新试试");
            }
        });
        httpLoad.execute(listurl);
    }
}
