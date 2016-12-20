package com.site.name;

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
    String html;
    String url;
    String name;
    String newchaptersurl;
    String newchaptersname;

    public QiushunameParse(String html) {
        this.html = html;
        httpparse(html);
    }

    public void httpparse(String html)throws NullPointerException {

        Document doc = Jsoup.parse(html);
        Elements els2 = null;
        Elements links = null;
        try {
            Element element = doc.getElementById("main");
            Elements els = element.getElementsByClass("t1");
            els2 = element.getElementsByClass("t4");
            links = els.select("a");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (Element el2 : links) {
            url = el2.attr("href");
            name = el2.text();
            name = name.substring(1, name.length() - 5);
            nameBean = new NameBean(url, name);
            nameBeanArrayList.add(nameBean);
        }
        Elements links2 = els2.select("a");
        for (Element el2 : links2) {
            newchaptersurl = el2.attr("href");
            newchaptersname = el2.text();
            chapterBean = new ChapterBean(newchaptersurl, newchaptersname);
            chapterBeanArrayList.add(chapterBean);
        }
        for (int i = 0; i < nameBeanArrayList.size(); i++) {
            String url = nameBeanArrayList.get(i).url;
            String name = nameBeanArrayList.get(i).name;
            String newchaptersurl = "www.qiushu.cc/" + chapterBeanArrayList.get(i).newchaptersurl;
            String newchaptersname = chapterBeanArrayList.get(i).newchaptersname;
            newNovelBean = new NewNovelBean(url, name, newchaptersurl, newchaptersname);
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
