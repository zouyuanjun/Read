package com.zou.suibian.novel.chapter;

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
import android.widget.Toast;

import com.zou.suibian.novel.novellist.NovelListActivity;
import com.zou.suibian.novel.sql.Novel;
import com.zou.suibian.novel.util.CustomTextview;
import com.zou.suibian.novel.util.HttpLoad;
import com.zou.suibian.novel.start.StartActivity;
import com.zou.suibian.novel.util.Popwindow;
import com.zou.suibian.R;
import com.zou.suibian.novel.start.Setting;
import com.zou.suibian.novel.site.chapter.DldParse;
import com.zou.suibian.novel.site.chapter.QiushuParse;

import android.view.Gravity;
import android.view.View.OnClickListener;

/**
 * Created by zou on 2016/7/14.
 */
public class ActivityChapter extends Activity {
    Intent intent;
    String content;
    CustomTextview tv_content;
    Result mresult;
    String lasturl;
    String nexturl;

    private String picurl;
    private String novelname;
    private String listurl;
    private String chapterurl;
    private int chapteraccount;
    private String chaptertitle;
    private int source;

    String islastchapterurl;

    Layout layout;
    int lineindex=0;
    int lincount=0;
    int num;
    String re;
    int maxlinecount;
    Popwindow popwindow;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chapter);
        activity=this;
        Display mDisplay = getWindowManager().getDefaultDisplay();//获取屏幕分辨率
        final int H = mDisplay.getHeight();

        intent=getIntent();
        source=Setting.getSource();
        chapterurl =intent.getStringExtra("chapterurl");

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
                    popwindow=new Popwindow(activity,itemsOnClick);
                    popwindow.showAtLocation(ActivityChapter.this.findViewById(R.id.chapteractivity),
                            Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
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
        init(chapterurl);
    }
    //点击之后翻下一页
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
                    islastchapterurl = listurl + "./";
                    break;
                }
                case 2: {
                    islastchapterurl = listurl;
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
    public void lastpag(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_addfavorite) {
            return true;
        }
        if (id == R.id.action_getlist) {

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
                picurl=Setting.getdata("DATA","picurl");
                novelname = mresult.name;
                listurl=Setting.getdata("DATA","listurl");
                chapterurl=Setting.getdata("DATA","chapterurl");
                chapteraccount=Integer.parseInt(Setting.getdata("DATA","chapteraccount"));
                chaptertitle=mresult.chaptertitle;
                content= mresult.content;
                lasturl= listurl +mresult.lasturl;
                nexturl= listurl +mresult.nexturl;
                tv_content.setText(Html.fromHtml(content));
                tv_content.setTitle(chaptertitle);
                tv_content.setMovementMethod(new ScrollingMovementMethod());
                Novel updatanovel=new Novel();
                updatanovel.setChapterurl(url);
                updatanovel.setChapteraccount(chapteraccount);
                updatanovel.setChaptertitle(chaptertitle);
                updatanovel.updateAll("novelname=?", novelname);
                Log.d("55555","上一章地址"+lasturl);
                Log.d("55555","下一章地址"+nexturl);
                Log.d("55555","列表地址"+  listurl);
            }

            @Override
            public void dataDownloadFailed() {
                tv_content.setText("O豁，这个网站又崩溃惹。。。换个网站或者重进一下试试");

            }
        });
        httpLoad.execute(url);
    }
    //为弹出窗口实现监听类
    private OnClickListener  itemsOnClick = new OnClickListener(){
        public void onClick(View v) {
            popwindow.dismiss();
            switch (v.getId()) {
                case R.id.tv_collect:
                    Novel novel = new Novel();
                    novel.setPicurl(picurl);
                    novel.setNovelname(novelname);
                    novel.setChapterurl(chapterurl);
                    novel.setListurl(listurl);
                    novel.setChapteraccount(chapteraccount);
                    novel.setChaptertitle(chaptertitle);
                    novel.setSource(source);
                    if (novel.save()) {
                        Toast.makeText(activity, "收藏" + novelname + "成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity, "收藏" + novelname + "失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.tv_backlist:
                    Intent intent = new Intent(ActivityChapter.this, NovelListActivity.class);
                    intent.putExtra("listurl", listurl);
                    intent.putExtra("navelname",novelname);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
}

