package com.example.utehy_app.CaiDatTaiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CaiDatTaiKhoanActivity extends AppCompatActivity {
Button btnThongTin,btnDangXuat,btnDoiMK;
Toolbar toolbar;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat_tai_khoan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
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
        btnThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(CaiDatTaiKhoanActivity.this,ThongTaiKhoanActivity.class);
                startActivity(it);
            }
        });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaLogDoiMK();
            }
        });
    }

    private void DiaLogDoiMK() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_doi_mat_khau);
        TextInputEditText edtMkCu=(TextInputEditText) dialog.findViewById(R.id.da_mkCu);
        TextInputEditText edtMkMoi=(TextInputEditText) dialog.findViewById(R.id.da_mkMoi);
        TextInputEditText edtNhapLaiMk=(TextInputEditText) dialog.findViewById(R.id.da_mkNhapLai);
        Button btnDoiMK=dialog.findViewById(R.id.da_btnLuuNk);


        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkCu=edtMkCu.getText().toString();
                String mkMoi=edtMkMoi.getText().toString();
                String mkNhapLai=edtNhapLaiMk.getText().toString();
                String mk= ManHinhChinhActivity.mk;
                if(!mkCu.equals(mk)){
                    Toast.makeText(CaiDatTaiKhoanActivity.this, "Mật khẩu không chính xác "+mkCu, Toast.LENGTH_SHORT).show();
                }else {
                    if(!mkMoi.equals("") && !mkNhapLai.equals("")){
                    if(!mkMoi.equals(mkNhapLai)){
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Mật khẩu nhập lại không chinh xác", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String maSV=ManHinhChinhActivity.sv_hientai.getMaSV();
                        mData.child("TaiKhoan").child(maSV).child("matKhau").setValue(mkMoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ManHinhChinhActivity.mk=mkMoi;
                                Toast.makeText(CaiDatTaiKhoanActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                    }
                    else {
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        dialog.show();
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
        btnDoiMK=findViewById(R.id.CDTaiKHoan_DoiMK);

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