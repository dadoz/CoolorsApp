package com.application.dev.david.coolorsapp.modules.colorGrid.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.dev.david.coolorsapp.R;

import java.util.List;

public class ColorSettingArrayListAdapter extends ArrayAdapter<String> {

    private final int resource;
    private final List<String> items;
    private final int color;

    public ColorSettingArrayListAdapter(@NonNull Context context, @LayoutRes int resource,
                                        @NonNull List<String> objects, int color) {
        super(context, resource, objects);
        this.resource = resource;
        this.items = objects;
        this.color = color;

    }
    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {
        View v =LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ((TextView) v.findViewById(R.id.colorSettingTextViewId)).setText(items.get(position));
        ((TextView) v.findViewById(R.id.colorSettingTextViewId)).setTextColor(color);
        ((ImageView) v.findViewById(R.id.colorSettingImageViewId)).setImageDrawable(
                ContextCompat.getDrawable(getContext(), getDrawableFromPosition(position)));
        ((ImageView) v.findViewById(R.id.colorSettingImageViewId)).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        return v;
    }

    private int getDrawableFromPosition(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_pin;
            case 1:
                return R.drawable.ic_lock;
            case 2:
                return R.drawable.ic_share;
            case 3:
                return R.drawable.ic_elephant;
            default:
                return R.drawable.ic_potions;
        }
    }
}