package com.example.zou.read;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zou on 2016/7/13.
 */
public class HttpLoad extends AsyncTask<String,Void,String> {
    private  String result="";
    DataDownloadListener dataDownloadListener;
    public  HttpLoad(){
        Log.d("55555","异步任务创建");
    }
    public void setDataDownloadListener(DataDownloadListener dataDownloadListener) {
        this.dataDownloadListener = dataDownloadListener;
    }

    private String HttpConnection(String urlString) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        final URL url;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
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
            isr=new InputStreamReader(in,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            while ((line=br.readLine())!=null){
                result+=line;
            }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            Log.d("55555","正在联网");
            String result=HttpConnection(params[0]) ;
            Log.d("55555","获取的数据为"+result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        result="什么也没有";
        Log.d("55555",result);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result!=null&&result.length()!=0){
            Log.d("55555","读取成功"+result);
            dataDownloadListener.dataDownloadSuccessfully(result);
        }else {
            dataDownloadListener.dataDownloadFailed();
        }
    }
    public static interface DataDownloadListener{
        void dataDownloadSuccessfully(String result);
        void dataDownloadFailed();
    }
}