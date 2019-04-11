package com.application.dev.david.coolorsapp.modules.storedPalette.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.dev.david.coolorsapp.R;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.List;
import java.util.zip.Inflater;

public class StoredPaletteRecyclerViewAdapter extends RecyclerView.Adapter<StoredPaletteRecyclerViewAdapter.StoredPaletteViewHolder> {
        private final List<StoredColorPalette> items;

        public StoredPaletteRecyclerViewAdapter(List<StoredColorPalette> list) {
            this.items = list;
        }
        @NonNull
        @Override
        public StoredPaletteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_stored_palette_item, viewGroup, false);
            return new StoredPaletteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StoredPaletteViewHolder viewHolder, int i) {
            viewHolder.title.setText("Palette n#" + items.get(i).getColorPaletteId());
            List<String> list = items.get(i).getColorPaletteList();
            int pos = 0;
            for (String item: list) {
                try {
                    ((TextView) viewHolder.paletteList.getChildAt(pos)).setText(item);
                    viewHolder.paletteList.getChildAt(pos).setBackgroundColor(Color.parseColor(item));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pos ++;
            }

            if (list.size() == 1) {
                for (pos = 1; pos <= 4; pos ++) {
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
                this.timestamp= itemView.findViewById(R.id.colorStoredPaletteTimestampId);
                this.title = itemView.findViewById(R.id.colorStoredPaletteTitleId);
            }
        }
}