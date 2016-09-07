package com.bxwx.name;

import android.util.Log;

import com.example.zou.read.NewNovelBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by zou on 2016/9/7.
 */
public class Parse {
    NewNovelBean newNovelBean;
    NameBean nameBean;
    ChapterBean chapterBean;
    ArrayList<NameBean> nameBeanArrayList=new ArrayList<>();
    ArrayList<ChapterBean> chapterBeanArrayList=new ArrayList<>();
    static ArrayList<NewNovelBean> newnovelbean=new ArrayList();
    String html;
    String url;
    String name;
    String newchaptersurl;
    String newchaptersname;
    public Parse(String html) {
        this.html = html;
        httpparse(html);
    }
    public void httpparse(String html){

        Document doc= Jsoup.parse(html);
        Element el = doc.getElementById("centerm");
        Elements els= el.getElementsByClass("odd");
        Elements links = els.select("a[href]");
        for (Element el2:links){
            url =el2.attr("href");
            name=el2.text();
            nameBean=new NameBean(url,name);
            nameBeanArrayList.add(nameBean);
        }
        Elements els2= el.getElementsByClass("even");
        Elements links2 = els2.select("a[href]");
        for (Element el2:links2){
            newchaptersurl=el2.attr("href");
            newchaptersname=el2.text();
            chapterBean=new ChapterBean(newchaptersurl,newchaptersname);
            chapterBeanArrayList.add(chapterBean);
        }
        for (int i=0;i<nameBeanArrayList.size();i++){
            String url=nameBeanArrayList.get(i).url;
            String name=nameBeanArrayList.get(i).name;
            String newchaptersurl=chapterBeanArrayList.get(i).newchaptersurl;
            String newchaptersname=chapterBeanArrayList.get(i).newchaptersname;
            newNovelBean=new NewNovelBean(url,name,newchaptersurl,newchaptersname);
            newnovelbean.add(newNovelBean);
        }

    }
    public static ArrayList<NewNovelBean> getNewnovelbean(){
        return newnovelbean;
    }
}
