package com.example.zou.novellist;

import android.util.Log;

import com.example.zou.read.NewNovelBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NovelListParse {
    String html;
    ArrayList<NovelListBean> novelListbean=new ArrayList();
    String baseurl="http://www.doulaidu.com/";
    public NovelListParse(String html){
        this.html=html;
        parse();
    }
    public void parse(){
        Document doc = Jsoup.parse(html,"http://www.doulaidu.com/");
        Element doc1 = doc.getElementById("list");
        Elements links1=doc1.select("dl");
        Elements links = links1.select("a[href]");
        for (Element link : links) {
            String linkHref=baseurl+link.attr("href");
            String linkText = link.text();
            NovelListBean novelListBean=new NovelListBean(linkHref,linkText);
            novelListbean.add(novelListBean);
            Log.d("55555","获取的属性："+linkHref);
            Log.d("55555","获取的内容："+linkText);
        }
    }
    public ArrayList<NovelListBean> getnovelListbean(){
        return novelListbean;
    }
}
