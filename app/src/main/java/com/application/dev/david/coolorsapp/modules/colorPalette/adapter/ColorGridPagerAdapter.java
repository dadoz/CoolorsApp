package com.application.dev.david.coolorsapp.modules.colorPalette.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.dev.david.coolorsapp.R;
import com.application.dev.david.coolorsapp.models.ColorPalette;
import com.application.dev.david.coolorsapp.utils.ColorUtils;

import java.util.List;

public class ColorGridPagerAdapter extends PagerAdapter {
    private final OnOptionItemClickListener listener;
    private List<ColorPalette> items;

    public ColorGridPagerAdapter(List<ColorPalette> list, OnOptionItemClickListener lst) {
        this.items = list;
        this.listener = lst;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.color_grid_view,
                container, false);
        container.addView(view);

        //trick to avoid empty page n2
        initView(view, position);
        return view;
    }

    private void initView(View view, int position) {
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_grid_item,
                        viewGroup, false);
                view1.getLayoutParams().height = viewGroup.getHeight() / 5;
                return new RecyclerView.ViewHolder(view1) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                String color = items.get(position).getColorList().get(i);
                ((TextView) viewHolder.itemView.findViewById(R.id.colorTextViewId)).setText(color);
                ((TextView) viewHolder.itemView.findViewById(R.id.colorTextViewId)).setTextColor(ColorUtils.lighten(Color.parseColor(color), 0.6f));
                ((ImageView) viewHolder.itemView.findViewById(R.id.colorImageViewId)).setColorFilter(ColorUtils.lighten(Color.parseColor(color), 0.6f), PorterDuff.Mode.SRC_ATOP);
                (viewHolder.itemView.findViewById(R.id.colorImageViewId)).setOnClickListener(v -> listener.onOptionClick(v, i));
                viewHolder.itemView.setBackgroundColor(Color.parseColor(color));
            }

            @Override
            public int getItemCount() {
                return items.size() > position ? items.get(position).getColorList().size() : 0;
            }
        });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public List<String> getColorList(int position) {
        return items.get(position).getColorList();
    }

    public void setItems(int requestedPos, List<ColorPalette> list) {
        if (items.size() > requestedPos)
            items.remove(requestedPos);
        items.addAll(requestedPos, list);
    }

    public interface OnOptionItemClickListener {
        void onOptionClick(View v, int postion);
    }
}
