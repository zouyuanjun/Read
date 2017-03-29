package com.example.z.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.z.start.Setting;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zou on 2016/7/13.
 */
public class HttpLoad extends AsyncTask<String,Void,String> {
    private  String result="";
    String content;
    DataDownloadListener dataDownloadListener;
    public  HttpLoad(){
        Log.d("55555","异步任务创建");
    }
    public void setDataDownloadListener(DataDownloadListener dataDownloadListener) {
        this.dataDownloadListener = dataDownloadListener;
    }
    private String OKhttp(String url)throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder=new  Request.Builder();
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 Greatwqs");
        Request request = builder.url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            byte[] bytes = response.body().bytes(); //获取数据的bytes
            switch (Setting.getSource()){
                case 1:  {
                    content = new String(bytes,"GBK");
                    Log.d("6666","源代码为1");
                    break;
                }
                case 2:{
                    Log.d("6666","源代码为2");
                    content = new String(bytes,"GBK");
                    break;
                }
            }
            return content;
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }
    private String HttpConnection(String urlString) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        final URL url;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 Greatwqs");
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader isr;
        String result="";
        String line="";
        try {
            if (in!=null){
            isr=new InputStreamReader(in,"GB2312");
            BufferedReader br=new BufferedReader(isr);
            while ((line=br.readLine())!=null){
                result+=line;
            }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlConnection.disconnect();
        return result;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
           // String result=HttpConnection(params[0]) ;
            Log.d("6666","本次请求的网址为:"+params[0]);
           String result=OKhttp(params[0]);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        result=null;
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result!=null&&result.length()!=0){
            try {
                dataDownloadListener.dataDownloadSuccessfully(result);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            dataDownloadListener.dataDownloadFailed();
        }
    }
    public static interface DataDownloadListener{
        void dataDownloadSuccessfully(String result) throws UnsupportedEncodingException;
        void dataDownloadFailed();
    }
}