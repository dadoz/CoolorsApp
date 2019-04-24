package com.application.dev.david.coolorsapp.modules.storedPalette.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.dev.david.coolorsapp.R;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;
import com.application.dev.david.coolorsapp.utils.DateFormatUtils;

import java.util.List;

public class StoredPaletteRecyclerViewAdapter extends RecyclerView.Adapter<StoredPaletteRecyclerViewAdapter.StoredPaletteViewHolder> {
    private final List<StoredColorPalette> items;
    private final OnStoredItemOptionClickListener listener;

    public StoredPaletteRecyclerViewAdapter(List<StoredColorPalette> list, OnStoredItemOptionClickListener listener) {
        this.items = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public StoredPaletteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_stored_palette_item, viewGroup, false);
        return new StoredPaletteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoredPaletteViewHolder viewHolder, int i) {
        viewHolder.timestamp.setText(DateFormatUtils.formatDate(items.get(i).getCreatedAt()));
        viewHolder.title.setText("Palette n# \n" + items.get(i).getColorPaletteId());
        viewHolder.itemView.setOnClickListener(v -> listener.onStoredItemOptionClick(v, i));
        List<String> list = items.get(i).getColorPaletteList();
        int pos = 0;
        //palette adding
        for (String item: list) {
            try {
                ((TextView) viewHolder.paletteList.getChildAt(pos)).setText(item);
                viewHolder.paletteList.getChildAt(pos).setBackgroundColor(Color.parseColor(item));
            } catch (Exception e) {
                e.printStackTrace();
            }
            pos ++;
        }

        for (pos = 0; pos < 5; pos ++) {
            if (pos >= list.size()) {
                viewHolder.paletteList.getChildAt(pos).setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class StoredPaletteViewHolder extends RecyclerView.ViewHolder {
        private final TextView timestamp;
        public TextView title;
        public ViewGroup paletteList;

        public StoredPaletteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.paletteList = itemView.findViewById(R.id.colorStoredPaletteLayoutContainerId);
            this.timestamp = itemView.findViewById(R.id.colorStoredPaletteTimestampId);
            this.title = itemView.findViewById(R.id.colorStoredPaletteTitleId);
        }
    }

    public interface OnStoredItemOptionClickListener {
        void onStoredItemOptionClick(View v, int position);
    }
}