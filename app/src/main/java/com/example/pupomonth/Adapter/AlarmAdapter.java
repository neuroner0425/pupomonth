package com.example.pupomonth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pupomonth.data.ItemAlarm;
import com.example.pupomonth.R;

import java.util.List;

public class AlarmAdapter extends BaseAdapter {
    private final List<ItemAlarm> listData;
    private final LayoutInflater layoutInflater;

    public AlarmAdapter(Context context, List<ItemAlarm> listData) {
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
            convertView = layoutInflater.inflate(R.layout.alarm_item_view, null);

            holder = new ViewHolder();

            holder.tvDDay = (TextView) convertView.findViewById(R.id.tvDDay);
            holder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemAlarm itemAlarm = this.listData.get(position);
        holder.tvDDay.setText("D - "+ itemAlarm.getDDay());
        holder.tvComment.setText(itemAlarm.getComment());
        holder.tvTitle.setText(itemAlarm.getTitle());

        return convertView;
    }

    static class ViewHolder {
       TextView tvDDay;
       TextView tvComment;
       TextView tvTitle;
       ImageView imgTest;
    }
}