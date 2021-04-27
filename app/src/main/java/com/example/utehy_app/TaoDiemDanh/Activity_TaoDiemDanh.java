package com.example.utehy_app.TaoDiemDanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.GuiThongBao.Activity_GuiThongBao;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.CTDiemDanh;
import com.example.utehy_app.Model.DiemDanh;
import com.example.utehy_app.Model.MonHoc;
import com.example.utehy_app.Model.Obj_RowTaoDiemDanh;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
import com.example.utehy_app.R;
import com.example.utehy_app.ThemTaiKhoan.Activity_ThemTaiKhoan;
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
import java.util.Locale;
import java.util.logging.Logger;

public class Activity_TaoDiemDanh extends AppCompatActivity {

    int ngay_save,thang_save,nam_save;
    DatabaseReference mData;
    Toolbar toolbar;
    TextView tvTenMH,tvMaLop,tvNgay;
    ImageView imgChonNgay,imgChonMon;
    ListView lv;

    EditText edtGVDay;
    ArrayList<MonHoc> listMH;
    ArrayList<DiemDanh> listDiemDanhLopHT;

    ArrayAdapter<MonHoc> adapterMH;
    ArrayList<MonHoc> listMH_backup = new ArrayList<>();

    ArrayList<Obj_RowTaoDiemDanh> listRowTaoDiemDanh;
    Adapter_TaoDiemDanh adapter_taoDiemDanh;

    ProgressDialog TempDialog;
    int i =0;

    //Các trường thông tin lưu bảng điểm danh
    String maBDD_ht = "";
    String maLop_ht ="";
    String gvDay_ht ="";
    String maMH_ht ="";
    String ngay_ht ="";

