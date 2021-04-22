package com.example.utehy_app.ThongBao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.utehy_app.Model.ThongBao;
import com.example.utehy_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThongBaoActivity extends AppCompatActivity {
Toolbar toolbar;
ListView listView;
ArrayList<ThongBao> arrAllTBao=new ArrayList<>();
ThongBaoAdapter thongBaoAdapter;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        init();
        getALLThongBao();
    }

    private void init() {
        toolbar=findViewById(R.id.ThongBao_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông báo");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView=findViewById(R.id.ThongBao_listView);

    }
    private void getALLThongBao(){
        Intent it=getIntent();
        String maLop=it.getStringExtra("maLop");
        mData.child("THongBao").orderByChild("maLop").equalTo(maLop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrAllTBao.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    ThongBao tb=ds.getValue(ThongBao.class);
                    arrAllTBao.add(tb);
                    thongBaoAdapter=new ThongBaoAdapter(ThongBaoActivity.this,arrAllTBao);
                    listView.setAdapter(thongBaoAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}