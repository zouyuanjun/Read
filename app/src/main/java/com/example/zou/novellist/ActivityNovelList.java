package com.example.zou.novellist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.bxwx.chapterdirectory.DirectoryParse;
import com.example.zou.read.HttpLoad;
import com.example.zou.read.R;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class ActivityNovelList extends Activity{
    Intent intent;
    ListView listView;
    String baseurl="http://www.bxwx8.org";
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
                DirectoryParse directoryParse=new DirectoryParse(result);
//                NovelListParse novelListParse=new NovelListParse(result);
//                ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();
//                novelListbean=novelListParse.getnovelListbean();
//                listView.setAdapter(new NovelListAdapter(novelListbean,url));
                Log.d("555557",result);
            }
            @Override
            public void dataDownloadFailed() {
                textView.setText("O豁，服务器炸了，等等再看");
            }
        });
        Log.d("66666","即将开始联网");
        httpLoad.execute(baseurl+url);
    }
}
