package com.example.utehy_app.QuanTriAll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.R;
import com.example.utehy_app.TaoMonHoc.Activity_QuanLyMonHoc;

public class Activity_QuanTriALL extends AppCompatActivity {
    ImageView imgTaiKhoan,imgThoat,imgThongBao,imgMonHoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__quan_tri_all);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        Init();
        Events();
    }

    private void Events() {
        imgTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_QuanTriALL.this,QuanLyTaiKhoan_Activity.class));
            }
        });
        imgThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Activity_QuanTriALL.this, Activity_DangNhap.class));
            }
        });
        imgThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Activity_QuanTriALL.this, QuanTriAll_GuiThongBao.class));
            }
        });
        imgMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Activity_QuanTriALL.this, Activity_QuanLyMonHoc.class));
            }
        });
    }

    private void Init() {
        imgTaiKhoan = findViewById(R.id.QTA_imgTaiKhoan);
        imgThoat = findViewById(R.id.QTA_imgThoat);
        imgThongBao = findViewById(R.id.QTA_imgThongBao);
        imgMonHoc = findViewById(R.id.QTA_imgMonHoc);
    }
}