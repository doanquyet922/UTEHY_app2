package com.example.utehy_app.QuanTriVien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.utehy_app.GuiThongBao.Activity_GuiThongBao;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.R;
import com.example.utehy_app.ThemTaiKhoan.Activity_ThemTaiKhoan;

public class QuanTri_Activity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgGuiThongBao,imgThemTaiKhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_tri);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        imgGuiThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanTri_Activity.this, Activity_GuiThongBao.class));
            }
        });

        imgThemTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanTri_Activity.this, Activity_ThemTaiKhoan.class));
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.QuanTri_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản trị lớp");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanTri_Activity.this, ManHinhChinhActivity.class));
            }
        });

        imgGuiThongBao = findViewById(R.id.QuanTri_imgThongBao);
        imgThemTaiKhoan = findViewById(R.id.QuanTri_imgTaiKhoan);
    }
}