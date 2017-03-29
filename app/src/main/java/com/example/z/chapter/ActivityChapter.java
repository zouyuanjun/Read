package com.example.z.chapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z.novellist.NovelListActivity;
import com.example.z.sql.Novel;
import com.example.z.util.CustomTextview;
import com.example.z.util.HttpLoad;
import com.example.z.start.StartActivity;
import com.example.zou.novel.R;
import com.example.z.start.Setting;
import com.site.chapter.DldParse;
import com.site.chapter.QiushuParse;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by zou on 2016/7/14.
 */
public class ActivityChapter extends Activity {
    Intent intent;
    String directoryurl ="";
    String content;
    String url;
    CustomTextview tv_content;
    Result mresult;
    String lasturl;
    String nexturl;
    String name;
    int chapteraccount;
    String chaptertitle;
    int source;

    String islastchapterurl;

    Layout layout;
    int lineindex=0;
    int lincount=0;
    int num;
    String re;
    String noverstring;
    int maxlinecount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chapter);

        Display mDisplay = getWindowManager().getDefaultDisplay();//获取屏幕分辨率
        int W = mDisplay.getWidth();
        final int H = mDisplay.getHeight();

        intent=getIntent();
        source=Setting.getSource();
        url=intent.getStringExtra("newchapterurl");
        directoryurl =intent.getStringExtra("directoryurl");//获取目录url
        chapteraccount=intent.getIntExtra("chapteraccount",0);//获取章节URL
        source=intent.getIntExtra("source",1);
        tv_content= (CustomTextview) findViewById(R.id.tv_content);
        tv_content.setOndownlistener(new CustomTextview.OndownActionListener() {
            @Override
            public void OnDown(float x, float y) {
                if (y>H*2/3){
                    nextpag();
                }else if (y<H/3){
                    init(lasturl);
                    tv_content.scrollTo(0,0);
                }else {

                }
            }
        });


        ViewTreeObserver observer = tv_content.getViewTreeObserver(); // textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = tv_content.getViewTreeObserver();

                float viewheight=tv_content.getHeight();
                float linheight=tv_content.getLineHeight();
                lincount = (int) (viewheight / linheight);//获取每页有多少行
                layout=tv_content.getLayout();
                re=tv_content.getText().toString();

                maxlinecount=tv_content.getLineCount();
                Log.d("7777","一页可加载行"+lincount+"总行数"+maxlinecount);
                if (maxlinecount>=lincount){
                    num=layout.getLineEnd(lincount-1);
                }else num=layout.getLineEnd(maxlinecount-1);

                Log.d("7777","初始字符位置："+num);

            }
        });


        init(url);
    }
    public void nextpag(){
        //判断总行数是否小于当前页最大行数，如果是则转下一章
        if (lincount< maxlinecount) {
            lineindex = lineindex + lincount;
            Log.d("5555","行指针"+lincount+"最大行数"+maxlinecount);
            re = re.substring(num, re.length());
            num = layout.getLineEnd(lincount);
            Log.d("7777", "行数" + lineindex + "字符位置：" + num);
            tv_content.setText(re);
        } else {
            switch (source) {
                case 1: {
                    islastchapterurl = directoryurl + "./";
                    break;
                }
                case 2: {
                    islastchapterurl = directoryurl;
                    break;
                }
            }

            if (islastchapterurl.equals(nexturl)) {
                Toast.makeText(StartActivity.getContext(), "已经到最后一章", Toast.LENGTH_LONG).show();
            } else {
                init(nexturl);
                tv_content.scrollTo(0, 0);
            }
        }
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
            novel.setChaptertitle(chaptertitle);
            novel.setSource(source);
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
    public void init(final String url) {

        HttpLoad httpLoad = new HttpLoad();
        httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
            @Override
            public void dataDownloadSuccessfully(String result) {
                switch (source) {
                    case 1: {
                        Log.d("6666","解析代码为1，求书网");
                        QiushuParse chapterParse=new QiushuParse(result);
                        mresult=chapterParse.getResult();
                        break;
                    }
                    case 2: {
                        Log.d("6666","解析代码为2，都来读");
                        DldParse dldParse=new DldParse(result);
                        mresult=dldParse.getResult();
                        break;
                    }
                }

                content= mresult.content;
                name= mresult.name;
                lasturl= directoryurl +mresult.lasturl;
                nexturl= directoryurl +mresult.nexturl;
                chaptertitle=mresult.chaptertitle;
                tv_content.setText(Html.fromHtml(content));
                tv_content.setTitle(chaptertitle);
                tv_content.setMovementMethod(new ScrollingMovementMethod());
                Novel updatanovel=new Novel();
                updatanovel.setChapterurl(url);
                updatanovel.setChapteraccount(chapteraccount);
                updatanovel.setChaptertitle(chaptertitle);
                updatanovel.updateAll("title=?",name);
                Log.d("55555","上一章地址"+lasturl);
                Log.d("55555","下一章地址"+nexturl);
                Log.d("55555","列表地址"+ directoryurl);
            }

            @Override
            public void dataDownloadFailed() {
                tv_content.setText("O豁，这个网站又崩溃惹。。。换个网站或者重进一下试试");

            }
        });
        httpLoad.execute(url);
    }

}

