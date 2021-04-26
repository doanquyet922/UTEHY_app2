package com.example.utehy_app.ThemTaiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.MainActivity;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.Model.ThongBao;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
import com.example.utehy_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Activity_ThemTaiKhoan extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseReference mData;
    //Các view
    EditText edtMaSV,edtHoTen,edtDiaChi;
    Spinner spnGioiTinh;
    TextView tvNgaySinh;
    Button btnTaoTK;
    ImageView imgChonNgay;
    ArrayList<String> listGT;
    ArrayAdapter adapterGT;

    boolean isTonTaiMaSV;

    int nam_chon =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__them_tai_khoan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();


        Events();
    }

    private void Events() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        imgChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_ThemTaiKhoan.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year,month,dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            tvNgaySinh.setText(sdf.format(calendar.getTime()));
                            nam_chon = year;


                        }
                    },nam,thang,ngay);
                    datePickerDialog.show();
                }

            }
        });

        //sự kiện click thêm sinh viên
        btnTaoTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cld = calendar.getInstance();
                isTonTaiMaSV(edtMaSV.getText().toString());
                if(edtHoTen.getText().toString().length()==0){
                    edtHoTen.setError("Thông tin không được trống");
                    edtHoTen.requestFocus();
                    return;
                }
                String gioiTinh = spnGioiTinh.getSelectedItem().toString();
                if(gioiTinh.equals("Chọn giới tính")){
                    Toast.makeText(Activity_ThemTaiKhoan.this,"Vui lòng chọn giới tính",Toast.LENGTH_LONG).show();
                    return;
                }
                if(tvNgaySinh.getText().toString().length()==0 || CheckNamSinh(nam_chon)==false){
                    Toast.makeText(Activity_ThemTaiKhoan.this,"Năm sinh còn trống hoặc không hợp lệ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(edtDiaChi.getText().length()==0){
                    edtDiaChi.setError("Thông tin còn trống");
                    edtDiaChi.requestFocus();
                    return;
                }
                Toast.makeText(Activity_ThemTaiKhoan.this,"ok",Toast.LENGTH_LONG).show();

                //thông tin tài khoản
                String tenTK = edtMaSV.getText().toString();
                String matKhau = "utehy@123";
                String loaiTK = "tv";

                //thông tin sv
                String maSV_add = edtMaSV.getText().toString();
                String tenSV = edtHoTen.getText().toString();
                String gt = spnGioiTinh.getSelectedItem().toString();
                String ngaySinh = tvNgaySinh.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String maLop = ManHinhChinhActivity.sv_hientai.getMaLop();

                //gọi hàm thêm 1 tìa khoản vào db
                TaiKhoan t = new TaiKhoan();
                t.setMaSV(maSV_add);
                t.setMatKhau(matKhau);
                t.setLoaiTK(loaiTK);

                mData.child("TaiKhoan").child(maSV_add).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Activity_ThemTaiKhoan.this,"Thêm tài khoản thành công",Toast.LENGTH_SHORT).show();
                        ThemSinhVien();
                    }

                    private void ThemSinhVien() {
                        SinhVien sv = new SinhVien(maSV_add,tenSV,gt,ngaySinh,diaChi,maLop);
                        mData.child("SinhVien").child(maSV_add).setValue(sv).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Activity_ThemTaiKhoan.this,"Thêm sv thành công",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_ThemTaiKhoan.this,QuanTri_Activity.class));
                                finish();
                            }
                        });
                    }
                });


            }
        });
    }

    private void isTonTaiMaSV(String maSV){

        mData.child("SinhVien").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        SinhVien s = ds.getValue(SinhVien.class);
                        if(s.getMaSV().equals(maSV)){
                            edtMaSV.setError("Mã sinh viên đã tồn tại");
                            edtMaSV.setText("");
                            edtMaSV.requestFocus();
                            return;
                        }
                    }
                }else{
                    Toast.makeText(Activity_ThemTaiKhoan.this,"thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean CheckNamSinh(int ns){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(date);

        int nam = Integer.parseInt(year);
        if((nam-ns)<17){
            return false;
        }else{
            return true;
        }
    }

    private void Init() {
        mData = FirebaseDatabase.getInstance().getReference();
        toolbar = findViewById(R.id.ThemTaiKhoan_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo tài khoản thành viên lớp");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ThemTaiKhoan.this, QuanTri_Activity.class));
            }
        });

        edtMaSV = findViewById(R.id.ThemTaiKhoan_edtMaSV);
        edtHoTen = findViewById(R.id.ThemTaiKhoan_edtHoTen);
        edtDiaChi = findViewById(R.id.ThemTaiKhoan_edtDiaChi);

        spnGioiTinh = findViewById(R.id.ThemTaiKhoan_spnGioiTinh);

        tvNgaySinh = findViewById(R.id.ThemTaiKhoan_tvNgaySinh);

        btnTaoTK = findViewById(R.id.ThemTaiKhoan_btnTaoTK);

        imgChonNgay = findViewById(R.id.ThemTaiKhoan_imgChonNgay);

        listGT = new ArrayList<>();
        listGT.add("Chọn giới tính");
        listGT.add("Nam");
        listGT.add("Nữ");


        adapterGT = new ArrayAdapter<String>(Activity_ThemTaiKhoan.this, android.R.layout.simple_list_item_1,listGT);
        spnGioiTinh.setAdapter(adapterGT);

    }
}