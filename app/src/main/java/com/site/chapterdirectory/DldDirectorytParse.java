package com.site.chapterdirectory;

import com.example.zou.novellist.ChapterDirectoryBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zou on 2016/7/14.
 */
public class DldDirectorytParse {
    String html;
    ArrayList<ChapterDirectoryBean> novelListbean=new ArrayList();
    public DldDirectorytParse(String html){
        this.html=html;
        parse();
    }
    public void parse(){
        Document doc = Jsoup.parse(html,"http://www.doulaidu.com/");
        Element doc1 = doc.getElementById("list");
        Elements links1=doc1.select("dl");
        Elements links = links1.select("a[href]");
        for (Element link : links) {
            String linkHref=link.attr("href");
            String linkText = link.text();
            //正则匹配处理章节URL
            String patterns="/(.*?)/(.*?)/";
            Pattern pattern=Pattern.compile(patterns);
            Matcher matcher=pattern.matcher(linkHref);
            int size=0;
            if (matcher.find()){
                size=matcher.group().length();
            }
            String url=linkHref.substring(size-1,linkHref.length());
            ChapterDirectoryBean novelListBean=new ChapterDirectoryBean(url,linkText);
            novelListbean.add(novelListBean);
//            Log.d("55555","获取的属性："+linkHref);
//            Log.d("55555","获取的内容："+linkText);
        }
    }
    public ArrayList<ChapterDirectoryBean> getnovelListbean(){
        return novelListbean;
    }
}
