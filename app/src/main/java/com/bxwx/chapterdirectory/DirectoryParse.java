package com.bxwx.chapterdirectory;

import android.util.Log;

import com.example.zou.novellist.ChapterDirectoryBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by li on 2016/9/8.
 */
public class DirectoryParse {
    String html;
    ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();

    public DirectoryParse(String html) {
        this.html = html;
    }
    public void Parse(){
        Document doc = Jsoup.parse(html);
        Elements element=doc.getElementsByAttributeValue("src","/images/dian.gif");
        String taburl=element.attr("href");
        Log.d("666666","目录网址为："+taburl);
    }
}
