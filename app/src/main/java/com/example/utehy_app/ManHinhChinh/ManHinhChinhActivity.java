package com.example.utehy_app.ManHinhChinh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utehy_app.BangTin.BangTinActivity;
import com.example.utehy_app.CongThongTin.CongThongTin_Activity;
import com.example.utehy_app.CustomAdapter.MonHocVang_Adapter;
import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.DiemDanh.DiemDanhActivity;
import com.example.utehy_app.Eclass.Eclass_Activity;
import com.example.utehy_app.HoatDong.HoatDong_Activity;
import com.example.utehy_app.Model.CTDiemDanh;
import com.example.utehy_app.Model.DiemDanh;
import com.example.utehy_app.Model.MonHoc;
import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.Model.TinTucUTEHY;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
import com.example.utehy_app.R;
import com.example.utehy_app.ThongBao.TatCaThongBao_Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ManHinhChinhActivity extends AppCompatActivity {
    DatabaseReference mData;
    ImageView imgBangTin,imgDiemDanh,imgThongBao,imgQuanTri,imgHoatDong,imgCongTT,imgEclass;
    TaiKhoan taiKhoan;
    SinhVien sinhVien;
    TextView tvHoTen,tvQuanTri;
    TextView tvLichHoc;
    ListView lvTinTuc;

    public static SinhVien sv_hientai;
    ListView lvMHV;

    ArrayList<TinTucUTEHY> listTinTucUTEHY;
    Adapter_GridView_TinTuc adapter_gridView_tinTuc;

    ArrayList<MonHocVang> listMHV;
    MonHocVang_Adapter adapterMHV;
    public static ArrayList<CTDiemDanh>arrCT_Of_MaSV=new ArrayList<>();
    public static ArrayList<DiemDanh>arrALL_DD=new ArrayList<>();
    ArrayList<MonHoc>arrALL_MH=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mData= FirebaseDatabase.getInstance().getReference();
        Init();
        Events();
        getDataCTDiemDanh();
        getDataDienDanh();
        getDataMonHoc();
        getDataMonHocVang();

        getLichHocHomNay();
        getDSTinTucUTEHY();
    }

    private void Events() {
        getUser();

        imgQuanTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhChinhActivity.this, QuanTri_Activity.class));
            }
        });
        imgBangTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhChinhActivity.this, BangTinActivity.class));
            }
        });


        imgThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ManHinhChinhActivity.this, TatCaThongBao_Activity.class);
                it.putExtra("maLop",sinhVien.getMaLop());
                startActivity(it);
            }
        });


        imgHoatDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhChinhActivity.this, HoatDong_Activity.class));
            }
        });

        imgCongTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhChinhActivity.this, CongThongTin_Activity.class));
            }
        });

        imgEclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhChinhActivity.this, Eclass_Activity.class));
            }
        });


    }
    private void getUser(){
        Intent it=getIntent();
        taiKhoan= (TaiKhoan) it.getSerializableExtra("TaiKhoan");
        if(!taiKhoan.getLoaiTK().equals("qtv")){
            imgQuanTri.setVisibility(View.GONE);
            tvQuanTri.setVisibility(View.GONE);
        }else{
            imgQuanTri.setVisibility(View.VISIBLE);
            tvQuanTri.setVisibility(View.VISIBLE);
        }
        if (taiKhoan!=null && !taiKhoan.getMaSV().equals("")){
            mData.child("SinhVien").child(taiKhoan.getMaSV()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    sinhVien=snapshot.getValue(SinhVien.class);
                    if(sinhVien !=null && sinhVien.getHoTen()!=null){
                        tvHoTen.setText(sinhVien.getHoTen());
                        Log.d("sinhvien_get",sinhVien.toString());
                        sv_hientai = snapshot.getValue(SinhVien.class);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("AAA", "onCancelled: "+error.getMessage());
                }
            });
    }

}
    private void Init() {
        imgBangTin = findViewById(R.id.MHC_imgBangTin);
        imgDiemDanh=findViewById(R.id.MHC_imgDiemDanh);
        imgCongTT=findViewById(R.id.MHC_imgCongTT);
        imgEclass=findViewById(R.id.MHC_imgEclass);
        imgThongBao=findViewById(R.id.MHC_imgThongBao);
        tvHoTen=findViewById(R.id.MHC_tvHoTen);
        lvMHV = findViewById(R.id.MHC_lvMonHocVang);
        listMHV = new ArrayList<>();
        adapterMHV = new MonHocVang_Adapter(ManHinhChinhActivity.this,listMHV);
        lvMHV.setAdapter(adapterMHV);

        tvLichHoc = findViewById(R.id.MHC_tvLichHoc);
        tvQuanTri = findViewById(R.id.MHC_tvQuanTri);

        lvTinTuc = findViewById(R.id.MHC_lvTinTuc);
        listTinTucUTEHY = new ArrayList<>();
        adapter_gridView_tinTuc = new Adapter_GridView_TinTuc(ManHinhChinhActivity.this,listTinTucUTEHY);
        lvTinTuc.setAdapter(adapter_gridView_tinTuc);

        imgQuanTri = findViewById(R.id.MHC_imgQuanTri);
        imgHoatDong = findViewById(R.id.MHC_imgHoatDong);

    }

    private void getLichHocHomNay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sinhVien.getMaLop()!=null){
                    String url_base = "https://utehyapp-default-rtdb.firebaseio.com/LichHoc/";
                    String maLichHocLop = "LH"+sinhVien.getMaLop();
                    String path = url_base+maLichHocLop+"/TKB";
                    Log.d("path",path);
                    Date date = new Date();
                    String date_string = date.toString();
                    String thu_cut = date_string.substring(0,3);
                    String thu = getThuHienTai(thu_cut);
                    DatabaseReference data = FirebaseDatabase.getInstance().getReferenceFromUrl(path);
                    data.child(thu).child("Sang").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot!=null) {
                                tvLichHoc.append("SÁNG : " + snapshot.getValue().toString() + "\n\n");
                            }else{
                                tvLichHoc.append("SÁNG : nghỉ");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    data.child(thu).child("Chieu").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot!=null) {
                                tvLichHoc.append("CHIỀU : " + snapshot.getValue().toString() + "\n\n");
                            }else{
                                tvLichHoc.append("CHIỀU : nghỉ");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    handler.removeCallbacks(this::run);

                }else {
                    handler.postDelayed(this,500);
                }

            }
        },500);





    }

    private String getThuHienTai(String date){
        String ngay = "";
        switch (date){
            case "Mon":
                ngay = "T2";
                break;
            case "Tue":
                ngay = "T3";
                break;
            case "Wed":
                ngay = "T4";
                break;
            case "Thu":
                ngay = "T5";
                break;
            case "Fri":
                ngay = "T6";
                break;
            case "Sat":
                ngay = "T7";
                break;
            case "Sun":
                ngay = "T8";
                break;
        }
        return ngay;
    }

    private void getDataMonHocVang() {


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<DiemDanh> dsDiemDanh=new ArrayList<>();
                for(int i=0;i<arrCT_Of_MaSV.size();i++){
                    CTDiemDanh ct=arrCT_Of_MaSV.get(i);
                    for (int j=0;j<arrALL_DD.size();j++){
                        DiemDanh dd=arrALL_DD.get(j);
                        if(ct.getMaBangDiemDanh().equals(dd.getMaBangDiemDanh())){
                            dsDiemDanh.add(dd);
                        }
                    }
                }
                ArrayList<MonHoc>dsMonHoc=new ArrayList<>();
                for(int i=0;i<dsDiemDanh.size();i++){
                    DiemDanh dd=dsDiemDanh.get(i);
                    for (int j=0;j<arrALL_MH.size();j++){
                        MonHoc mh=arrALL_MH.get(j);
                        if(dd.getMaMH().equals(mh.getMaMH())){
                            if (!dsMonHoc.contains(mh)){

                                dsMonHoc.add(mh);
                            }

                        }
                    }
                }
                if (dsMonHoc.size()>0){
                    ArrayList<MonHocVang>dsMonHocVang=new ArrayList<>();
                    for(MonHoc mh: dsMonHoc){
                        String maSV=taiKhoan.getMaSV();
                        String maMH=mh.getMaMH();
                        String tenMH=mh.getTenMH();
                        int soTC=mh.getSoTC();
                        int soBuoiVang=0;
                        for (DiemDanh dd:dsDiemDanh){
                            if(mh.getMaMH().equals(dd.getMaMH())){
                                for(CTDiemDanh ct:arrCT_Of_MaSV){
                                    if(dd.getMaBangDiemDanh().equals(ct.getMaBangDiemDanh()) && ct.getVangMat()==0)
                                        soBuoiVang++;
                                }
                            }
                        }
                        MonHocVang mhv=new MonHocVang(maSV,maMH,tenMH,soTC,soBuoiVang);
                        dsMonHocVang.add(mhv);
                    }
                    imgDiemDanh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it=new Intent(ManHinhChinhActivity.this,DiemDanhActivity.class);
                            it.putExtra("dsMonHocVang",dsMonHocVang);
                            startActivity(it);
                        }
                    });
                    ArrayList<MonHocVang> tmp=new ArrayList<>();
                    for(MonHocVang mhv:dsMonHocVang){
                        if (mhv.getSoBuoiNghi()>0){
                            tmp.add(mhv);
                        }
                    }
                    adapterMHV=new MonHocVang_Adapter(ManHinhChinhActivity.this,tmp);
                    lvMHV.setAdapter(adapterMHV);
                    handler.removeCallbacks(this);
                }
                else {
                handler.postDelayed(this,500);}
            }
        },500);



    }
    private void getDataCTDiemDanh(){
        mData.child("CTDiemDanh").orderByChild("maSV").equalTo(taiKhoan.getMaSV()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        CTDiemDanh ct=ds.getValue(CTDiemDanh.class);
                        arrCT_Of_MaSV.add(ct);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDataDienDanh(){
        mData.child("DiemDanh").orderByChild("maBangDiemDanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("BBB", "onDataChange: "+snapshot.getValue());
                if (snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        DiemDanh dd=ds.getValue(DiemDanh.class);
                        arrALL_DD.add(dd);
                    }
//                    Log.d("BBB", "DiemDanh: "+snapshot.getValue()+"\nSize:"+arrDD.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDataMonHoc(){
        mData.child("monHoc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        MonHoc mh=ds.getValue(MonHoc.class);
                        arrALL_MH.add(mh);
                    }
//                    Log.d("BBB", "MonHoc: "+snapshot.getValue()+"\nSize:"+arrMH.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDSTinTucUTEHY(){
        mData.child("TinTucUTEHY").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot!=null){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        TinTucUTEHY tt=ds.getValue(TinTucUTEHY.class);
                        listTinTucUTEHY.add(tt);
                    }

                    adapter_gridView_tinTuc.notifyDataSetChanged();

                    Log.d("tintucute",listTinTucUTEHY.size()+"");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}