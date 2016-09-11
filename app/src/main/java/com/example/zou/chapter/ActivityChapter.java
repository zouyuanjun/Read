package com.example.zou.chapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qiushu.chapter.QiushuParse;
import com.example.zou.novellist.NovelListActivity;
import com.example.zou.start.HttpLoad;
import com.example.zou.read.R;
import com.example.zou.start.StartActivity;
import com.example.zou.sql.Novel;

/**
 * Created by zou on 2016/7/14.
 */
public class ActivityChapter extends Activity {
    Intent intent;
    String directoryurl ="";
    String content;
    String url;
    TextView tv_content;
    Result mresult;
    String lasturl;
    String nexturl;
    Button bt_last;
    Button bt_next;
    String name;
    int chapteraccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        intent=getIntent();
        url=intent.getStringExtra("newchapterurl");
        directoryurl =intent.getStringExtra("directoryurl");
        chapteraccount=intent.getIntExtra("chapteraccount",0);
        tv_content= (TextView) findViewById(R.id.tv_content);
        bt_last= (Button) findViewById(R.id.bt_last_chapter);
        bt_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init(lasturl);
                tv_content.scrollTo(0,0);
            }
        });
        bt_next= (Button) findViewById(R.id.bt_next_chapter);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string= directoryurl +"./";
                if (string.equals(nexturl)){
                    Toast.makeText(StartActivity.getContext(),"已经到最后一章",Toast.LENGTH_LONG).show();
                }else {
                init(nexturl);
                tv_content.scrollTo(0,0);
                }
            }
        });
        init(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addfavorite) {
            Novel novel=new Novel();
            novel.setTitle(name);
            novel.setChapterurl(url);
            novel.setListurl(directoryurl);
            novel.setChapteraccount(chapteraccount);
            if (novel.save()){
                Toast.makeText(this,"收藏"+name+"成功",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"收藏"+name+"失败",Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (id==R.id.action_getlist){
            Intent intent=new Intent(ActivityChapter.this, NovelListActivity.class);
            intent.putExtra("novelurl", directoryurl);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void init(String url) {

        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
               QiushuParse chapterParse=new QiushuParse(result);

                mresult=chapterParse.getResult();
                content=chapterParse.getResult().content;
                name=chapterParse.getResult().name;
                lasturl= directoryurl +mresult.lasturl;
                nexturl= directoryurl +mresult.nexturl;
                tv_content.setText(content);
                tv_content.setMovementMethod(new ScrollingMovementMethod());
                Log.d("55555","上一章地址"+lasturl);
                Log.d("55555","下一章地址"+nexturl);
                Log.d("55555","列表地址"+ directoryurl);
            }

            @Override
            public void dataDownloadFailed() {
                tv_content.setText("O豁，这个网站又崩溃惹。。。换个网站或者时间再来吧");

            }
        });
        httpLoad.execute(url);
    }
}

