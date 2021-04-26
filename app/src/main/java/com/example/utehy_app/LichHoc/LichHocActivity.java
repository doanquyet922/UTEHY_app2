package com.example.utehy_app.LichHoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.utehy_app.R;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

public class LichHocActivity extends AppCompatActivity {
Toolbar toolbar;
CustomCalendar customCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_hoc);
        init();
    }

    private void init() {
        toolbar=findViewById(R.id.LichHoc_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lịch học");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customCalendar=findViewById(R.id.LichHoc_callendar);
        HashMap<Object, Property> descHashMap=new HashMap<>();
        Property defaultProperty=new Property();
        defaultProperty.layoutResource=R.layout.default_view;
        defaultProperty.dateTextViewResource=R.id.default_view_tv;
        descHashMap.put("default",defaultProperty);
        Property currentProperty=new Property();
        currentProperty.layoutResource=R.layout.current_new;
        currentProperty.dateTextViewResource=R.id.current_view_tv;
        descHashMap.put("current",currentProperty);
        Property presentProperty=new Property();
        presentProperty.layoutResource=R.layout.present_view;
        presentProperty.dateTextViewResource=R.id.present_view_tv;
        descHashMap.put("present",presentProperty);

        Property absentProperty=new Property();
        presentProperty.layoutResource=R.layout.absent_view;
        presentProperty.dateTextViewResource=R.id.absent_view_tv;
        descHashMap.put("absent",presentProperty);

        customCalendar.setMapDescToProp(descHashMap);

        HashMap<Integer, Object> dateHash=new HashMap<>();
        Calendar calendar=Calendar.getInstance();
        dateHash.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        dateHash.put(1,"present");
        dateHash.put(2,"absent");
        dateHash.put(3,"present");
        dateHash.put(4,"absent");
        dateHash.put(20,"present");
        dateHash.put(30,"absent");

        customCalendar.setDate(calendar,dateHash);
        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                String date=selectedDate.get(Calendar.DAY_OF_MONTH)
                        +"/"+(selectedDate.get(Calendar.MONTH)+1)
                        +"/"+(selectedDate.get(Calendar.YEAR));
                Toast.makeText(LichHocActivity.this, ""+date, Toast.LENGTH_SHORT).show();
            }
        });

    }
}