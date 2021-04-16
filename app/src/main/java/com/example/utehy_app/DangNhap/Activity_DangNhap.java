package com.example.utehy_app.DangNhap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_DangNhap extends AppCompatActivity {
    public static DatabaseReference mData;
EditText edtDNUser,edtPass;
Button btnDN;
CheckBox cbLuuMK;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dangnhap);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        init();
        sharedPreferences=getSharedPreferences("dataLogin",MODE_PRIVATE);//Khởi tạo 1 file
        // lấy giá trị sharedPreferences
        edtDNUser.setText(sharedPreferences.getString("MaSV",""));
        edtPass.setText(sharedPreferences.getString("MatKhau",""));
        cbLuuMK.setChecked(sharedPreferences.getBoolean("checked",false));
        events();
    }

    private void events() {
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CheckDangNhap();
            }
        });

    }

    private void init() {
        edtDNUser=(EditText) findViewById(R.id.edtDNUser);
        edtPass=(EditText) findViewById(R.id.edtPass);
        btnDN=findViewById(R.id.btnDN);
        cbLuuMK=findViewById(R.id.cbLuuMK);

    }
    private void CheckDangNhap(){

        String MaSV=edtDNUser.getText().toString();
        String MatKhau=edtPass.getText().toString();
        mData.child("SinhViens").startAt("101185","MaLop").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("AAA", "onDataChange: "+snapshot.getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mData.child("TaiKhoan").child(MaSV).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("AAA", "onComplete: "+task.getResult().getValue());
                    TaiKhoan tk=task.getResult().getValue(TaiKhoan.class);
                    if(tk!=null){
                    if(tk.getMatKhau()!=null ) {
                        if (tk.getMatKhau().equals(MatKhau)) {
                            Toast.makeText(Activity_DangNhap.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("MaSV",MaSV);
                            editor.commit();
                            if(cbLuuMK.isChecked()){
//                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("MatKhau",MatKhau);
                                editor.putBoolean("checked",true);
                                editor.commit();
                            }
                            else {
                                editor.remove("MatKhau");
                                editor.remove("checked");
                                editor.commit();
                            }
                        } else {
                            Toast.makeText(Activity_DangNhap.this, "Đăng nhập không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Activity_DangNhap.this, "Đăng nhập không thành công.", Toast.LENGTH_SHORT).show();
                    }
                    }else {
                        Toast.makeText(Activity_DangNhap.this, "Đăng nhập không thành công.", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });
    }
}