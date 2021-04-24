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

public class TatCaThongBao_Activity extends AppCompatActivity {
    DatabaseReference mData;
    Toolbar toolbar;
    ListView lvTB;
    ArrayList<ThongBao> listTB;
    AllThongBao_Adapter allThongBao_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tat_ca_thong_bao);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mData= FirebaseDatabase.getInstance().getReference();


        Init();
    }

    private void Init() {
        toolbar = findViewById(R.id.AllThongBao_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông báo lớp");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
        listTB = new ArrayList<>();
        allThongBao_adapter = new AllThongBao_Adapter(TatCaThongBao_Activity.this,listTB);
        lvTB = findViewById(R.id.AllThongBao_lvTB);
        lvTB.setAdapter(allThongBao_adapter);
        getDataThongBao();
    }

    private void getDataThongBao(){
        Intent it=getIntent();
        String maLop=it.getStringExtra("maLop");
        mData.child("THongBao").orderByChild("maLop").equalTo(maLop).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        ThongBao tb = new ThongBao();
                        tb = ds.getValue(ThongBao.class);
                        listTB.add(tb);
                    }
                    allThongBao_adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TB3","lỗi");
            }
        });
    }
}