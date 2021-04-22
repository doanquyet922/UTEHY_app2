package com.example.utehy_app.ThongBao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.utehy_app.R;

public class ThongBaoActivity extends AppCompatActivity {
Toolbar toolbar;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        init();
    }

    private void init() {
        toolbar=findViewById(R.id.ThongBao_toolbar);
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
        listView=findViewById(R.id.ThongBao_listView);
    }
}