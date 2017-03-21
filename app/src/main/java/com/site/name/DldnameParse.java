package com.site.name;

import android.util.Log;

import com.example.z.novel.NewNovelBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Created by zou on 2016/7/13.
 */
public class DldnameParse {
    String html;
    ArrayList<NewNovelBean> newnovelbean=new ArrayList();
    String url;
    String name;
    String newchaptersurl;
    String newchaptersname;
    String baseurl="http://www.doulaidu.com";
    String picurl;
    String intro;
    public DldnameParse(String html) throws UnsupportedEncodingException {

            this.html=html;
        parse();
    }
    public void parse(){
        Document doc = Jsoup.parse(html,"http://www.doulaidu.com/");
        Element doc1 = doc.getElementById("newscontent");
        Elements content = doc.getElementsByClass("l");
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
                    break;
                 }
                case 2:{
                    String linkHref = link.attr("href");
                    String linkText = link.text();
                    newchaptersurl=baseurl+linkHref;
                    newchaptersname=linkText;
                    i=1;
                    tag=true;
                }
            }
            if (tag){
                NewNovelBean newNovelBean=new NewNovelBean(url,name,newchaptersurl,newchaptersname,picurl,intro);
                Log.d("55557",url+name+newchaptersurl+newchaptersname);
                newnovelbean.add(newNovelBean);
            }

        }
    }
    public ArrayList<NewNovelBean> getNewnovelbean(){
        return newnovelbean;
    }
}
