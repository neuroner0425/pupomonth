package com.example.pupomonth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pupomonth.R;
import com.example.pupomonth.service.NetworkStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    EditText etID;
    EditText etPW;
    Button btnLogin;
    String session;

    private void nextActivity() {
        final Thread thread = new Thread(() -> {
            try {
                finish();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }catch (Exception e) {
                Log.i("tag", "error :" + e);
            }
        });
        thread.start();
    }

    private void isSession(String _session) {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
            final Thread thread = new Thread(() -> {
                try {
                    URL url = new URL("http://www.departmentofsw.site/api/auth.info?NSG_SES="+_session);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    final StringBuilder stringBuilder = new StringBuilder();
                    if(connection != null) {
                        Log.i("ONSERVERVERVERVERVER", "conn 연결");
                        connection.setConnectTimeout(10000);
                        connection.setRequestMethod("GET");
                        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                                    connection.getInputStream(), StandardCharsets.UTF_8
                            ));
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                            bufferedReader.close();
                            JSONObject jsonResponse = new JSONObject(stringBuilder.toString());
                            Log.i("ONSERVERVERVERVERVER", "확인 jsonArray : " + jsonResponse);
                            runOnUiThread(()->{
                                try {
                                    Toast.makeText(getApplicationContext(),"로그인됨: "+ jsonResponse.getString("userId"),Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    Log.i("ONSERVERVERVERVERVER","error :"+e);
                                }
                            });
                            nextActivity();
                        }else {
                            Log.i("ONSERVERVERVERVERVER","네트워크 문제 발생");
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "네트워크 문제 발생", Toast.LENGTH_SHORT).show();
                            });
                        }
                        connection.disconnect();
                    }

                }catch (Exception e) {
                    Log.i("ONSERVERVERVERVERVER", "error :" + e);
                }
            });
            thread.start();
        }else {
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void isIdPW(String _id, String _pw){
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(status != NetworkStatus.TYPE_NOT_CONNECTED) {
            final Thread thread = new Thread(() -> {
                try {
                    URL url = new URL("http://www.departmentofsw.site/api/auth.login");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", _id);
                        jsonObject.put("pw", _pw);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String json = jsonObject.toString();
                    Log.i("ONSERVERVERVERVERVER",json);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    final StringBuilder stringBuilder = new StringBuilder();
                    if(connection != null) {
                        Log.i("ONSERVERVERVERVERVER", "conn 연결");
                        connection.setConnectTimeout(10000);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        connection.setRequestProperty("Accept", "application/json");
                        OutputStream os = connection.getOutputStream();
                        os.write(json.getBytes(StandardCharsets.UTF_8));
                        os.close();
                        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                                    connection.getInputStream(), StandardCharsets.UTF_8
                            ));
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                            bufferedReader.close();
                            String session = stringBuilder.toString();
                            Log.i("ONSERVERVERVERVERVER", "session:"+session);
                            getSharedPreferences("pupomonth",Activity.MODE_PRIVATE).edit().putString("session",session).apply();
                        }else {
                            Log.i("ONSERVERVERVERVERVER","네트워크 문제 발생");
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "네트워크 문제 발생", Toast.LENGTH_SHORT).show();
                            });
                        }
                        connection.disconnect();
                    }

                }catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }

                isSession(getSharedPreferences("pupomonth",Activity.MODE_PRIVATE).getString("session",""));
            });
            thread.start();
        }else {
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etID = findViewById(R.id.etID);
        etPW = findViewById(R.id.etPW);
        btnLogin = findViewById(R.id.btnLogin);

        session = getSharedPreferences("pupomonth", Activity.MODE_PRIVATE).getString("session","");

        if (!session.isEmpty()){
            isSession(session);
        }

        btnLogin.setOnClickListener(v -> {
            if (etID.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this, "ID를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if (etPW.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else{
                isIdPW(etID.getText().toString(), etPW.getText().toString());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
//        getSharedPreferences("pupomonth", Activity.MODE_PRIVATE).edit().clear().apply();
    }
}
