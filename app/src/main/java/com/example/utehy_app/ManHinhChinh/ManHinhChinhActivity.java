package com.example.utehy_app.ManHinhChinh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utehy_app.BangTin.BangTinActivity;
import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.DiemDanh.DiemDanhActivity;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManHinhChinhActivity extends AppCompatActivity {
    DatabaseReference mData;
    ImageView imgBangTin,imgDiemDanh;
    TaiKhoan taiKhoan;
    SinhVien sinhVien;
    TextView tvHoTen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        Init();
        Events();
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

                }
            });

    }

}
    private void Init() {
        imgBangTin = findViewById(R.id.MHC_imgBangTin);
        imgDiemDanh=findViewById(R.id.MHC_imgDiemDanh);
        tvHoTen=findViewById(R.id.MHC_tvHoTen);
    }
}