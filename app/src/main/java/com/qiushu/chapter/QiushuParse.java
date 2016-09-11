package com.qiushu.chapter;

import android.util.Log;

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
    ArrayList<String> namelist=new ArrayList<>();

    public QiushuParse(String html) {
        this.html = html;
        Parse();
    }

    public Result getResult() {
        return result;
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
        Elements el=document.getElementsByClass("text");
        Elements elements2=el.select("a");
        for (Element element1:elements2){
            String name=element1.text();
            namelist.add(name);
        }
        Elements elements3=document.getElementsByClass("date");
        Elements elements4=elements3.select("h1");
        String chaptertitle=elements4.text();

        result=new Result(content,arrayList.get(2),arrayList.get(0),namelist.get(1),chaptertitle);
    }
}
