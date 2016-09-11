package com.example.zou.novellist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.qiushu.chapterdirectory.QiushuDirectoryParse;
import com.example.zou.start.HttpLoad;
import com.example.zou.read.R;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NovelListActivity extends Activity{
    Intent intent;
    ListView listView;
    String url;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        url=intent.getStringExtra("novelurl");
        setContentView(R.layout.activity_novel_list);
        listView= (ListView) findViewById(R.id.lv_novel_list);
        textView= (TextView) findViewById(R.id.wait_list_tv);
        init();
    }
    public void init() {
        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                QiushuDirectoryParse directoryParse=new QiushuDirectoryParse(result);
                ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();
                novelListbean=directoryParse.getNovelListbean();
                listView.setAdapter(new NovelListAdapter(novelListbean,url));
                textView.setVisibility(View.GONE);
            }
            @Override
            public void dataDownloadFailed() {
                textView.setText("O豁，服务器炸了，等等再看");
            }
        });
        Log.d("66666","即将开始联网");
        httpLoad.execute(url);
    }
}
