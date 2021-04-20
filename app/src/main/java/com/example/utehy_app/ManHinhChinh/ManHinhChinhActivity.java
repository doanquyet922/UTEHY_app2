package com.example.utehy_app.ManHinhChinh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utehy_app.BangTin.BangTinActivity;
import com.example.utehy_app.CustomAdapter.MonHocVang_Adapter;
import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.DiemDanh.DiemDanhActivity;
import com.example.utehy_app.Model.CTDiemDanh;
import com.example.utehy_app.Model.DiemDanh;
import com.example.utehy_app.Model.MonHoc;
import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManHinhChinhActivity extends AppCompatActivity {
    DatabaseReference mData;
    ImageView imgBangTin,imgDiemDanh;
    TaiKhoan taiKhoan;
    SinhVien sinhVien;
    TextView tvHoTen;

    ListView lvMHV;
    ArrayList<MonHocVang> listMHV;
    MonHocVang_Adapter adapterMHV;
    ArrayList<CTDiemDanh>arrCT=new ArrayList<>();
    ArrayList<DiemDanh>arrDD=new ArrayList<>();
    ArrayList<MonHoc>arrMH=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        Init();
        Events();
        getDataCTDiemDanh();
        getDataDienDanh();
        getDataMonHoc();
        getDataMonHocVang();
    }

    private void Events() {
        getUser();
        imgBangTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhChinhActivity.this, BangTinActivity.class));
            }
        });
        imgDiemDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ManHinhChinhActivity.this, DiemDanhActivity.class);
                it.putExtra("MaSV",taiKhoan.getMaSV());
                startActivity(it);
            }
        });

    }
private void getUser(){
        Intent it=getIntent();
        taiKhoan= (TaiKhoan) it.getSerializableExtra("TaiKhoan");
        if (taiKhoan!=null && !taiKhoan.getMaSV().equals("")){
            mData.child("SinhVien").child(taiKhoan.getMaSV()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    sinhVien=snapshot.getValue(SinhVien.class);
                    if(sinhVien !=null && sinhVien.getHoTen()!=null){
                        tvHoTen.setText(sinhVien.getHoTen());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("AAA", "onCancelled: "+error.getMessage());
                }
            });

    }

}
    private void Init() {
        imgBangTin = findViewById(R.id.MHC_imgBangTin);
        imgDiemDanh=findViewById(R.id.MHC_imgDiemDanh);
        tvHoTen=findViewById(R.id.MHC_tvHoTen);
        lvMHV = findViewById(R.id.MHC_lvMonHocVang);
        listMHV = new ArrayList<>();
        adapterMHV = new MonHocVang_Adapter(ManHinhChinhActivity.this,listMHV);
        lvMHV.setAdapter(adapterMHV);

    }

    private void getDataMonHocVang() {
//        listMHV.clear();
//        listMHV.add(new MonHocVang("10118456","MH3","Tiếng anh chuyên ngành",3,2));
//        listMHV.add(new MonHocVang("10118456","MH1","Cơ sở dữ liệu",4,2));
//        listMHV.add(new MonHocVang("10118456","MH2","Giải tích",2,1));
//        listMHV.add(new MonHocVang("10118456","MH4","Thể chất 1",1,1));
//
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<DiemDanh> dsDiemDanh=new ArrayList<>();
                for(int i=0;i<arrCT.size();i++){
                    CTDiemDanh ct=arrCT.get(i);
                    Log.d("BBB", "CHitiet: "+ct.toString());
                    for (int j=0;j<arrDD.size();j++){
                        DiemDanh dd=arrDD.get(j);
                        Log.d("BBB", "DIemDanh: "+dd.toString());
                        if(ct.getMaBangDiemDanh().equals(dd.getMaBangDiemDanh())){
                            dsDiemDanh.add(dd);
                        }
                    }
                }
                ArrayList<MonHoc>dsMonHoc=new ArrayList<>();
                for(int i=0;i<dsDiemDanh.size();i++){
                    DiemDanh dd=dsDiemDanh.get(i);
                    for (int j=0;j<arrMH.size();j++){
                        MonHoc mh=arrMH.get(j);
                        if(dd.getMaMH().equals(mh.getMaMH())){
                            Log.d("BBB", "MOnHoc: "+mh.getTenMH());
                            dsMonHoc.add(mh);
                        }
                    }
                }
                Log.d("BBB", "size: "+dsMonHoc.size());
                if (dsMonHoc.size()>0){
                    Log.d("BBB", "run: ");
                    handler.removeCallbacks(this);
                }
                else {
                handler.postDelayed(this,500);}
            }
        },500);



    }
    private void getDataCTDiemDanh(){
        mData.child("CTDiemDanh").orderByChild("maSV").equalTo(taiKhoan.getMaSV()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        CTDiemDanh ct=ds.getValue(CTDiemDanh.class);
                        arrCT.add(ct);
                    }
//                    Log.d("BBB", "CTDiemDanh: "+snapshot.getValue()+"\nSize:"+arrCT.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDataDienDanh(){
        mData.child("DiemDanh").orderByChild("maBangDiemDanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("BBB", "onDataChange: "+snapshot.getValue());
                if (snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        DiemDanh dd=ds.getValue(DiemDanh.class);
                        arrDD.add(dd);
                    }
//                    Log.d("BBB", "DiemDanh: "+snapshot.getValue()+"\nSize:"+arrDD.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDataMonHoc(){
        mData.child("monHoc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        MonHoc mh=ds.getValue(MonHoc.class);
                        arrMH.add(mh);
                    }
//                    Log.d("BBB", "MonHoc: "+snapshot.getValue()+"\nSize:"+arrMH.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}