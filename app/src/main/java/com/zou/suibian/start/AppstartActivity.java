package com.zou.suibian.start;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zou.suibian.R;
import com.zou.suibian.novel.start.StartActivity;

import java.util.ArrayList;

import static com.zou.suibian.R.id.tv_appstart;

/**
 * Created by 邹远君 on 2017/8/20.
 */

public class AppstartActivity extends AppCompatActivity {
    public GridView gv_appstart;
    Context context;
    public  Intent intent;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart_activity);
        gv_appstart= (GridView) findViewById(R.id.gv_appstart_activity);
        toolbar= (Toolbar) findViewById(R.id.tb_appstartactivity);
        gv_appstart.setNumColumns(2);
        context=this;
        ArrayList ImagearrayList=new ArrayList();
        ImagearrayList.add(R.drawable.novel);
        ImagearrayList.add(R.drawable.news);
        ImagearrayList.add(R.drawable.weather);
        ImagearrayList.add(R.drawable.historytaday);
        ImagearrayList.add(R.drawable.wechat);
        ImagearrayList.add(R.drawable.express);
        ArrayList Textarratlist=new ArrayList();
        Textarratlist.add("看小说");
        Textarratlist.add("看新闻");
        Textarratlist.add("看天气");
        Textarratlist.add("看历史");
        Textarratlist.add("看微信");
        Textarratlist.add("查快递");
        gv_appstart.setAdapter(new GVadapter(ImagearrayList,Textarratlist));
        gv_appstart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent=new Intent(AppstartActivity.this, StartActivity.class);

                }
                startActivity(intent);
            }
        });

    }
    public class GVadapter extends BaseAdapter {
        public ImageView imageView;;
        public TextView textView;
        public ArrayList arrayList=new ArrayList();
        ArrayList Textarratlist=new ArrayList();

        public GVadapter(ArrayList arrayList, ArrayList textarratlist) {
            this.arrayList = arrayList;
            Textarratlist = textarratlist;
        }
        @Override
        public int getCount() {
            return arrayList.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= LayoutInflater.from(context).inflate(R.layout.appstartactivity_item, null);
            imageView= (ImageView) view.findViewById(R.id.iv_appstart);
            textView=(TextView) view.findViewById(R.id.tv_appstart);
            imageView.setImageResource((int)arrayList.get(position));
            textView.setText((String)Textarratlist.get(position));
            return view;
        }

    }
}
