package com.example.z.start;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.z.sql.Novel;
import com.example.zou.novel.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by zou on 2016/7/19.
 */
public class FavoriteAdapter extends RecyclerView.Adapter {

    //数据源
    private List<Novel> dataList;
    //构造函数
    public FavoriteAdapter(List<Novel> dataList) {
        this.dataList = dataList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.startactivity_item, null));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            //其他条目中的逻辑在此
            ((BodyViewHolder) viewHolder).getfavorite_nover_name().setText("asd");
            ((BodyViewHolder) viewHolder).getTv_chapter_account().setText("5554");
            ((BodyViewHolder) viewHolder).getDraweeView().setImageURI("http://img.qiushu.cc/55/55152/55152s.jpg");
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

        private TextView tv_chapter_account;
        public BodyViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.im_favorite_view);
            favorite_nover_name = (TextView) itemView.findViewById(R.id.tv_favorite_nover_name);
            tv_chapter_account= (TextView) itemView.findViewById(R.id.tv_chapter_account);
        }
         public SimpleDraweeView getDraweeView() {
             return draweeView;
         }

         public TextView getTv_chapter_account() {
             return tv_chapter_account;
         }
        public TextView getfavorite_nover_name() {
            return favorite_nover_name;
        }
    }
}