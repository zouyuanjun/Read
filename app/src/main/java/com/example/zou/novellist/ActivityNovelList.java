package com.example.zou.novellist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.zou.read.HttpLoad;
import com.example.zou.read.NewNovelAdapter;
import com.example.zou.read.R;

/**
 * Created by zou on 2016/7/14.
 */
public class ActivityNovelList extends Activity{
    Intent intent;
    ListView listView;
    String url="http://www.doulaidu.com//xs/65861";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        url=intent.getStringExtra("novelurl");
        setContentView(R.layout.activity_novel_list);
        listView= (ListView) findViewById(R.id.lv_novel_list);
        init();
    }
    public void init() {
        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                NovelListParse novelListParse=new NovelListParse(result);

                  listView.setAdapter(new NovelListAdapter(novelListParse.getnovelListbean(),url));
                Log.d("55555",result);
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        httpLoad.execute(url);
    }
}
