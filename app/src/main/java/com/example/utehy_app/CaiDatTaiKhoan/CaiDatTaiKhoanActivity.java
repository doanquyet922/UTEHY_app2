package com.example.utehy_app.CaiDatTaiKhoan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.R;

public class CaiDatTaiKhoanActivity extends AppCompatActivity {
Button btnThongTin,btnDangXuat;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat_tai_khoan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        events();
    }

    private void events() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogDangXuat();
            }
        });
    }

    private void init() {
        toolbar=findViewById(R.id.CDTaiKHoan_ToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cài Đặt");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnThongTin=findViewById(R.id.CDTaiKHoan_ThongTin);
        btnDangXuat=findViewById(R.id.CDTaiKHoan_DangXuat);
    }
    private void DialogDangXuat(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Xác nhận đăng xuất");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it=new Intent(CaiDatTaiKhoanActivity.this, Activity_DangNhap.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                finish();
            }
        });
        builder.create();
        builder.show();
    }

}