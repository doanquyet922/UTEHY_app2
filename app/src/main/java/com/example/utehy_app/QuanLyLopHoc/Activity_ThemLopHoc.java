package com.example.utehy_app.QuanLyLopHoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utehy_app.Model.Lop;
import com.example.utehy_app.QuanTriAll.Activity_QuanTriALL;
import com.example.utehy_app.R;
import com.example.utehy_app.ThemTaiKhoan.Activity_ThemTaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity_ThemLopHoc extends AppCompatActivity {
    EditText edtMaLop,edtTenLop,edtGVCN;
    Spinner spnKhoa;
    Button btnHuy,btnLuu;

    Toolbar toolbar;

    DatabaseReference mData;

    ProgressDialog TempDialog;
    int i = 0;

    ArrayList<Lop> listLop;

    ArrayList<String> listMaKhoa;
    ArrayAdapter<String> adapterMaKhoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__them_lop_hoc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ThemLopHoc.this,Activity_QuanLyLopHoc.class));
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMaLop.getText().length()==0){
                    edtMaLop.setError("Th??ng tin c??n tr???ng");
                    edtMaLop.requestFocus();
                    return;
                }
                if(isTonTaiMaLop(edtMaLop.getText().toString())==true){
                    edtMaLop.setError("M?? l???p ???? t???n t???i");
                    edtMaLop.requestFocus();
                    return;
                }
                if(edtTenLop.getText().length()==0){
                    edtTenLop.setError("Th??ng tin c??n tr???ng");
                    edtTenLop.requestFocus();
                    return;
                }
                if(edtGVCN.getText().length()==0){
                    edtGVCN.setError("Th??ng tin c??n tr???ng");
                    edtGVCN.requestFocus();
                    return;
                }

                TempDialog.show();

                Lop lh = new Lop(edtMaLop.getText().toString(),edtTenLop.getText().toString(),spnKhoa.getSelectedItem().toString(),edtGVCN.getText().toString());
                mData.child("Lop").child(edtMaLop.getText().toString()).setValue(lh).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Activity_ThemLopHoc.this,"Th??m l???p h???c th??nh c??ng",Toast.LENGTH_SHORT).show();


                            SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String ngay_ht = sdf_ngay.format(new Date());

                            Obj_LichHocTaoMoi lh = new Obj_LichHocTaoMoi();
                            lh.setMaLop(edtMaLop.getText().toString());
                            lh.setMaLichHoc("LH"+edtMaLop.getText().toString());
                            lh.setNgayApDung(ngay_ht);

                            mData.child("LichHoc").child(lh.getMaLichHoc()).setValue(lh).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Activity_ThemLopHoc.this,"Th??m l???ch h???c t???o m???i ok",Toast.LENGTH_SHORT).show();
                                    if(task.isSuccessful()){
                                        for(int i=2;i<9;++i){
                                            Obj_TKBTrongNgay obj = new Obj_TKBTrongNgay();
                                            String thu = "T"+i;
                                            obj.setSANG("Ngh???");
                                            obj.setCHIEU("Ngh???");
                                            mData.child("LichHoc").child(lh.getMaLichHoc()).child("TKB").child(thu).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Log.d("th??m tkb th???","th??nh c??ng");
                                                    }else{
                                                        Log.d("th??m tkb th???","th???t b???i");

                                                    }
                                                }
                                            });
                                        }
                                        TempDialog.dismiss();
                                        startActivity(new Intent(Activity_ThemLopHoc.this, Activity_QuanLyLopHoc.class));
                                    }else{
                                        Toast.makeText(Activity_ThemLopHoc.this,"Th???t b???i",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }else{
                            Toast.makeText(Activity_ThemLopHoc.this,"Th??m l???p h???c th???t b???i",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
    }

    private boolean isTonTaiMaLop(String ma){
        for (Lop l : listLop){
            if(l.getMaLop().toLowerCase().equals(ma.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private void Init() {

        toolbar=findViewById(R.id.ThemLopHoc_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TH??M L???P H???C");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Activity_ThemLopHoc.this,Activity_QuanLyLopHoc.class));

            }
        });

        TempDialog = new ProgressDialog(Activity_ThemLopHoc.this);
        TempDialog.setTitle("??ang th??m l???p h???c");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        edtMaLop = findViewById(R.id.ThemLopHoc_edtMaLop);
        edtTenLop = findViewById(R.id.ThemLopHoc_edtTenLop);
        edtGVCN = findViewById(R.id.ThemLopHoc_TenGVCN);

        spnKhoa = findViewById(R.id.ThemLopHoc_spnKhoa);

        btnHuy = findViewById(R.id.ThemLopHoc_btnHuy);
        btnLuu = findViewById(R.id.ThemLopHoc_btnLuu);

        listMaKhoa = new ArrayList<>();
        listMaKhoa.add("C??ng ngh??? th??ng tin");
        listMaKhoa.add("??i???n - ??i???n t???");
        listMaKhoa.add("C?? kh??");
        listMaKhoa.add("C?? kh?? ?????ng l???c");
        listMaKhoa.add("C??ng ngh??? may & th???i trang");
        listMaKhoa.add("Kinh t???");
        listMaKhoa.add("C??ng ngh??? h??a h???c & m??i tr?????ng");
        listMaKhoa.add("Ngo???i ng???");

        adapterMaKhoa = new ArrayAdapter<>(Activity_ThemLopHoc.this, android.R.layout.simple_list_item_1,listMaKhoa);
        adapterMaKhoa.notifyDataSetChanged();
        spnKhoa.setAdapter(adapterMaKhoa);

        listLop = new ArrayList<>();
        GetDSLH();

    }

    private void GetDSLH(){

        mData = FirebaseDatabase.getInstance().getReference();
        listLop.clear();
        mData.child("Lop").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot ds = task.getResult();
                    for(DataSnapshot snapshot : ds.getChildren()){
                        listLop.add(snapshot.getValue(Lop.class));

                    }
                }
            }
        });
    }
}