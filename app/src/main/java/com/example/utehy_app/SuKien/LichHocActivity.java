package com.example.utehy_app.SuKien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarWeekDay;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.utehy_app.DrawableUtils;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.SuKien;
import com.example.utehy_app.Model.ThuTrongTuan;
import com.example.utehy_app.R;
import com.example.utehy_app.calendar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LichHocActivity extends AppCompatActivity   {
    DatabaseReference mData;
Toolbar toolbar;
TextView tvEvenDay;
com.applandeo.materialcalendarview.CalendarView calendarView;
ArrayList<SuKien> arrSK=new ArrayList<>();
    String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_hoc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        init();
        getSuKien();
    }

    private void init() {
        tvEvenDay=findViewById(R.id.tvEvenDay);
        toolbar=findViewById(R.id.LichHoc_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sự kiện");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calendarView=findViewById(R.id.LichHoc_callendar);


//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.add(Calendar.DAY_OF_MONTH, 10);
//        events.add(new EventDay(calendar1, R.drawable.sample_icon_2));

//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.add(Calendar.DAY_OF_MONTH, 10);
//        events.add(new EventDay(calendar2, R.drawable.sample_icon_3, Color.parseColor("#228B22")));


//
//        Calendar calendar4 = Calendar.getInstance();
//        calendar4.add(Calendar.DAY_OF_MONTH, 13);
//        events.add(new EventDay(calendar4, DrawableUtils.getThreeDots(this)));


        calendarView.setFirstDayOfWeek(CalendarWeekDay.SUNDAY);
        calendarView.setCalendarDayLayout(R.layout.custom_calendar_day_row);





    }
    private void getSuKien(){
        List<EventDay> events = new ArrayList<>();
        SinhVien sv= ManHinhChinhActivity.sv_hientai;
        String maLop=sv.getMaLop();
        mData.child("HoatDong").orderByChild("maLop").equalTo(maLop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn:snapshot.getChildren()){
                    SuKien sk=sn.getValue(SuKien.class);
                    arrSK.add(sk);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date dateSK=simpleDateFormat.parse(sk.getNgay());
                        Date today = new Date();

                        String da=simpleDateFormat.format(dateSK);
                        String to=simpleDateFormat.format(today);

                        if(da.equals(to)){
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.add(Calendar.DAY_OF_WEEK, 0);
                            events.add(new EventDay(calendar1, DrawableUtils.getThreeDots(LichHocActivity.this)));
                        }
                        else {

                            int t= daysBetween(today,dateSK);
                            if(t<0) {
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.add(Calendar.DAY_OF_WEEK, t);
                                events.add(new EventDay(calendar2, DrawableUtils.getThreeDots(LichHocActivity.this)));
                            }else {
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.add(Calendar.DAY_OF_WEEK, t+1);
                                events.add(new EventDay(calendar3, DrawableUtils.getThreeDots(LichHocActivity.this)));
                            }
//                            Log.d("BBB", "time: "+t);
                        }





                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    calendarView.setEvents(events);
                }
                calendarView.setOnDayClickListener(new OnDayClickListener() {
                    @Override
                    public void onDayClick(@NotNull EventDay eventDay) {

                        Calendar calendar=eventDay.getCalendar();
                        Date date=calendar.getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String ngayClick=simpleDateFormat.format(calendar.getTime());
//                        Log.d("BBB","date:"+ngayClick);
//                        Log.d("BBB","date:"+calendar.getTime());
                        mData.child("HoatDong").orderByChild("ngay").equalTo(ngayClick).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("BBB", "data: "+snapshot.getValue());
                                if (snapshot.getValue()!=null) {
                                    tvEvenDay.setText("Events:");
                                    for (DataSnapshot sn : snapshot.getChildren()) {
                                        SuKien sk = sn.getValue(SuKien.class);
                                        tvEvenDay.append("\n"+sk.getNoiDung());
//                                        Log.d("BBB", "HoatDong: " + sk.toString());

                                    }
                                }else {
                                    tvEvenDay.setText("Không có sự kiện nào");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("BBB", "onCancelled: "+error.getMessage());
                            }
                        });


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public int daysBetween(Date d1, Date d2){

        return (int)((d2.getTime() - d1.getTime()) / (1000.0 * 60.0 * 60.0 * 24.0));
    }
}