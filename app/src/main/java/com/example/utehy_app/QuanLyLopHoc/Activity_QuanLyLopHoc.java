package com.example.utehy_app.QuanLyLopHoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.utehy_app.Model.Lop;
import com.example.utehy_app.QuanTriAll.Activity_CapQuyenNguoiDung;
import com.example.utehy_app.QuanTriAll.Activity_QLTK_ThemTK;
import com.example.utehy_app.QuanTriAll.QuanLyTaiKhoan_Activity;
import com.example.utehy_app.R;
import com.example.utehy_app.TaoMonHoc.Activity_QuanLyMonHoc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_QuanLyLopHoc extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtTimKiem;
    ListView lv;

    ArrayList<Lop> listLop;
    Adapter_LopHoc adapter_lopHoc;

    DatabaseReference mData;

    ProgressDialog TempDialog;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__quan_ly_lop_hoc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
    }

    private void Init() {
        toolbar=findViewById(R.id.QLLH_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QUẢN LÝ LỚP HỌC");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TempDialog = new ProgressDialog(Activity_QuanLyLopHoc.this);
        TempDialog.setTitle("Đang tải");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        edtTimKiem = findViewById(R.id.QLLH_edtTimKiem);
        lv = findViewById(R.id.QLLH_lv);

        listLop = new ArrayList<>();
        adapter_lopHoc = new Adapter_LopHoc(Activity_QuanLyLopHoc.this,listLop);
        lv.setAdapter(adapter_lopHoc);

        GetDSLH();


    }

    private void GetDSLH(){
        TempDialog.show();
        mData = FirebaseDatabase.getInstance().getReference();
        listLop.clear();
        mData.child("Lop").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot ds = task.getResult();
                    for(DataSnapshot snapshot : ds.getChildren()){
                        listLop.add(snapshot.getValue(Lop.class));
                        adapter_lopHoc.notifyDataSetChanged();
                    }
                    TempDialog.dismiss();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_qllop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuQLLop_itThem:
                startActivity(new Intent(Activity_QuanLyLopHoc.this,Activity_ThemLopHoc.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}