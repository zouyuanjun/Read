package com.qiushu.chapterdirectory;

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
public class QiushuDirectoryParse {
    String html;
    ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();

    public QiushuDirectoryParse(String html) {
        this.html = html;
        Parse();
    }
    public void Parse(){
        Document doc = Jsoup.parse(html);
        Elements elements=doc.getElementsByClass("book_con_list");
        Elements elements1=elements.select("a");
        for (Element link:elements1){
            String url=link.attr("href");
            String text=link.text();
            ChapterDirectoryBean chapterDirectoryBean=new ChapterDirectoryBean(url,text);
            novelListbean.add(chapterDirectoryBean);
        }
    }

    public ArrayList<ChapterDirectoryBean> getNovelListbean() {
        return novelListbean;
    }
}
