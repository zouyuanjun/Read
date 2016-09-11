package com.example.zou.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zou.start.HttpLoad;
import com.example.zou.start.StartActivity;
import com.qiushu.name.QiushunameParse;

/**
 * Created by zou on 2016/9/7.
 */
public class NameActivity extends AppCompatActivity{
    String url;

    ListView new_novel_list;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent l=getIntent();
        url=l.getStringExtra("url");
        init(url);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        url=intent.getStringExtra("url");
        init(url);
        super.onNewIntent(intent);
    }

    //
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
            Intent intent = new Intent(NameActivity.this, NameActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void init(String url) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.name_activitytoolbar);
        setSupportActionBar(toolbar);//toolbar支持
        if (toolbar != null) {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int menuItemId = item.getItemId();
                    if (menuItemId == R.id.action_doushiyanqing) {
                        Intent intent = new Intent(NameActivity.this, NameActivity.class);
                        intent.putExtra("url", "http://www.qiushu.cc/ls/4-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_langmanyanqing) {
                        Intent intent = new Intent(NameActivity.this, NameActivity.class);
                        intent.putExtra("url", "http://www.qiushu.cc/ls/24-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_fengyutongren) {
                        Intent intent = new Intent(NameActivity.this, NameActivity.class);
                        intent.putExtra("url", "http://www.qiushu.cc/ls/11-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_dongfangxuanhuang) {
                        Intent intent = new Intent(NameActivity.this, NameActivity.class);
                        intent.putExtra("url", "http://www.qiushu.cc/ls/12-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_xianxiaxiuzheng) {
                        Intent intent = new Intent(NameActivity.this, NameActivity.class);
                        intent.putExtra("url", "http://www.qiushu.cc/ls/3-1.html");
                        startActivity(intent);
                        return true;
                    }
                    if (menuItemId == R.id.action_guoshuwuxia) {
                        Intent intent = new Intent(NameActivity.this, NameActivity.class);
                        intent.putExtra("url", "http://www.qiushu.cc/ls/15-1.html");
                        startActivity(intent);
                        return true;
                    }
                    return true;
                }
            });
        }
        new_novel_list = (ListView) findViewById(R.id.lv_new_novel);
        textView= (TextView) findViewById(R.id.wait_newlist_tv);

        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                QiushunameParse parse=new QiushunameParse(result);
                NewNovelAdapter newNovelAdapter=new NewNovelAdapter(parse.getNewnovelbean());
                new_novel_list.setAdapter(newNovelAdapter);
                newNovelAdapter.notifyDataSetChanged();
                textView.setVisibility(View.GONE);
            }

            @Override
            public void dataDownloadFailed() {
                textView.setText("O豁，服务器炸了，过一会再来");
            }
        });
        httpLoad.execute(url);
    }
}

