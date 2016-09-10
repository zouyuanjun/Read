package com.qiushu.chapter;

import com.example.zou.chapter.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by zou on 2016/9/10.
 */
public class QiushuParse {
    String html;
    String content="5";
    Result result;
    ArrayList<String> arrayList=new ArrayList<>();

    public QiushuParse(String html) {
        this.html = html;
        Parse();
    }

    public String getContent() {
        return content;
    }

    public void Parse(){
        Document document= Jsoup.parse(html);
        Element element=document.getElementById("content");
        element.removeAttr("href");
        content=element.text();

        Elements elements=document.getElementsByClass("book_page");
        Elements elements1=elements.select("a");
        for (Element element1:elements1){
            String url=element1.attr("href");
            arrayList.add(url);
        }
        result=new Result(content,arrayList.get(3),arrayList.get(1));
    }
}
