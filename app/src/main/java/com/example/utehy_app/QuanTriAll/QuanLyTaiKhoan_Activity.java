package com.example.utehy_app.QuanTriAll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.R;
import com.example.utehy_app.TaoDiemDanh.Activity_TaoDiemDanh;
import com.example.utehy_app.ThongBao.TatCaThongBao_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuanLyTaiKhoan_Activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtTim;
    Spinner spnLoaiTK;
    ListView lv;

    public static Obj_TaiKhoanQuanLy obj_selected;
    int index_selected = -1;
    //Temp dialog
    ProgressDialog TempDialog;
    int i = 0;

    //data firebase
    DatabaseReference mData;

    //list Sinh viên
    public static ArrayList<SinhVien> listSV_get;
    //list tài khoản
    ArrayList<TaiKhoan> listTK_get;
    //list đối tượng lv
    ArrayList<Obj_TaiKhoanQuanLy> listTKQL_get;
    ArrayList<Obj_TaiKhoanQuanLy> listTKQL_get_backup;
    //list các tên chức vụ
    ArrayList<String> listChucVu;
    ArrayAdapter<String> adapterChucVu;

    Adapter_QuanLyTaiKhoan adapter_quanLyTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        this.registerForContextMenu(lv);
    }

    private void Events() {
        //            spnLoaiTK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:
//                        listTKQL_get.clear();
//                        listTKQL_get = listTKQL_get_backup;
//                        adapter_quanLyTaiKhoan.notifyDataSetChanged();
//                        Toast.makeText(QuanLyTaiKhoan_Activity.this,""+listTKQL_get.size(),Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        listTKQL_get.clear();
//                        for (Obj_TaiKhoanQuanLy o : listTKQL_get_backup){
//                            if(o.getLoaiTK().equals("qtv")){
//                                listTKQL_get.add(o);
//                            }
//                        }
//                        Toast.makeText(QuanLyTaiKhoan_Activity.this,""+listTKQL_get.size(),Toast.LENGTH_SHORT).show();
//
//                        adapter_quanLyTaiKhoan.notifyDataSetChanged();
//                        break;
//                    case 2:
//                        listTKQL_get.clear();
//                        for (Obj_TaiKhoanQuanLy o : listTKQL_get_backup){
//                            if(o.getLoaiTK().equals("tv")){
//                                listTKQL_get.add(o);
//                            }
//                        }
//                        Toast.makeText(QuanLyTaiKhoan_Activity.this,""+listTKQL_get.size(),Toast.LENGTH_SHORT).show();
//
//                        adapter_quanLyTaiKhoan.notifyDataSetChanged();
//                        break;
//                }
//            }
//
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
////                listTKQL_get.clear();
////                listTKQL_get = listTKQL_get_backup;
////                adapter_quanLyTaiKhoan.notifyDataSetChanged();
////                Toast.makeText(QuanLyTaiKhoan_Activity.this,""+listTKQL_get.size(),Toast.LENGTH_SHORT).show();
//            }
//        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index_selected = position;
                return false;
            }
        });
        edtTim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Search(s);
            }

            private void Search(CharSequence s) {
                listTKQL_get.clear();
                for(Obj_TaiKhoanQuanLy o : listTKQL_get_backup){
                    if(o.getHoTen().toLowerCase().contains(s) || o.getGioiTinh().toLowerCase().toLowerCase().contains(s) || o.getMaLop().contains(s)){
                        listTKQL_get.add(o);
                    }
                }
                adapter_quanLyTaiKhoan.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void Init() {
        toolbar=findViewById(R.id.QLTK_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QUẢN LÝ TÀI KHOẢN SV");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtTim = findViewById(R.id.QLTK_edtTimKiem);

        mData = FirebaseDatabase.getInstance().getReference();

        listChucVu = new ArrayList<>();
        listChucVu.add("Tất cả");
        listChucVu.add("Cán bộ lớp");
        listChucVu.add("Thành viên");



        adapterChucVu = new ArrayAdapter<>(QuanLyTaiKhoan_Activity.this, android.R.layout.simple_list_item_1,listChucVu);

        spnLoaiTK = findViewById(R.id.QLTK_spinLoaiTK);
        spnLoaiTK.setAdapter(adapterChucVu);

        //Temp Dialog
        TempDialog = new ProgressDialog(QuanLyTaiKhoan_Activity.this);
        TempDialog.setTitle("Đang tải dữ liệu tài khoản...");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TempDialog.show();

        lv = findViewById(R.id.QLTK_lv);

        //get ds tài khoản sinh viên
        listSV_get = new ArrayList<>();
        listTK_get = new ArrayList<>();
        listTKQL_get = new ArrayList<>();
        listTKQL_get_backup = new ArrayList<>();

        adapter_quanLyTaiKhoan = new Adapter_QuanLyTaiKhoan(QuanLyTaiKhoan_Activity.this,listTKQL_get);
        lv.setAdapter(adapter_quanLyTaiKhoan);
        GetDSSV();
    }


    //lấy về danh sách thông tin sinh viên
    private void GetDSSV(){
        listSV_get.clear();
        mData.child("SinhVien").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        SinhVien sv = ds.getValue(SinhVien.class);
                        if(!sv.getMaSV().equals("admin")){
                            listSV_get.add(sv);
                            Log.d("getSV: ",sv.toString());
                        }


                    }
                    GetDSTK();

                }
            }
        });
    }

    //lấy về danh sách thông tin tài khoản
    private void GetDSTK(){
        listTK_get.clear();
        mData.child("TaiKhoan").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        TaiKhoan tk = ds.getValue(TaiKhoan.class);
                        if(!tk.getMaSV().equals("admin")){
                            listTK_get.add(tk);
                            Log.d("getTK: ",tk.toString());
                        }


                    }

                    GetDSTKQL();
                }
            }
        });
    }


    private void GetDSTKQL(){
        listTKQL_get.clear();
        for(SinhVien sv : listSV_get){
            String maSV = sv.getMaSV();
            String maLop = sv.getMaLop();
            String hoTen = sv.getHoTen();
            String gt = sv.getGioiTinh();
            String loaiTK ="";
            for(TaiKhoan tk : listTK_get){
                if(tk.getMaSV().toLowerCase().equals(maSV.toLowerCase())){
                    loaiTK = tk.getLoaiTK();
                }
            }

            listTKQL_get.add(new Obj_TaiKhoanQuanLy(maSV,maLop,hoTen,gt,loaiTK));
            listTKQL_get_backup.add(new Obj_TaiKhoanQuanLy(maSV,maLop,hoTen,gt,loaiTK));
        }
        TempDialog.dismiss();
        adapter_quanLyTaiKhoan.notifyDataSetChanged();
        Events();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_qltk, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //..
        switch (item.getItemId()){
            case R.id.menuQLTK_itThem:
                startActivity(new Intent(QuanLyTaiKhoan_Activity.this,Activity_QLTK_ThemTK.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_qltk, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.context_menu_itCapQuyen:
                obj_selected = listTKQL_get.get(index_selected);
                startActivity(new Intent(QuanLyTaiKhoan_Activity.this,Activity_CapQuyenNguoiDung.class));
                break;
            case R.id.context_menu_itXoaSV:
                AlertDialog.Builder al = new AlertDialog.Builder(QuanLyTaiKhoan_Activity.this);
                al.setMessage("Xóa sinh viên này ?");
                al.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maSV = listTKQL_get.get(index_selected).getMaSV();
                        mData.child("TaiKhoan").child(maSV).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    listTKQL_get.remove(index_selected);
                                    adapter_quanLyTaiKhoan.notifyDataSetChanged();
                                    Toast.makeText(QuanLyTaiKhoan_Activity.this,"Xóa thành công tài khoản",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(QuanLyTaiKhoan_Activity.this,"Xóa thất bại tài khoản",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        mData.child("SinhVien").child(maSV).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(QuanLyTaiKhoan_Activity.this,"Xóa thành công sinh viên",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(QuanLyTaiKhoan_Activity.this,"Xóa thất bại sv",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                al.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                al.create();
                al.show();

                break;

        }
        return true;
    }
}