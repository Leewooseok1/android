package com.example.lws.work.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lws.work.R;
import com.example.lws.work.Sub1Activity;
import com.example.lws.work.common.SquareImageView;
import com.example.lws.work.model.WardrobeMO;

import java.util.ArrayList;
import java.util.List;

public class RecommendColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 리사이클러뷰 어뎁터를 상속받는다
    // 그리드어뎁터의 어레이리스트뷰와 코딩 및 주속 유사하다.
    // 자주찾는 옷 6개 거기에 색상 넣을수있는 ""이미지뷰"" 만들때 사용

    public static final int TYPE_COLOR = 1;
    public static final int TYPE_FAVORITES = 2;

    private List<InnerItem> listItem = new ArrayList<>();

    private class InnerItem {
        public int type;
        public Object item;

        public InnerItem(int type, Object obj) {
            this.type = type;
            this.item = obj;
        }
    }

    private List<WardrobeMO> list;
    private Context context;

    public RecommendColorAdapter(Context context) {
        this.context = context;
    }

    public void setItem(List<Integer> colorList) {
        listItem.clear();

        for (Integer color : colorList) {
            listItem.add(new InnerItem(TYPE_COLOR, color)); // 메인프래그맨트에 있던 컬러 추가
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case TYPE_COLOR:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_color_item, parent, false);
                holder = new MainViewHolder(v);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wardrobe_1, parent, false);
                holder = new MainViewHolder(v);
                break;
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();

        switch (viewType) {
            case TYPE_COLOR:
                ((MainViewHolder) holder).setItem((Integer) listItem.get(position).item);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listItem.get(position).type;
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        View v;
        CardView viewColor;

        public MainViewHolder(@NonNull View v) {
            super(v);
            this.v = v;
            viewColor = v.findViewById(R.id.viewColor);
        }

        public void setItem(final Integer color) {
            viewColor.setCardBackgroundColor(color);
        }
    }
}
