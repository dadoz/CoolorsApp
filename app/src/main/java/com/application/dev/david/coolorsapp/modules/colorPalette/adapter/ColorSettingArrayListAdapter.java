package com.application.dev.david.coolorsapp.modules.colorPalette.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
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
import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.List;

public class ColorSettingArrayListAdapter extends ArrayAdapter<String> {

    private final int resource;
    private OnDialogItemClickListener listener;
    private int selectedOppositeColor = -1;
    private final List<String> items;

    public ColorSettingArrayListAdapter(@NonNull Context context, @LayoutRes int resource,
                                        @NonNull List<String> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.items = objects;
    }


    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ((TextView) v.findViewById(R.id.colorSettingTextViewId)).setText(items.get(position));
        ((ImageView) v.findViewById(R.id.colorSettingImageViewId)).setImageDrawable(
                ContextCompat.getDrawable(getContext(), getDrawableFromPosition(position)));

        if (selectedOppositeColor != -1) {
            ((TextView) v.findViewById(R.id.colorSettingTextViewId)).setTextColor(selectedOppositeColor);
            ((ImageView) v.findViewById(R.id.colorSettingImageViewId)).setColorFilter(selectedOppositeColor,
                    PorterDuff.Mode.SRC_ATOP);
        }
        v.setOnClickListener(v1 -> listener.onDialogItemClick(items, position));
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
                return R.drawable.ic_delete;
            case 4:
                return R.drawable.ic_elephant;
            default:
                return R.drawable.ic_potions;
        }
    }

    public void setSelectedOppositeColor(int selectedOppositeColor) {
        this.selectedOppositeColor = selectedOppositeColor;
         notifyDataSetChanged();
    }

    public void setOndialogItemClickListener(OnDialogItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnDialogItemClickListener {
        void onDialogItemClick(List<String> list, int position);
    }
}