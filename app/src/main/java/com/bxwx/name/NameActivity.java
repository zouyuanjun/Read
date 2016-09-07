package com.bxwx.name;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zou.read.HttpLoad;
import com.example.zou.read.MainActivity;
import com.example.zou.read.NewNovelAdapter;
import com.example.zou.read.R;
import static com.example.zou.read.R.menu.menu_startactivity;

/**
 * Created by zou on 2016/9/7.
 */
public class NameActivity extends AppCompatActivity{
    String url="http://www.doulaidu.com/dsort/7/1.html";

    ListView new_novel;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.name_activitytoolbar);
        setSupportActionBar(toolbar);//toolbar支持
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_doushiyanqing) {
                    Intent intent = new Intent(NameActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                return true;
            }
        });
        Intent l=getIntent();
        url=l.getStringExtra("url");
        new_novel= (ListView) findViewById(R.id.lv_new_novel);
        textView= (TextView) findViewById(R.id.wait_newlist_tv);
        init();
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
            Intent intent = new Intent(NameActivity.this, com.bxwx.name.NameActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void init() {
        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                Parse parse=new Parse(result);
//               HttpParse httpParse=new HttpParse(result);
               new_novel.setAdapter(new NewNovelAdapter(Parse.getNewnovelbean()));
            }

            @Override
            public void dataDownloadFailed() {
                textView.setText("O豁，服务器炸了，过一会再来");
            }
        });
        httpLoad.execute(url);
    }
}

