package com.example.pupomonth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends BaseAdapter {
    Context context;
    private final ArrayList<ItemBoard> listData;
    private final LayoutInflater layoutInflater;
    Bitmap bitmap;

    public BoardAdapter(Context context, ArrayList<ItemBoard> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
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
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(context).inflate(R.layout.board_list_item_layout, null);

        ImageView iv = view.findViewById(R.id.pic_image);
        Glide.with(view).load(listData.get(position).getImgURL()).into(iv);

        Button tv = view.findViewById(R.id.boardTitle);
        tv.setText(listData.get(position).getTitle());
        return view;
    }
}