package com.example.utehy_app.QuanLyLichHoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyLichHoc_Activity extends AppCompatActivity {

    Toolbar toolbar;
    GridView gridView;

    DatabaseReference mData;

    ArrayList<ThoiKhoaBieu> listTKB;
    Adapter_QuanLyLichHoc adapter_quanLyLichHoc;

    int post_click = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_lich_hoc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                post_click = position;
                Dialog_SuaTKB();
                //Toast.makeText(QuanLyLichHoc_Activity.this,listTKB.get(position).getThu(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.QuanLyLichHoc_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lịch học "+ ManHinhChinhActivity.sv_hientai.getMaLop());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                startActivity(new Intent(QuanTri_Activity.this, ManHinhChinhActivity.class));

            }
        });

        mData = FirebaseDatabase.getInstance().getReference();

        gridView = findViewById(R.id.QuanLyLichHoc_gridView);

        listTKB = new ArrayList<>();
        adapter_quanLyLichHoc = new Adapter_QuanLyLichHoc(QuanLyLichHoc_Activity.this,listTKB);
        gridView.setAdapter(adapter_quanLyLichHoc);


        getThoiKhoaBieu();



    }

    private void getThoiKhoaBieu(){
        String maLop = ManHinhChinhActivity.sv_hientai.getMaLop();
        String maLH = "LH"+maLop;

        listTKB.clear();

        for(int i=2;i<9;++i){
            ThoiKhoaBieu t = new ThoiKhoaBieu();
            String thu = "T"+i;
            t.setThu(thu);

            mData.child("LichHoc").child(maLH).child("TKB").child(thu).child("Sang").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot ds = task.getResult();
                    t.setSang(ds.getValue().toString());
                    Log.d("tkb", "onComplete: " +ds.getValue().toString());

                    //

                    mData.child("LichHoc").child(maLH).child("TKB").child(thu).child("Chieu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            DataSnapshot ds = task.getResult();
                            t.setChieu(ds.getValue().toString());
                            Log.d("tkb", "onComplete: " +ds.getValue().toString());

                            listTKB.add(t);
                            adapter_quanLyLichHoc.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }

    private void Dialog_SuaTKB(){
        Dialog dialog = new Dialog(QuanLyLichHoc_Activity.this);
        dialog.setContentView(R.layout.dialog_sualichhoc);

        TextView tvThu = dialog.findViewById(R.id.dialogSuaLH_tvThu);

        EditText edtSang = dialog.findViewById(R.id.dialogSuaLH_edtSang);
        EditText edtChieu = dialog.findViewById(R.id.dialogSuaLH_edtChieu);
        EditText edtPhongSang = dialog.findViewById(R.id.dialogSuaLH_edtPhongSang);
        EditText edtPhongChieu = dialog.findViewById(R.id.dialogSuaLH_edtPhongChieu);

        Spinner spnTietSang = dialog.findViewById(R.id.dialogSuaLH_spnTietSang);
        Spinner spntietChieu = dialog.findViewById(R.id.dialogSuaLH_spnTietChieu);

        Button btnLuu= dialog.findViewById(R.id.dialogSuaLH_btnLuu);

        ArrayList<String> listTietHocSang = new ArrayList<>();
        listTietHocSang.add("Chọn tiết");
        listTietHocSang.add("1234");
        listTietHocSang.add("12345");
        ArrayAdapter<String> adapterSang = new ArrayAdapter<>(QuanLyLichHoc_Activity.this, android.R.layout.simple_list_item_1,listTietHocSang);
        spnTietSang.setAdapter(adapterSang);

        ArrayList<String> listTietHocChieu = new ArrayList<>();
        listTietHocChieu.add("Chọn tiết");
        listTietHocChieu.add("7890");
        listTietHocChieu.add("78901");
        listTietHocChieu.add("8901");
        ArrayAdapter<String> adapterChieu = new ArrayAdapter<>(QuanLyLichHoc_Activity.this, android.R.layout.simple_list_item_1,listTietHocChieu);
        spntietChieu.setAdapter(adapterChieu);


        ThoiKhoaBieu tkb = listTKB.get(post_click);

        tvThu.setText(getThu(tkb.getThu()));

        //bóc tách dữ liệu tiết học buổi sáng
        String tietSang = tkb.getSang().toString();

        if(tietSang.toLowerCase().equals("nghỉ")){
            edtSang.setText("");
            spnTietSang.setSelection(0);
            edtPhongSang.setText("");
        }else{
            String tenMH_sang = tietSang.substring(0,tietSang.indexOf("-"));
            String tietHoc_sang = tietSang.substring(tietSang.indexOf("-")+1,tietSang.lastIndexOf("-")).trim();
            String phongHoc_sang = tietSang.substring(tietSang.lastIndexOf("-")+1,tietSang.lastIndexOf(""));

            edtSang.setText(tenMH_sang);
            if(tietHoc_sang.equals("1234")){
                spnTietSang.setSelection(1);
            }else{
                spnTietSang.setSelection(2);
            }

            edtPhongSang.setText(phongHoc_sang);
        }

        //bóc tách dữ liệu tiết học buổi chiều
        String tietChieu = tkb.getChieu().toString();

        if(tietChieu.toLowerCase().equals("nghỉ")){
            edtChieu.setText("");
            spntietChieu.setSelection(0);
            edtPhongChieu.setText("");
        }else {
            String tenMH_chieu = tietChieu.substring(0, tietChieu.indexOf("-"));
            String tietHoc_chieu = tietChieu.substring(tietChieu.indexOf("-") + 1, tietChieu.lastIndexOf("-")).trim();
            String phongHoc_chieu = tietChieu.substring(tietChieu.lastIndexOf("-") + 1, tietChieu.lastIndexOf(""));

            edtChieu.setText(tenMH_chieu);

            if(tietHoc_chieu.equals("7890")){
                spntietChieu.setSelection(1);
            }else if(tietHoc_chieu.equals("78901")){
                spntietChieu.setSelection(2);
            }else{
                spntietChieu.setSelection(3);
            }

            edtPhongChieu.setText(phongHoc_chieu);
        }



        dialog.show();

    }
    private String getThu(String thu){
        String thu_return = "";
        switch (thu){
            case "T2":
                thu_return = "Thứ 2";
                break;
            case "T3":
                thu_return = "Thứ 3";
                break;
            case "T4":
                thu_return = "Thứ 4";
                break;
            case "T5":
                thu_return = "Thứ 5";
                break;
            case "T6":
                thu_return = "Thứ 6";
                break;
            case "T7":
                thu_return = "Thứ 7";
                break;
            case "T8":
                thu_return = "Chủ nhật";
                break;
        }

        return thu_return;
    }

}