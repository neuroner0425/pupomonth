package com.example.pupomonth.activity;

import static com.example.pupomonth.R.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pupomonth.R;
import com.example.pupomonth.service.NetworkStatus;

import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    ImageButton navi3;
    ImageButton navi4;

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

    private void runCameraResult(Bitmap bitmap){
        Intent cameraResultIntent = new Intent(MainActivity.this, CameraResultTempActivity.class);
        cameraResultIntent.putExtra("bm",(Bitmap) bitmap);
        startActivity(cameraResultIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.all_activity_main);

        navi3 = findViewById(R.id.navi3);
        navi4 = findViewById(R.id.navi4);

        navi3.setOnClickListener((v)->{
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mPreContractStartActivityResult.launch(cameraIntent);
        });

        navi4.setOnClickListener(v->{
            Intent nintent = new Intent(MainActivity.this, BoardTempActivity.class);
            startActivity(nintent);
        });
    }

    private final ActivityResultLauncher<Intent> mPreContractStartActivityResult =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    a_result -> {
                        if (a_result.getResultCode() == Activity.RESULT_OK) {
                            assert a_result.getData() != null;
                            Bitmap photo = (Bitmap) Objects.requireNonNull(a_result.getData().getExtras()).get("data");
                            runCameraResult(photo);
                        }
                    });
}