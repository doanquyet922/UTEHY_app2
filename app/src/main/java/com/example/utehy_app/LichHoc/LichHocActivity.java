package com.example.utehy_app.LichHoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.utehy_app.R;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LichHocActivity extends AppCompatActivity   {
Toolbar toolbar;
CalendarView calendarView;
    String selectedDate;
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
        calendarView=findViewById(R.id.LichHoc_callendar);
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
        descHashMap.put("absent",absentProperty);

//        customCalendar.setMapDescToProp(descHashMap);
//
//        HashMap<Integer, Object> dateHash=new HashMap<>();
//        Calendar calendar=Calendar.getInstance();
////        dateHash.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
////        dateHash.put(1,"present");
////        dateHash.put(2,"absent");
////        dateHash.put(3,"present");
////        dateHash.put(4,"absent");
////        dateHash.put(20,"present");
////        dateHash.put(30,"absent");
//        customCalendar.setDate(calendar,dateHash);
//
//
//
//        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
//                String date=selectedDate.get(Calendar.DAY_OF_MONTH)
//                        +"/"+(selectedDate.get(Calendar.MONTH)+1)
//                        +"/"+(selectedDate.get(Calendar.YEAR));
//                Toast.makeText(LichHocActivity.this, "date"+date, Toast.LENGTH_SHORT).show();
//            }
//        });
        Date date=new Date();


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate=Integer.toString(year)+Integer.toString(month)+Integer.toString(dayOfMonth);
                Log.d("date", "onSelectedDayChange: "+selectedDate);
            }
        });


    }
}