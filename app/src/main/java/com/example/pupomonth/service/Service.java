package com.example.pupomonth.service;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.pupomonth.R;

import java.net.URL;


public class Service {
    public boolean permissionsGranted(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void doRequestPermissions(Context context, String[] permissions,int requestCode){
        requestPermissions((Activity) context, permissions, requestCode);
    }

    private void setImgByURL(Context _context, String _url, ImageView _imgV) {
        int status = NetworkStatus.getConnectivityStatus(_context);
        if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
            final Thread thread = new Thread(() -> {
                try {
                    URL url = new URL(_url);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    ((Activity)_context).runOnUiThread(()->{
                        _imgV.setImageBitmap(bmp);
                    });
                }catch (Exception e) {
                    Log.i("tag", "error :" + e);
                    _imgV.setImageResource(R.drawable.baseline_hide_image_24);
                }
            });
            thread.start();
        }else {
            _imgV.setImageResource(R.drawable.baseline_hide_image_24);

        }
    }
}