    //Các trường thông tin lưu chi tiết điểm danh
    String maSV_ht ="";
    int isVangMat_ht = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tao_diem_danh);
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_TaoDiemDanh.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year,month,dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            tvNgay.setText(sdf.format(calendar.getTime()));
                            ngay_save = dayOfMonth;
                            thang_save = month;
                            nam_save = year;
                        }
                    },nam,thang,ngay);
                    datePickerDialog.show();
                }

            }
        });

        imgChonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogChonMonHoc();
            }

            private void DialogChonMonHoc() {
                Dialog dialog = new Dialog(Activity_TaoDiemDanh.this);
                dialog.setContentView(R.layout.layout_chonmonhoc);
                dialog.show();

                EditText edtTim = dialog.findViewById(R.id.ChonMonHoc_edtTimMH);
                ListView lv = dialog.findViewById(R.id.ChonMonHoc_lvMH);

                getDSMH();
                lv.setAdapter(adapterMH);



                edtTim.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Search(s);
                    }

                    private void Search(CharSequence s) {
                        listMH.clear();
                        for (MonHoc m : listMH_backup){
                            if(m.getTenMH().contains(s)){
                                listMH.add(m);
                                adapterMH.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvTenMH.setText(listMH.get(position).getTenMH());
                        maMH_ht = listMH.get(position).getMaMH();
                        Toast.makeText(Activity_TaoDiemDanh.this,maMH_ht,Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
            }
        });
    }

    private void Init() {
        mData = FirebaseDatabase.getInstance().getReference();
        listRowTaoDiemDanh = new ArrayList<>();
        adapter_taoDiemDanh = new Adapter_TaoDiemDanh(Activity_TaoDiemDanh.this,listRowTaoDiemDanh);
        lv = findViewById(R.id.TaoDiemDanh_lv);
        lv.setAdapter(adapter_taoDiemDanh);
        imgChonMon = findViewById(R.id.TaoDiemDanh_imgChonMon);

        listMH = new ArrayList<>();
        listDiemDanhLopHT = new ArrayList<>();
        listDiemDanhLopHT = getDSBangDiemDanh();

        adapterMH = new ArrayAdapter<>(Activity_TaoDiemDanh.this, android.R.layout.simple_list_item_1,listMH);

        TempDialog = new ProgressDialog(Activity_TaoDiemDanh.this);
        TempDialog.setTitle("Vui lòng đợi ...");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        toolbar = findViewById(R.id.TaoDiemDanh_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Điểm danh");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_TaoDiemDanh.this, QuanTri_Activity.class));
                finish();
            }
        });

        registerForContextMenu(toolbar);
        getDataSVLop();
        adapter_taoDiemDanh.notifyDataSetChanged();


        tvTenMH = findViewById(R.id.TaoDiemDanh_tvTenMon);
        imgChonMon = findViewById(R.id.TaoDiemDanh_imgChonMon);
        imgChonNgay = findViewById(R.id.TaoDiemDanh_imgChonNgay);
        edtGVDay = findViewById(R.id.TaoDiemDanh_edtGVDay);

        //set dữ liệu mặc định
        tvMaLop = findViewById(R.id.TaoDiemDanh_tvMaLop);
        tvMaLop.setText("Lớp "+ManHinhChinhActivity.sv_hientai.getMaLop());


        SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = sdf_ngay.format(new Date());

        tvNgay = findViewById(R.id.TaoDiemDanh_tvNgayDiemDanh);
        tvNgay.setText(date);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_diemdanh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //..
        switch (item.getItemId()){
            case R.id.menuDiemDanh_itLuu:
                AlertDialog.Builder al = new AlertDialog.Builder(Activity_TaoDiemDanh.this);
                al.setMessage("Xác nhận lưu bảng điểm danh ?");
                al.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isTonTaiBangDiemDanh()==true){
                            Toast.makeText(Activity_TaoDiemDanh.this,"Môn học này hôm nay đã điểm danh rồi",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            return;
                        }else{
                            LuuBangDiemDanh();
                        }
                    }
                });
                al.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                al.create();
                al.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void LuuBangDiemDanh(){
        if(tvTenMH.getText().equals("")){
            Toast.makeText(Activity_TaoDiemDanh.this,"Hãy chọn 1 môn học",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtGVDay.getText().length()==0){
            edtGVDay.setError("Thông tin không được trống");
            edtGVDay.requestFocus();
            return;
        }

        //Dữ liệu bảng điểm danh
        DiemDanh dd = new DiemDanh();
        dd.setMaBangDiemDanh("DD"+maMH_ht+ManHinhChinhActivity.sv_hientai.getMaLop()+ngay_save+""+thang_save+""+nam_save);
        dd.setMaLop(ManHinhChinhActivity.sv_hientai.getMaLop());
        dd.setGvDay(edtGVDay.getText().toString());
        dd.setMaMH(maMH_ht);
        dd.setNgay(tvNgay.getText().toString());
        Log.d("AAA", "LuuBangDiemDanh: "+dd.toString());
        String s = dd.getMaBangDiemDanh();

        mData.child("DiemDanh").child(s).setValue(dd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Activity_TaoDiemDanh.this,"thêm bảng điểm danh thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Activity_TaoDiemDanh.this,"thêm bảng điểm danh thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Dữ liệu bảng chi tiết điểm danh
        ArrayList<CTDiemDanh> listCTDD = new ArrayList<>();
        for(Obj_RowTaoDiemDanh obj : listRowTaoDiemDanh){
            CTDiemDanh ct = new CTDiemDanh();
            ct.setMaBangDiemDanh(dd.getMaBangDiemDanh());
            ct.setMaSV(obj.getMaSV());
            if(obj.isVangMat()==true){
                ct.setVangMat(0);
            }else{
                ct.setVangMat(1);
            }

            listCTDD.add(ct);
        }


        for(CTDiemDanh c : listCTDD){
            TempDialog.show();
            mData.child("CTDiemDanh").push().setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("themct "+c.getMaSV(),"ok");
                    }else{
                        Log.d("themct "+c.getMaSV(),"failed");
                    }
                }
            });
        }
        TempDialog.dismiss();

    }


    //hàm check môn học hiện tại của lớp đã điểm danh hôm nay chưa
    private boolean isTonTaiBangDiemDanh(){
        String ngay = tvNgay.getText().toString();

        for (DiemDanh dd : listDiemDanhLopHT){
            if(dd.getMaMH().equals(maMH_ht)){
                if(dd.getNgay().equals(ngay)){
                    return true;
                }
            }
        }
        return false;
    }

    //hàm lấy về danh sách sinh viên lớp hiện tại
    private void getDataSVLop(){
        String maLop = ManHinhChinhActivity.sv_hientai.getMaLop();
        mData.child("SinhVien").orderByChild("maLop").equalTo(maLop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("snap size",snapshot.getValue(SinhVien.class).toString());
                for (DataSnapshot ds : snapshot.getChildren()){
                    SinhVien sv = ds.getValue(SinhVien.class);
                    listRowTaoDiemDanh.add(new Obj_RowTaoDiemDanh(sv.getMaSV(),sv.getHoTen(),true));
                    adapter_taoDiemDanh.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //hàm lấy về danh sách các môn học
    private void getDSMH(){
        listMH.clear();
        mData.child("monHoc").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        MonHoc mh = ds.getValue(MonHoc.class);
                        listMH.add(mh);
                        listMH_backup.add(mh);
                    }
                    adapterMH.notifyDataSetChanged();

                }
            }
        });
    }

    //hàm lấy về các bảng điểm danh của lớp hiện tại
    private ArrayList<DiemDanh> getDSBangDiemDanh(){
        ArrayList<DiemDanh> listBDD = new ArrayList<>();
        mData.child("DiemDanh").orderByChild("maLop").equalTo(ManHinhChinhActivity.sv_hientai.getMaLop()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()){
                    listBDD.add(sn.getValue(DiemDanh.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DSDiemDanh","failed");
            }
        });
        return listBDD;
    }
}