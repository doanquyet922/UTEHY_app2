package com.example.utehy_app.TaoMonHoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.Model.MonHoc;
import com.example.utehy_app.QuanTriAll.Activity_QLTK_ThemTK;
import com.example.utehy_app.QuanTriAll.QuanLyTaiKhoan_Activity;
import com.example.utehy_app.R;
import com.example.utehy_app.ThongBao.TatCaThongBao_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_QuanLyMonHoc extends AppCompatActivity {

    Toolbar toolbar;

    EditText edtTimKiem;
    ListView lv;

    ArrayList<MonHoc> listMH;
    Adapter_MonHoc adapter_monHoc;


    DatabaseReference mData;
    ProgressDialog TempDialog;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__quan_ly_mon_hoc);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();


        Events();

    }

    private void Events() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogCTMH(position);
            }

            private void DialogCTMH(int position) {
                ProgressDialog tem_dialog;
                int i = 0;
                tem_dialog = new ProgressDialog(Activity_QuanLyMonHoc.this);
                tem_dialog.setTitle("Đang xử lý");
                tem_dialog.setCancelable(false);
                tem_dialog.setProgress(i);
                tem_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                tem_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                MonHoc m = listMH.get(position);


                Dialog dialog = new Dialog(Activity_QuanLyMonHoc.this);
                dialog.setContentView(R.layout.dialog_chitiet_monhoc);
                dialog.setCanceledOnTouchOutside(false);

                EditText edtTenMH = dialog.findViewById(R.id.dialogCTMH_edtTenMH);
                EditText edtSoTC = dialog.findViewById(R.id.dialogCTMH_edtSoTC);


                Button btnHuy = dialog.findViewById(R.id.dialogCTMH_btnHuy);
                Button btnLuu = dialog.findViewById(R.id.dialogCTMH_btnLuu);

                edtTenMH.setText(m.getTenMH());
                edtSoTC.setText(m.getSoTC()+"");

                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtTenMH.getText().length()==0){
                            edtTenMH.setError("Thông tin còn trống");
                            edtTenMH.requestFocus();
                            return;
                        }
                        if(edtSoTC.getText().length()==0 || Integer.parseInt(edtSoTC.getText().toString())<1){
                            edtSoTC.setError("Số tín chỉ không hợp lệ");
                            edtSoTC.requestFocus();
                            return;
                        }
                        tem_dialog.show();
                        MonHoc mh = new MonHoc();
                        mh.setMaMH(m.getMaMH());
                        mh.setTenMH(edtTenMH.getText().toString());
                        mh.setSoTC(Integer.parseInt(edtSoTC.getText().toString()));
                        mData.child("monHoc").child(m.getMaMH()).setValue(mh).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    dialog.cancel();
                                    Toast.makeText(Activity_QuanLyMonHoc.this,"Lưu thông tin thành công",Toast.LENGTH_SHORT).show();
                                    tem_dialog.dismiss();
                                    GetDSMH();
                                }else{
                                    tem_dialog.dismiss();
                                    dialog.cancel();
                                    Toast.makeText(Activity_QuanLyMonHoc.this,"Lưu thông tin thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();

            }
        });
    }

    private void Init() {
        toolbar=findViewById(R.id.QLMH_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QUẢN LÝ MÔN HỌC");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TempDialog = new ProgressDialog(Activity_QuanLyMonHoc.this);
        TempDialog.setTitle("Loading ...");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TempDialog.show();

        mData = FirebaseDatabase.getInstance().getReference();

        edtTimKiem = findViewById(R.id.QLMH_edtTimKiem);
        lv = findViewById(R.id.QLMH_lv);

        listMH = new ArrayList<>();
        adapter_monHoc = new Adapter_MonHoc(Activity_QuanLyMonHoc.this,listMH);
        lv.setAdapter(adapter_monHoc);

        TempDialog.show();

        GetDSMH();
    }

    private void GetDSMH(){
        listMH.clear();
        mData.child("monHoc").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    Log.d("getData Môn học","ok");
                    DataSnapshot ds = task.getResult();
                    for (DataSnapshot snapshot : ds.getChildren()){
                        listMH.add(snapshot.getValue(MonHoc.class));
                        adapter_monHoc.notifyDataSetChanged();
                    }

                    TempDialog.dismiss();
                }else{
                    Log.d("getData Môn học","failed");
                    Toast.makeText(Activity_QuanLyMonHoc.this,"Lấy dữ liệu thất bại",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu_qlmonhoc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //..
        switch (item.getItemId()){
            case R.id.menuQLMH_itThem:
                DialogThemMonHoc();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThemMonHoc() {
        Dialog dialog = new Dialog(Activity_QuanLyMonHoc.this);
        dialog.setContentView(R.layout.dialog_tao_monhoc);

        dialog.show();

        EditText edtTenMH = dialog.findViewById(R.id.dialogTaoMH_edtTenMH);
        EditText edtSoTC = dialog.findViewById(R.id.dialogTaoMH_edtSoTC);

        Button btnHuy = dialog.findViewById(R.id.dialogTaoMH_btnHuy);
        Button btnLuu = dialog.findViewById(R.id.dialogTaoMH_btnLuu);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTenMH.getText().length()==0){
                    edtTenMH.setError("Thông tin còn trống");
                    edtTenMH.requestFocus();
                    return;
                }
                if(edtSoTC.getText().length()==0){
                    edtSoTC.setError("Thông tin còn trống");
                    edtSoTC.requestFocus();
                    return;
                }

                if(isTontaiMH(edtTenMH.getText().toString())==true){
                    edtTenMH.setError("Môn học đã tồn tại");
                    edtTenMH.requestFocus();
                    return;
                }


                TempDialog.show();
                SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss", Locale.getDefault());
                String ngay_ht = sdf_ngay.format(new Date());
                String maMH = "MH"+ngay_ht;

                String tenMH = edtTenMH.getText().toString();
                int soTC = Integer.parseInt(edtSoTC.getText().toString());

                MonHoc m = new MonHoc(maMH,tenMH,soTC);

                mData.child("monHoc").child(m.getMaMH()).setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            dialog.cancel();
                            TempDialog.dismiss();
                            Toast.makeText(Activity_QuanLyMonHoc.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                            GetDSMH();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.cancel();
                        Toast.makeText(Activity_QuanLyMonHoc.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        TempDialog.dismiss();
                    }
                });


            }
        });
    }

    private boolean isTontaiMH(String ten){
        for(MonHoc mh :listMH){
            if(mh.getTenMH().toLowerCase().equals(ten.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}