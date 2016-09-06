package com.example.zou.read;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {
    String url="http://www.doulaidu.com/dsort/7/1.html";

    ListView new_novel;
    private static Context mcontext;
    public static MainActivity mainActivity;
    TextView  textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent l=getIntent();
        url=l.getStringExtra("url");
        mainActivity=this;
        mcontext=MainActivity.this;
        new_novel= (ListView) findViewById(R.id.lv_new_novel);
        textView= (TextView) findViewById(R.id.wait_newlist_tv);
        init();
    }
    public static Context getContext(){
        return mcontext;
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    public void init() {
        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                HttpParse httpParse=new HttpParse(result);
                new_novel.setAdapter(new NewNovelAdapter(httpParse.getNewnovelbean()));
            }

            @Override
            public void dataDownloadFailed() {
            textView.setText("O豁，服务器炸了，过一会再来");
            }
        });
        httpLoad.execute(url);
    }
}
