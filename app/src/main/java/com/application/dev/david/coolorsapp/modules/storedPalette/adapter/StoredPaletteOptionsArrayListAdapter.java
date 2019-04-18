package com.application.dev.david.coolorsapp.modules.storedPalette.adapter;

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

import java.util.List;

public class StoredPaletteOptionsArrayListAdapter extends ArrayAdapter<String> {

    private final int resource;
    private OnDialogItemClickListener listener;
    private final List<String> items;

    public StoredPaletteOptionsArrayListAdapter(@NonNull Context context, @LayoutRes int resource,
                                                @NonNull List<String> objects, OnDialogItemClickListener listener) {
        super(context, resource, objects);
        this.resource = resource;
        this.items = objects;
        this.listener = listener;
    }


    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ((TextView) v.findViewById(R.id.colorSettingTextViewId)).setText(items.get(position));
        ((TextView) v.findViewById(R.id.colorSettingTextViewId)).setTextColor(getTextColorByPos(position));
        ((ImageView) v.findViewById(R.id.colorSettingImageViewId)).setImageDrawable(
                ContextCompat.getDrawable(getContext(), getDrawableFromPosition(position)));

        v.setOnClickListener(v1 -> listener.onDialogItemClick(position));
        return v;
    }

    private int getDrawableFromPosition(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_edit;
            case 1:
                return R.drawable.ic_share;
            case 2:
                return R.drawable.ic_delete;
            default:
                return R.drawable.ic_potions;
        }
    }
    private int getTextColorByPos(int position) {
        switch (position) {
            case 2:
                return ContextCompat.getColor(getContext(), R.color.colorPrimaryRed);
            default:
                return ContextCompat.getColor(getContext(), R.color.colorPrimaryBlue);
        }
    }

    public interface OnDialogItemClickListener {
        void onDialogItemClick(int position);
    }
}