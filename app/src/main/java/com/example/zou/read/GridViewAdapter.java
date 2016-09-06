package com.example.zou.read;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zou.chapter.ActivityChapter;
import com.example.zou.sql.FavoriteBean;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/19.
 */
public class GridViewAdapter extends BaseAdapter{
    ArrayList<FavoriteBean> favoriteBeanArrayList=new ArrayList<>();
    LayoutInflater layoutInflater;
    ListView listView;

    public GridViewAdapter(ArrayList<FavoriteBean> favoriteBeanArrayList,ListView listView) {
        layoutInflater=LayoutInflater.from(StartActivity.getContext());
        this.favoriteBeanArrayList=favoriteBeanArrayList;
        this.listView=listView;

    }

    @Override
    public int getCount() {
        return favoriteBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.gridview_item,null);
        TextView name= (TextView) convertView.findViewById(R.id.tv_gridview_item);
        name.setText(favoriteBeanArrayList.get(position).novelname);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(StartActivity.getContext(), ActivityChapter.class);
                intent.putExtra("newchapterurl",favoriteBeanArrayList.get(position).chapterurl);
                intent.putExtra("baseurl",favoriteBeanArrayList.get(position).novellist);
                StartActivity.getContext().startActivity(intent);
                Toast.makeText(StartActivity.getContext(),"选择了"+favoriteBeanArrayList.get(position)
                        .novelname,Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
