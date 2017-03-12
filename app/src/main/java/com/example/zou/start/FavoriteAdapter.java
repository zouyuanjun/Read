package com.example.zou.start;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zou.chapter.ActivityChapter;
import com.example.zou.read.R;
import com.example.zou.sql.Novel;
import com.facebook.drawee.view.SimpleDraweeView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by zou on 2016/7/19.
 */
public class FavoriteAdapter extends RecyclerView.Adapter {

    //数据源
    private List<String> dataList;
    //构造函数
    public FavoriteAdapter(List<String> dataList) {
        this.dataList = dataList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.startactivity_item, null));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            //其他条目中的逻辑在此
            ((BodyViewHolder) viewHolder).getfavorite_nover_name().setText(dataList.get(position ));
    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }
     /**
     * 给GridView中的条目用的ViewHolder
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
         private TextView favorite_nover_name;
         private SimpleDraweeView draweeView ;

         public SimpleDraweeView getDraweeView() {
             return draweeView;
         }

         public TextView getTv_chapter_account() {
            return tv_chapter_account;
        }

        private TextView tv_chapter_account;
        public BodyViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.im_favorite_view);
            favorite_nover_name = (TextView) itemView.findViewById(R.id.tv_favorite_nover_name);
            tv_chapter_account= (TextView) itemView.findViewById(R.id.tv_chapter_account);
        }
        public TextView getfavorite_nover_name() {
            return favorite_nover_name;
        }
    }
}