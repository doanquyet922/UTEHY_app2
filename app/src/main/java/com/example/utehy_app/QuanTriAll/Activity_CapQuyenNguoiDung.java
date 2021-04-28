package com.example.utehy_app.QuanTriAll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_CapQuyenNguoiDung extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img,imgDoiChucVu;
    TextView tvMaSV,tvMaLop,tvHoTen,tvChucVu;

    Button btnLuu;

    String chucVu_hientai ="";
    String chucVu_sau ="";

    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cap_quyen_nguoi_dung);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chucVu_hientai.equals(chucVu_sau)){
                    startActivity(new Intent(Activity_CapQuyenNguoiDung.this,QuanLyTaiKhoan_Activity.class));
                }else{
                    mData.child("TaiKhoan").child(tvMaSV.getText().toString()).child("loaiTK").setValue(chucVu_sau).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Activity_CapQuyenNguoiDung.this,"Cấp quyền thành công",Toast.LENGTH_SHORT).show();
                           finish();
                            startActivity(new Intent(Activity_CapQuyenNguoiDung.this,QuanLyTaiKhoan_Activity.class));
                        }
                    });
                }
            }
        });

        imgDoiChucVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chucVu_sau.equals("qtv")){
                    tvChucVu.setText("Thành viên lớp");
                    chucVu_sau = "tv";
                    Toast.makeText(Activity_CapQuyenNguoiDung.this,"Đổi thành THÀNH VIÊN LỚP",Toast.LENGTH_SHORT).show();
                }else{
                    tvChucVu.setText("Cán bộ lớp");
                    chucVu_sau = "qtv";
                    Toast.makeText(Activity_CapQuyenNguoiDung.this,"Đổi thành CÁN BỘ LỚP",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void Init() {
        toolbar=findViewById(R.id.CapQuyen_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cấp quyền");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mData = FirebaseDatabase.getInstance().getReference();

        img = findViewById(R.id.CapQuyen_img);
        imgDoiChucVu = findViewById(R.id.CapQuyen_imgDoi);

        tvMaSV = findViewById(R.id.CapQuyen_tvMaSV);
        tvMaLop = findViewById(R.id.CapQuyen_tvMaLop);
        tvHoTen = findViewById(R.id.CapQuyen_tvHoTen);
        tvChucVu = findViewById(R.id.CapQuyen_tvChucVu);


        btnLuu = findViewById(R.id.CapQuyen_btnLuu);



        //set Avt
        Obj_TaiKhoanQuanLy obj = QuanLyTaiKhoan_Activity.obj_selected;
        if(obj.getGioiTinh().toLowerCase().equals("nam")){
            img.setImageResource(R.drawable.ic_svnam);
        }else{
            img.setImageResource(R.drawable.ic_svnu);
        }

        tvMaSV.setText(obj.getMaSV());
        tvMaLop.setText(obj.getMaLop());
        tvHoTen.setText(obj.getHoTen());


        if(obj.getLoaiTK().equals("qtv")){
            tvChucVu.setText("Cán bộ lớp");
            chucVu_hientai ="qtv";
            chucVu_sau ="qtv";
        }else{
            tvChucVu.setText("Thành viên lớp");
            chucVu_hientai ="tv";
            chucVu_sau = "tv";
        }
    }
}