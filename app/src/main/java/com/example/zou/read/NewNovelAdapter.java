package com.example.zou.read;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zou.chapter.ActivityChapter;
import com.example.zou.novellist.ActivityNovelList;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NewNovelAdapter extends BaseAdapter {
    private Context context;
    ArrayList<NewNovelBean> newnovelbean=new ArrayList();
    private LayoutInflater mnewNovelInflater;

    public NewNovelAdapter(ArrayList<NewNovelBean> newnovelbean) {
        this.newnovelbean = newnovelbean;
        this.context=MainActivity.getContext();
        mnewNovelInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newnovelbean.size();
    }

    @Override
    public Object getItem(int position) {
        return newnovelbean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=mnewNovelInflater.inflate(R.layout.new_novel_listitem,null);
        TextView tv_new_novelname= (TextView) convertView.findViewById(R.id.tv_new_novelname);
        TextView tv_new_newchaptersname= (TextView) convertView.findViewById(R.id.tv_new_newchaptersname);

        tv_new_novelname.setText(newnovelbean.get(position).name);
        tv_new_novelname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novelurl=newnovelbean.get(position).url;
                Intent intent=new Intent(MainActivity.mainActivity, ActivityNovelList.class);
                intent.putExtra("novelurl",novelurl);
                MainActivity.mainActivity.startActivity(intent);
            }
        });
        tv_new_newchaptersname.setText(newnovelbean.get(position).newchaptersname);
        tv_new_newchaptersname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newchapterurl=newnovelbean.get(position).newchaptersurl;
                Intent intent=new Intent(MainActivity.mainActivity, ActivityChapter.class);
                intent.putExtra("newchapterurl",newchapterurl);
                MainActivity.mainActivity.startActivity(intent);
            }
        });
        return convertView;
    }
}