package com.example.utehy_app.DiemDanh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.CTDiemDanh;
import com.example.utehy_app.Model.DiemDanh;
import com.example.utehy_app.Model.Row_CTDiemDanh;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class CTDiemDanhActivity extends AppCompatActivity {
ListView listView;
Toolbar toolbar;
    ArrayList<Row_CTDiemDanh> row_ctDiemDanhArrayList=new ArrayList<>();
CTDiemDanh_Apdapter ctDiemDanh_apdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_diem_danh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    private void init() {
        toolbar=findViewById(R.id.CTDiemDanh_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView=findViewById(R.id.CTDiemDanh_listView);
        ViewListView();
    }
    private void ViewListView(){
        Intent it=getIntent();
        String maMH=it.getStringExtra("maMH");
        Log.d("BBB", "ViewListView: "+maMH);
        ArrayList<DiemDanh> dsDiemDanh_MonHoc=new ArrayList<>();
        for(DiemDanh dd: ManHinhChinhActivity.arrALL_DD){
            if(maMH.equals(dd.getMaMH())){
                dsDiemDanh_MonHoc.add(dd);
            }
        }
        for (DiemDanh dd:dsDiemDanh_MonHoc){
            for(CTDiemDanh ct:ManHinhChinhActivity.arrCT_Of_MaSV){
                if(dd.getMaBangDiemDanh().equals(ct.getMaBangDiemDanh())){
                    String ngay=dd.getNgay();
                    int vangMat=ct.getVangMat();
                    row_ctDiemDanhArrayList.add(new Row_CTDiemDanh(ngay,vangMat));
                }
            }
        }
        ctDiemDanh_apdapter=new CTDiemDanh_Apdapter(CTDiemDanhActivity.this,row_ctDiemDanhArrayList);
        listView.setAdapter(ctDiemDanh_apdapter);

    }
}