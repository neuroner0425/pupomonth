package com.example.pupomonth.activity;

import static com.example.pupomonth.R.*;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pupomonth.service.NetworkStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;


import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class CameraResultTempActivity extends AppCompatActivity {
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
    TextView tvLog;
    File tempSelectFile;

    public interface UploadAPIs {
        @Multipart
        @POST("/api/upload")
        Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);
    }

    private void upload(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

//        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
//        if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
//            final Thread thread = new Thread(() -> {
//                try {
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
//
//                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl("http://www.departmentofsw.site/")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//
//                    UploadAPIs uploadAPIs = retrofit.create(UploadAPIs.class);
//                    Call<ResponseBody> call = uploadAPIs.uploadImage(body);
//                    call.enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            if (response.isSuccessful()) {
//                                String logTXT;
//                                try {
//                                    logTXT = response.body().string();
//                                } catch (IOException e) {
//                                    logTXT = e.toString();
//                                }
//                                Log.i("Upload Success", "Response: " + logTXT);
//                                String finalLogTXT = logTXT;
//                                runOnUiThread(()->{
//                                    tvLog.setText(finalLogTXT);
//                                });
//                            } else {
//                                Log.i("Upload Error", "Response Code: " + response.code());
//                                String code;
//                                try {
//                                    assert response.errorBody() != null;
//                                    code = response.code() + response.errorBody().string();
//                                } catch (IOException e) {
//                                    code = "" + response.code();
//                                }
//                                String finalCode = code;
//                                runOnUiThread(()->{
//                                    tvLog.setText(finalCode);
//                                });
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Log.e("Upload Error", Objects.requireNonNull(t.getMessage()));
//                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "업로드 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show());
//                        }
//                    });
//                }catch (Exception e) {
//                    Log.i("tag", "error :" + e);
//                }
//            });
//            thread.start();
//        }else {
//            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
//        }
        final Thread thread = new Thread(() -> {
                try {
                    sleep(8000);
                    Intent intent = new Intent(CameraResultTempActivity.this, CameraResultActivity.class);
                    intent.putExtra("bm",(Bitmap) bitmap);
                    finish();
                    startActivity(intent);

                }catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            });
            thread.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.loading);

        Intent intent = getIntent();
        bitmap = (Bitmap)intent.getParcelableExtra("bm");

        setImgByURL("http://contents.jabcho.org/uploads/A.jpg",((ImageView)findViewById(id.imgWhileLoading)));
        tvLog = findViewById(id.tvLog);

        upload(bitmap);
    }


}