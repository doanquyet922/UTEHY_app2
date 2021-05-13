package com.example.utehy_app.QuanTriAll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.R;
import com.example.utehy_app.ThongBao.TatCaThongBao_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_DoiMatKhau_QTV extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtMKCu,edtMKMoi,edtXacNhan;
    Button btnXacNhan;

    DatabaseReference mData;

    ProgressDialog TempDialog;
    int i=0;

    TaiKhoan tk_current = Activity_DangNhap.tk_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__doi_mat_khau__qtv);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMKCu.getText().toString().length()==0){
                    edtMKCu.setError("Thông tin còn trống");
                    edtMKCu.requestFocus();
                    return;
                }
                if(!edtMKCu.getText().toString().equals(tk_current.getMatKhau())){
                    edtMKCu.setError("Mật khẩu cũ không đúng");
                    edtMKCu.requestFocus();
                    return;
                }
                if(edtMKMoi.getText().toString().length()<6){
                    edtMKMoi.setError("Mật khẩu có độ dài trên 6 kí tự");
                    edtMKMoi.requestFocus();
                    return;
                }
                if(edtXacNhan.getText().toString().length()<6){
                    edtXacNhan.setError("Mật khẩu có độ dài trên 6 kí tự");
                    edtXacNhan.requestFocus();
                    return;
                }
                if(!edtMKMoi.getText().toString().equals(edtXacNhan.getText().toString())){
                    edtMKMoi.setError("Mật khẩu không trùng khớp");
                    edtMKMoi.requestFocus();
                    edtXacNhan.setError("Mật khẩu không trùng khớp");
                    edtXacNhan.requestFocus();
                    return;
                }

                TempDialog.show();
                mData.child("TaiKhoan").child(tk_current.getMaSV()).child("matKhau").setValue(edtMKMoi.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            TempDialog.dismiss();
                            Toast.makeText(Activity_DoiMatKhau_QTV.this,"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Activity_DoiMatKhau_QTV.this,Activity_QuanTriALL.class));

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        TempDialog.dismiss();
                        Toast.makeText(Activity_DoiMatKhau_QTV.this,"Đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void Init() {
        toolbar=findViewById(R.id.QuanTriAll_DoiMatKhau_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đổi mật khẩu");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtMKCu = findViewById(R.id.QuanTriAll_DoiMatKhau_edtMKCu);
        edtMKMoi = findViewById(R.id.QuanTriAll_DoiMatKhau_edtMKMoi);
        edtXacNhan = findViewById(R.id.QuanTriAll_DoiMatKhau_edtMKXacNhan);

        btnXacNhan =findViewById(R.id.QuanTriAll_DoiMatKhau_btnXacNhan);

        mData = FirebaseDatabase.getInstance().getReference();

        TempDialog = new ProgressDialog(Activity_DoiMatKhau_QTV.this);
        TempDialog.setTitle("Loading");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


    }


}