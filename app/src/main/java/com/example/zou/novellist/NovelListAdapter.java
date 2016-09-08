package com.example.zou.novellist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zou.chapter.ActivityChapter;
import com.example.zou.read.R;
import com.example.zou.read.StartActivity;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NovelListAdapter extends BaseAdapter{
    private Context context;
    ArrayList<ChapterDirectoryBean> novelListBean =new ArrayList();
    private LayoutInflater mNovelListInflater;
    String url;
    String baseurl;
    int chapteaccount=0;
    public NovelListAdapter(ArrayList<ChapterDirectoryBean> novelListBean, String url) {
        this.novelListBean = novelListBean;
        this.context= StartActivity.getContext();
        this.baseurl=url;
        chapteaccount=novelListBean.size();
        mNovelListInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return novelListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return novelListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=mNovelListInflater.inflate(R.layout.novel_listitem,null);

        TextView chaptersname= (TextView) convertView.findViewById(R.id.tv_novel_list_item);

        chaptersname.setText(novelListBean.get(position).chaptersname);
        chaptersname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url= novelListBean.get(position).chaptersurl;
                Intent intent=new Intent(StartActivity.getContext(), ActivityChapter.class);
                intent.putExtra("newchapterurl",url);
                intent.putExtra("baseurl",baseurl);
                intent.putExtra("chapteraccount",chapteaccount);
                StartActivity.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
