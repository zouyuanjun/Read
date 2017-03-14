package com.site.name;

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
public class QiushunameParse {
    NewNovelBean newNovelBean;
    NameBean nameBean;
    ChapterBean chapterBean;
    ArrayList<NameBean> nameBeanArrayList = new ArrayList<>();
    ArrayList<ChapterBean> chapterBeanArrayList = new ArrayList<>();
    ArrayList<NewNovelBean> newnovelbean = new ArrayList();
    ArrayList<String> picurlarraylist=new ArrayList<>();
    ArrayList<String> introarraylist=new ArrayList<>();
    String html;
    String url;
    String name;
    String newchaptersurl;
    String newchaptersname;
    String picurl;
    String intro;

    public QiushunameParse(String html) {
        this.html = html;
        httpparse(html);
    }

    public void httpparse(String html)throws NullPointerException {

        Document doc = Jsoup.parse(html);
        Elements els2 = null;
        Elements links = null;
        Elements elpicurl=null;
        Elements elintro=null;
        try {
            Element element = doc.getElementById("main");
            Elements els = element.getElementsByClass("t1");
            els2 = element.getElementsByClass("t4");
            elpicurl=element.getElementsByClass("pic");
            elintro=element.getElementsByClass("t3");
            links = els.select("a");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //提取小说名信息
        for (Element el2 : links) {
            url = el2.attr("href");
            name = el2.text();
            name = name.substring(1, name.length() - 5);
            nameBean = new NameBean(url, name);
            nameBeanArrayList.add(nameBean);
        }
        Elements links2 = els2.select("a");//提取章节信息
        for (Element el2 : links2) {
            newchaptersurl = el2.attr("href");
            newchaptersname = el2.text();
            chapterBean = new ChapterBean(newchaptersurl, newchaptersname);
            chapterBeanArrayList.add(chapterBean);
        }
        //提取图片URL
        Elements piclink=elpicurl.select("img");
        for (Element element:piclink){
            picurl=element.attr("src");
            Log.d("6666","图片URL"+picurl);
            picurlarraylist.add(picurl);
        }
        //提取简介
        for (Element element:elintro){
            intro=element.text();
            introarraylist.add(intro);
        }
        for (int i = 0; i < nameBeanArrayList.size(); i++) {
            String url = nameBeanArrayList.get(i).url;
            String name = nameBeanArrayList.get(i).name;
            String newchaptersurl = "www.qiushu.cc/" + chapterBeanArrayList.get(i).newchaptersurl;
            String newchaptersname = chapterBeanArrayList.get(i).newchaptersname;
            String picurl=picurlarraylist.get(i);
            String intro=introarraylist.get(i);
            newNovelBean = new NewNovelBean(url, name, newchaptersurl, newchaptersname,picurl,intro);
            newnovelbean.add(newNovelBean);
        }
        nameBeanArrayList.clear();
        chapterBeanArrayList.clear();
    }

    public  ArrayList<NewNovelBean> getNewnovelbean() {

        return newnovelbean;
    }

    /**
     * Created by zou on 2016/9/8.
     */
    public static class ChapterBean {
        String newchaptersurl;
        String newchaptersname;

        public ChapterBean(String newchaptersurl, String newchaptersname) {
            this.newchaptersurl = newchaptersurl;
            this.newchaptersname = newchaptersname;
        }
    }

    /**
     * Created by zou on 2016/9/8.
     */
    public static class NameBean {
        String url;
        String name;

        public NameBean(String url, String name) {
            this.url = url;
            this.name = name;
        }
    }
}
