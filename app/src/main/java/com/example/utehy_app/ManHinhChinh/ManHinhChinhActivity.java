package com.example.utehy_app.ManHinhChinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utehy_app.BangTin.BangTinActivity;
import com.example.utehy_app.R;

public class ManHinhChinhActivity extends AppCompatActivity {
    ImageView imgBangTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        imgBangTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhChinhActivity.this, BangTinActivity.class));
            }
        });
    }

    private void Init() {
        imgBangTin = findViewById(R.id.MHC_imgBangTin);
    }
}