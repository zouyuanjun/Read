package com.zou.suibian.novel.site.chapter;

import android.util.Log;

import com.zou.suibian.novel.chapter.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


/**
 * Created by zou on 2016/7/14.
 */
public class DldParse {
    String html;
    String lasturl;
    String nexturl;
    String resultString;
    Result result;

    String baseurl="http://www.doulaidu.com/";
    public DldParse(String html){
        this.html=html;
        parse();
    }
    public void parse(){
        Document doc = Jsoup.parse(html,"http://www.doulaidu.com/");
        Element content = doc.getElementById("content");
        resultString =content.toString();

        Elements link=doc.getElementsByClass("page_chapter");
        Elements links=link.select("li");
        Elements herf=links.select("a[href]");
        ArrayList<String> url=new ArrayList();
        for (Element urls : herf) {
            String sdf=urls.toString();
            String linkHref=urls.attr("href");
            url.add(linkHref);
        }

        Elements title=doc.getElementsByClass("p");
        Elements title2=title.select(   "a[href]");
        String titlestring=title2.text();
        int index=url.get(0).lastIndexOf("/");
        Log.d("55555","上一张："+url.get(0).substring(index+1));
        Log.d("55555","下一张："+titlestring);
       result=new Result(resultString,url.get(0).substring(index+1),url.get(2).substring(index+1),titlestring,titlestring);
    }

    public Result getResult() {
        return result;
    }
}
