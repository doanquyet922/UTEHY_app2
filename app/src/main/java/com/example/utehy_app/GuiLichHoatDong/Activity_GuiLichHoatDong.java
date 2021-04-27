package com.example.utehy_app.GuiLichHoatDong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.Obj_RowTaoDiemDanh;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.SuKien;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
import com.example.utehy_app.R;
import com.example.utehy_app.TaoDiemDanh.Activity_TaoDiemDanh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Activity_GuiLichHoatDong extends AppCompatActivity {
    DatabaseReference mData;
    Toolbar toolbar;
    TextView tvToiLop,tvNgay;
    EditText edtND;
    Button btnGui;
    ImageView imgChonNgay;

    ArrayList<SuKien> listSKFromDB;

    int ngay_s,thang_s,nam_s;

    String maLop ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gui_lich_hoat_dong);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Init();
        Events();
    }

    private void Events() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        Date date = new Date();


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tvNgay.setText(sdf.format(date));

        //lay ngay hien tai
        SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd");
        ngay_s = Integer.parseInt(sdf_ngay.format(date));

        //lay thang hien tai
        SimpleDateFormat sdf_thang = new SimpleDateFormat("MM");
        thang_s = Integer.parseInt(sdf_thang.format(date));

        //lay nam hien tai
        SimpleDateFormat sdf_nam = new SimpleDateFormat("yyyy");
        nam_s = Integer.parseInt(sdf_nam.format(date));



        imgChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_GuiLichHoatDong.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year,month,dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            tvNgay.setText(sdf.format(calendar.getTime()));
                            ngay_s = dayOfMonth;
                            thang_s = month;
                            nam_s = year;
                        }
                    },nam,thang,ngay);
                    datePickerDialog.show();
                }

            }
        });

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtND.getText().length()==0){
                    edtND.setError("Thông tin không được trống");
                    edtND.requestFocus();
                    return;
                }

                SuKien sk = new SuKien();
                sk.setMaLop(maLop);
                sk.setNgay(tvNgay.getText().toString());
                sk.setNoiDung(edtND.getText().toString());

                String lhd_key = "LHD"+maLop+""+ngay_s+""+thang_s+""+nam_s;


                    mData.child("HoatDong").child(lhd_key).setValue(sk).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Activity_GuiLichHoatDong.this,"Thêm thành công !",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_GuiLichHoatDong.this, QuanTri_Activity.class));
                            }
                        }
                    });

            }
        });
    }

    //Hàm check ngày vừa nhập đã có nội dung nào chưa
    private boolean isTonTaiLich(){
        for (SuKien s : listSKFromDB){
            if(s.getMaLop().equals(maLop)){
                if(s.getNgay().equals(tvNgay.getText().toString())){
                    return true;        // đã có lịch
                }
            }
        }

        return false; //chưa có
    }

    //Hàm lấy về các lịch hoạt động của lớp hiện tại
    private ArrayList<SuKien> getSuKienLop(){
        String maLop = ManHinhChinhActivity.sv_hientai.getMaLop();

        ArrayList<SuKien> listSK = new ArrayList<>();
        mData.child("HoatDong").orderByChild("maLop").equalTo(maLop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        SuKien s = ds.getValue(SuKien.class);
                        listSK.add(s);
                    }
                }else{
                    Toast.makeText(Activity_GuiLichHoatDong.this,"không có hoạt động",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity_GuiLichHoatDong.this,"lấy hoạt động thất bại",Toast.LENGTH_SHORT).show();

            }
        });

        return listSK;
    }

    private void Init() {
        toolbar = findViewById(R.id.GuiLichHD_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gửi lịch hoạt động");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Activity_GuiLichHoatDong.this,QuanTri_Activity.class));
            }
        });



        mData = FirebaseDatabase.getInstance().getReference();

        tvToiLop = findViewById(R.id.GuiLichHD_toiMaLop);
        tvNgay = findViewById(R.id.GuiLichHD_tvNgay);

        edtND = findViewById(R.id.GuiLichHD_edtNoiDung);
        btnGui = findViewById(R.id.GuiLichHD_btnThem);

        imgChonNgay = findViewById(R.id.GuiLichHD_imgChonNgay);

        maLop = ManHinhChinhActivity.sv_hientai.getMaLop();
        tvToiLop.setText("tới "+maLop);


        listSKFromDB = new ArrayList<>();
        listSKFromDB = getSuKienLop();
    }
}