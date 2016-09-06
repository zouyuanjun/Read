package com.example.zou.read;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


/**
 * Created by zou on 2016/7/13.
 */
public class HttpParse {
    String html;
    ArrayList<NewNovelBean> newnovelbean=new ArrayList();
    String url;
    String name;
    String newchaptersurl;
    String newchaptersname;
    String baseurl="http://www.doulaidu.com/";
    public HttpParse(String html){
        this.html=html;
        parse();
    }
    public void parse(){
        Document doc = Jsoup.parse(html,"http://www.doulaidu.com/");
        Element doc1 = doc.getElementById("newscontent");
        Elements content = doc1.getElementsByClass("l");
        Elements links1=content.select("li");
        Elements links = links1.select("a[href]");
        int i=1;
        boolean tag=false;
        for (Element link : links) {
            switch (i){
                case 1: {
                    String linkHref=link.attr("href");
                    String linkText = link.text();
                    url=baseurl+linkHref;
                    name=linkText;
                    tag=false;
                    i=2;
                Log.d("5555","添加署名："+name);
                    break;
                 }
                case 2:{
                    String linkHref = link.attr("href");
                    String linkText = link.text();
                    newchaptersurl=baseurl+linkHref;
                    newchaptersname=linkText;
                    i=1;
                    tag=true;
                    Log.d("5555","添加章节名："+newchaptersname);
                }
            }
            if (tag){
                NewNovelBean newNovelBean=new NewNovelBean(url,name,newchaptersurl,newchaptersname);
                Log.d("55557",url+name+newchaptersurl+newchaptersname);
                newnovelbean.add(newNovelBean);
            }

//            Log.d("55555","获取的属性："+linkHref);
//            Log.d("55555","获取的内容："+linkText);
        }
    }
    public ArrayList<NewNovelBean> getNewnovelbean(){
        return newnovelbean;
    }
}