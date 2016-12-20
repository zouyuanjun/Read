package com.site.chapter;

import com.example.zou.chapter.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


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

        Element last=doc.getElementById("A1");
        Elements links2 = last.select("a[href]");
        for (Element link : links2) {
            String linkHref=link.attr("href");
            String linkText = link.text();
            lasturl=linkHref;
        }
        Element next=doc.getElementById("A3");
        Elements links3 = next.select("a[href]");
        for (Element link : links3) {
            String linkHref=link.attr("href");
            String linkText = link.text();
            nexturl=linkHref;
        }
        Elements title=doc.getElementsByClass("con_top");
        Elements title2=title.select("a[title]");
        String titlestring=title2.text();

       result=new Result(resultString,lasturl,nexturl,titlestring,titlestring);
    }

    public Result getResult() {
        return result;
    }
}
