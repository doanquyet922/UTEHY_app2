package com.example.utehy_app.CaiDatTaiKhoan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.GuiLichHoatDong.Activity_GuiLichHoatDong;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.R;
import com.example.utehy_app.calendar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThongTaiKhoanActivity extends AppCompatActivity {
    DatabaseReference mData;
    TextInputEditText edtHoten,edtDiachi;
    TextView tvGioiTinh,tvNamSinh;
    Button btnSuaTT;
    Toolbar toolbar;
    ImageButton btnNamSinh,btn_GioiTinh;
    int ngay_s,thang_s,nam_s;
    SinhVien sv= ManHinhChinhActivity.sv_hientai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tai_khoan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        init();
        getTT();
    }

    private void getTT() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        Date date = new Date();

        //lay ngay hien tai
        SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd");
        ngay_s = Integer.parseInt(sdf_ngay.format(date));

        //lay thang hien tai
        SimpleDateFormat sdf_thang = new SimpleDateFormat("MM");
        thang_s = Integer.parseInt(sdf_thang.format(date));

        //lay nam hien tai
        SimpleDateFormat sdf_nam = new SimpleDateFormat("yyyy");
        nam_s = Integer.parseInt(sdf_nam.format(date));


        edtHoten.setText(sv.getHoTen());
        edtDiachi.setText(sv.getDiaChi());
        tvGioiTinh.setText(sv.getGioiTinh());
        tvNamSinh.setText(sv.getNamSinh());
        getSupportActionBar().setTitle(sv.getHoTen()+"-"+sv.getMaSV()+"-"+sv.getMaLop());
        btnNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ThongTaiKhoanActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year,month,dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            tvNamSinh.setText(sdf.format(calendar.getTime()));
                            ngay_s = dayOfMonth;
                            thang_s = month;
                            nam_s = year;
                        }
                    },nam,thang,ngay);
                    datePickerDialog.show();}
            }
        });
        btn_GioiTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogGioiTinh();
            }
        });
        btnSuaTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                AlertDialog.Builder builder=new AlertDialog.Builder(ThongTaiKhoanActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn sửa thông tin?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SuaTT();
                    }
                });
                builder.create();
                builder.show();
            }
        });

    }
    private void SuaTT(){
        String maSV=sv.getMaSV();
        String hoTen=edtHoten.getText().toString();
        String gioiTinh=tvGioiTinh.getText().toString();
        String namSinh=tvNamSinh.getText().toString();
        String diaChi=edtDiachi.getText().toString();
        String maLop=sv.getMaLop();
        if(!hoTen.equals("") && !diaChi.equals("")) {
            SinhVien svUpdated = new SinhVien(maSV, hoTen, gioiTinh, namSinh, diaChi, maLop);
            String key = mData.child("SinhVien").push().getKey();
            Map<String, Object> postValues = svUpdated.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/SinhVien/" + key, postValues);
//            childUpdates.put("/user-posts/" + maSV + "/" + key, postValues);
            mData.updateChildren(childUpdates);
        }
        else {
            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
    }
    private void DialogGioiTinh() {
        Context context;
        Dialog dialog=new Dialog(ThongTaiKhoanActivity.this);
        dialog.setContentView(R.layout.custom_dialog_gioi_tinh);
        RadioButton rdNam=dialog.findViewById(R.id.rdNam);
        RadioButton rdNu=dialog.findViewById(R.id.rdNu);
        Button btnOk=dialog.findViewById(R.id.btnOK);
        if(sv.getGioiTinh().equals("Nam")){
            rdNam.setChecked(true);
            rdNu.setChecked(false);
        }else {
            rdNu.setChecked(true);
            rdNam.setChecked(false);
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdNam.isChecked()){
                    tvGioiTinh.setText("Nam");
                    dialog.dismiss();
                }
                else {
                    tvGioiTinh.setText("Nữ");
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    private void init() {
        toolbar=findViewById(R.id.TTTK_ToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin tài khoản");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtHoten=findViewById(R.id.TTTK_edtHoTen);
        edtDiachi=findViewById(R.id.TTTK_edtDiaChi);
        tvGioiTinh=findViewById(R.id.TTTK_tvGioiTinh);
        tvNamSinh=findViewById(R.id.TTTK_tvNamSinh);
        btnNamSinh=findViewById(R.id.TTTK_btnNamSinh);
        btn_GioiTinh=findViewById(R.id.TTTK_btnGioiTinh);
        btnSuaTT=findViewById(R.id.TTTK_btnSuaThongTin);
    }
}