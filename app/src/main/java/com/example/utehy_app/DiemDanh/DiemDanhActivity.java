package com.example.utehy_app.DiemDanh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.utehy_app.CustomAdapter.MonHocVang_Adapter;
import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class DiemDanhActivity extends AppCompatActivity {
Toolbar toolbar;
ListView listView;
ArrayList<MonHocVang> listMHV=new ArrayList<>();
    MonHocVang_Adapter adapterMHV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh);
        init();
    }

    private void init() {
        toolbar=findViewById(R.id.DiemDanh_toolbar);
        listView=findViewById(R.id.DiemDanh_lvMonHocVang);
    }
    private void getDataMonHocVang(){
        Intent it=getIntent();
        listMHV=it.getParcelableArrayListExtra("dsMonHocVang");
        Log.d("AAA", "getDataMonHocVang: "+listMHV.size());
    }
}