package com.example.utehy_app.QuanTriAll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.utehy_app.GuiThongBao.Activity_GuiThongBao;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
import com.example.utehy_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class QuanTriAll_GuiThongBao extends AppCompatActivity {
    Toolbar toolbar;

    TextView tvMaLopGui;
    ImageView imgChonAnh;
    EditText edtND;
    Button btnGui;
    Spinner spnMaLop;

    ArrayList<String> listMaLop;
    ArrayAdapter<String> adapterMaLop;

    ProgressDialog TempDialog;
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    boolean isHasImage = false;

    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    String linkAnh ="";
    int i =0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_tri_all__gui_thong_bao);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
    }

    private void Init() {
        toolbar = findViewById(R.id.QuanTriAll_GuiTB_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gửi thông báo");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanTriAll_GuiThongBao.this, Activity_QuanTriALL.class));
                finish();
            }
        });


        //Temp dialog
        TempDialog = new ProgressDialog(QuanTriAll_GuiThongBao.this);
        TempDialog.setTitle("Đang gửi thông báo");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


    }
}