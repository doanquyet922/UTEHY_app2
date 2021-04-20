package com.example.utehy_app.DiemDanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;


import com.example.utehy_app.CustomAdapter.MonHocVang_Adapter;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.CTDiemDanh;
import com.example.utehy_app.Model.DiemDanh;
import com.example.utehy_app.Model.MonHoc;
import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class DiemDanhActivity extends AppCompatActivity {
    DatabaseReference mData;
    ArrayList<MonHoc> arrMonHoc=new ArrayList<>();
    ArrayList<DiemDanh>arrDiemDanh=new ArrayList<>();
    ArrayList<CTDiemDanh> arrCTDiemDanh;
Toolbar toolbar;
ListView listView;
ArrayList<MonHocVang> listMHV=new ArrayList<>();
    MonHocVang_Adapter adapterMHV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        init();
        Events();
    }

    private void Events() {
        adapterMHV = new MonHocVang_Adapter(DiemDanhActivity.this,listMHV);
        listView.setAdapter(adapterMHV);
    }

    private void init() {
        toolbar=(Toolbar) findViewById(R.id.DiemDanh_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Điểm danh");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView=findViewById(R.id.DiemDanh_lvMonHocVang);
        arrCTDiemDanh=new ArrayList<>();

    }


}