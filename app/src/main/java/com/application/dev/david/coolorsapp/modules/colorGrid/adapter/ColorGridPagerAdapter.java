package com.application.dev.david.coolorsapp.modules.colorGrid.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.dev.david.coolorsapp.R;

import java.util.List;

public class ColorGridPagerAdapter extends PagerAdapter {
    private static final int PAGER_SIZE = 20;
    private List<String> items;


    public ColorGridPagerAdapter(List<String> list) {
        this.items = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.color_grid_view, container, false);
        container.addView(view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view;

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_grid_item,
                        viewGroup, false);
                return new RecyclerView.ViewHolder(view1) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((TextView) viewHolder.itemView).setText(items.get(i));
                viewHolder.itemView.setBackgroundColor(Color.parseColor(items.get(i)));
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        });

//        int index = 0;
//        for (String hexColor : items) {
//            TextView box = (TextView) ((ViewGroup) view.findViewById(R.id.coolorsContainerLayoutId)).getChildAt(index);
//            box.setBackgroundColor(Color.parseColor(hexColor));
//            box.setText(hexColor);
//            index ++;
//        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return PAGER_SIZE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    public void setItems(List<String> list) {
        this.items = list;
    }
}
