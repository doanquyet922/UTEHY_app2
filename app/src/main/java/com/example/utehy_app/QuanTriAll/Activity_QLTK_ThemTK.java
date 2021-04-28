package com.example.utehy_app.QuanTriAll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.Lop;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
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

public class Activity_QLTK_ThemTK extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtMaSV,edtHoTen,edtDiaChi;
    Spinner spnGioiTinh,spnLoaiTK,spnLop;
    TextView tvNgaySinh;
    Button btnThem;
    ImageView imgChonNgay;

    DatabaseReference mData;
    int nam_chon =0;

    //list dữ liệu cho spinner
    ArrayList<String> listGioiTinh;
    ArrayAdapter<String> adapterGT;
    ArrayList<String> listLoaiTK;
    ArrayAdapter<String> adapterLoaiTK;
    ArrayList<String> listLop;
    ArrayAdapter<String> adapterLop;


    ArrayList<SinhVien> listsv = new ArrayList<>();

    //Temp dialog
    ProgressDialog TempDialog;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__qltk__them_t_k);
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_QLTK_ThemTK.this, new DatePickerDialog.OnDateSetListener() {
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


        //Sự kiện thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMaSV.getText().length()==0){
                    edtMaSV.setError("Thông tin còn trống");
                    edtMaSV.requestFocus();
                    return;
                }
                if(isExistSV(edtMaSV.getText().toString())==true){
                    edtMaSV.setError("Mã sinh viên đã tồn tại");
                    edtMaSV.requestFocus();
                    return;
                }
                if(edtHoTen.getText().length()==0){
                    edtHoTen.setError("Thông tin còn trống");
                    edtHoTen.requestFocus();
                    return;
                }
                if(edtDiaChi.getText().length()==0){
                    edtDiaChi.setError("Thông tin còn trống");
                    edtDiaChi.requestFocus();
                    return;
                }
                if(tvNgaySinh.getText().length()==0 || CheckNamSinh(nam_chon)==false){
                    AlertDialog.Builder al = new AlertDialog.Builder(Activity_QLTK_ThemTK.this);
                    al.setMessage("Thông tin ngày sinh không hợp lệ");
                    al.show();
                    al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    return;
                }

                TempDialog.show();
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
                String maLop = spnLop.getSelectedItem().toString();

                //gọi hàm thêm 1 tìa khoản vào db
                TaiKhoan t = new TaiKhoan();
                t.setMaSV(maSV_add);
                t.setMatKhau(matKhau);
                if(spnLoaiTK.getSelectedItem().toString().equals("Cán bộ lớp")){
                    t.setLoaiTK("qtv");
                }else{
                    t.setLoaiTK("tv");
                }

                mData.child("TaiKhoan").child(maSV_add).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Activity_QLTK_ThemTK.this,"Thêm tài khoản thành công",Toast.LENGTH_SHORT).show();
                        ThemSinhVien();
                    }

                    private void ThemSinhVien() {
                        SinhVien sv = new SinhVien(maSV_add,tenSV,gt,ngaySinh,diaChi,maLop);
                        mData.child("SinhVien").child(maSV_add).setValue(sv).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Activity_QLTK_ThemTK.this,"Thêm sv thành công",Toast.LENGTH_SHORT).show();
                                TempDialog.dismiss();
                                edtMaSV.setText("");
                                edtHoTen.setText("");
                                edtDiaChi.setText("");
                                tvNgaySinh.setText("");
                            }
                        });
                    }
                });

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
        toolbar=findViewById(R.id.QLTK_ThemTaiKhoan_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo tài khoản sinh viên");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TempDialog = new ProgressDialog(Activity_QLTK_ThemTK.this);
        TempDialog.setTitle("Đang thêm tài khoản...");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));



        mData = FirebaseDatabase.getInstance().getReference();

        listsv = QuanLyTaiKhoan_Activity.listSV_get;

        //ánh xạ các view
        edtMaSV = findViewById(R.id.QLTK_ThemTaiKhoan_edtMaSV);
        edtHoTen = findViewById(R.id.QLTK_ThemTaiKhoan_edtHoTen);
        edtDiaChi = findViewById(R.id.QLTK_ThemTaiKhoan_edtDiaChi);

        spnGioiTinh = findViewById(R.id.QLTK_ThemTaiKhoan_spnGioiTinh);
        spnLoaiTK = findViewById(R.id.QLTK_ThemTaiKhoan_spnLoaiTK);

        listLop = new ArrayList<>();
        adapterLop = new ArrayAdapter<>(Activity_QLTK_ThemTK.this, android.R.layout.simple_list_item_1,listLop);
        spnLop = findViewById(R.id.QLTK_ThemTaiKhoan_spnLop);
        spnLop.setAdapter(adapterLop);
        GetDSMaLop();

        tvNgaySinh = findViewById(R.id.QLTK_ThemTaiKhoan_tvNgaySinh);

        btnThem = findViewById(R.id.QLTK_ThemTaiKhoan_btnTaoTK);

        imgChonNgay = findViewById(R.id.QLTK_ThemTaiKhoan_imgChonNgay);

        //dữ liệu spinner
        listGioiTinh = new ArrayList<>();
        listGioiTinh.add("Nam");
        listGioiTinh.add("Nữ");
        adapterGT = new ArrayAdapter<>(Activity_QLTK_ThemTK.this, android.R.layout.simple_list_item_1,listGioiTinh);
        spnGioiTinh.setAdapter(adapterGT);

        listLoaiTK = new ArrayList<>();
        listLoaiTK.add("Thành viên lớp");
        listLoaiTK.add("Cán bộ lớp");
        adapterLoaiTK = new ArrayAdapter<>(Activity_QLTK_ThemTK.this, android.R.layout.simple_list_item_1,listLoaiTK);
        spnLoaiTK.setAdapter(adapterLoaiTK);

    }

    //Hàm check sinh viên đã tồn tại chưa
    private boolean isExistSV(String ma){
        for(SinhVien s : listsv){
            if(s.getMaSV().toLowerCase().equals(ma.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    //hàm lấy về danh sách lớp
    private void GetDSMaLop(){
        mData.child("Lop").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Lop l = ds.getValue(Lop.class);
                        listLop.add(l.getMaLop());
                    }
                    adapterLop.notifyDataSetChanged();
                }
            }
        });
    }


}