package com.example.pupomonth;

import android.content.Context;
import android.media.CameraProfile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class CameraAdapter extends BaseAdapter {
    private final List<ItemCamera> listData;
    private final LayoutInflater layoutInflater;

    public CameraAdapter(Context context, List<ItemCamera> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.camera_item_item, null);

            holder = new ViewHolder();

            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvDate = (Switch) convertView.findViewById(R.id.cameraswitch1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemCamera itemCamera = this.listData.get(position);
        holder.tvTitle.setText(itemCamera.getTitle());
        holder.tvDate.setText(itemCamera.getDate());

        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle;
        Switch tvDate;
    }
}