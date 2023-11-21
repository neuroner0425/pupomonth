package com.example.pupomonth;

import static com.example.pupomonth.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CameraResultActivity extends AppCompatActivity {


    private void setImgByURL(String _url, ImageView _imgV) {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
            final Thread thread = new Thread(() -> {
                try {
                    URL url = new URL(_url);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    runOnUiThread(()->{
                        _imgV.setImageBitmap(bmp);
                    });
                }catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            });
            thread.start();
        }else {
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    Bitmap bitmap;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.camera_activity);

        Intent intent = getIntent();
        bitmap = (Bitmap)intent.getParcelableExtra("bm");

        setImgByURL("http://contents.jabcho.org/uploads/A.jpg",((ImageView)findViewById(id.imgViewCamera)));


        List<ItemCamera> cameraList = new ArrayList<>();
        cameraList.add(new ItemCamera("원서접수모집1차","2023.11.01~2023.12.08"));
        cameraList.add(new ItemCamera("원서접수모집2차","2023.12.11~2024.01.12" +
                ""));
        cameraList.add(new ItemCamera("면접모집1차","2023.11.01"));
        cameraList.add(new ItemCamera("면접모집2차","2024.01.17"));
        cameraList.add(new ItemCamera("합격자발표등록모집1차","2023.12.18~2023.12.21"));
        cameraList.add(new ItemCamera("합격자발표등록모집2차","2024.01.19~2024.01.24"));
        cameraList.add(new ItemCamera("교육기간","2024.03.01~2024.12.01"));

        ScrollView scrollView = findViewById(id.cameraScrollView);
        ListView listView = findViewById(id.cameraListView);
        listView.setOnTouchListener((v, event) -> {
            scrollView.requestDisallowInterceptTouchEvent(true);
            return false;
        });
        CameraAdapter cameraAdapter = new CameraAdapter(this, cameraList);
        listView.setAdapter(cameraAdapter);

        ((Button)findViewById(id.btCommit)).setOnClickListener((v)->{
            final Thread thread = new Thread(() -> {
                try {
                    finish();
                    Intent nIntent = new Intent(CameraResultActivity.this, BoardActivity.class);
                    startActivity(nIntent);
                }catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            });
            thread.start();
        });

    }

}