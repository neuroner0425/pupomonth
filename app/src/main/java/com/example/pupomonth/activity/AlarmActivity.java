package com.example.pupomonth.activity;

import static com.example.pupomonth.R.*;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pupomonth.Adapter.AlarmAdapter;
import com.example.pupomonth.R;
import com.example.pupomonth.data.ItemAlarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.alarm);
        List<ItemAlarm> alarmList = new ArrayList<>();
        alarmList.add(new ItemAlarm(7,"뭘봐", "무슨무슨 대회"));
        alarmList.add(new ItemAlarm(7,"뭘봐2", "하하하하"));
        alarmList.add(new ItemAlarm(7,"뭘봐3", "호호호호"));
        alarmList.add(new ItemAlarm(7,"뭘봐", "무슨무슨 대회"));
        alarmList.add(new ItemAlarm(7,"뭘봐2", "하하하하"));
        alarmList.add(new ItemAlarm(7,"뭘봐3", "호호호호"));
        alarmList.add(new ItemAlarm(7,"뭘봐", "무슨무슨 대회"));
        alarmList.add(new ItemAlarm(7,"뭘봐2", "하하하하"));
        alarmList.add(new ItemAlarm(7,"뭘봐3", "호호호호"));
        alarmList.add(new ItemAlarm(7,"뭘봐", "무슨무슨 대회"));
        alarmList.add(new ItemAlarm(7,"뭘봐2", "하하하하"));
        alarmList.add(new ItemAlarm(7,"뭘봐3", "호호호호"));
        ScrollView scrollView = findViewById(id.alarmScrollView);
        ListView listView = findViewById(R.id.alarmListView);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        AlarmAdapter alarmAdapter = new AlarmAdapter(this, alarmList);
        listView.setAdapter(alarmAdapter);

    }

}