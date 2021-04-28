package com.example.utehy_app.QuanTriAll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.utehy_app.R;
import com.google.firebase.database.DatabaseReference;

public class Activity_QLTK_ThemTK extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtMaSV,edtHoTen;
    Spinner spnGioiTinh,spnLoaiTK,edtDiaChi;
    TextView tvNgaySinh;
    Button btnThem;

    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__qltk__them_t_k);

        Init();
    }

    private void Init() {

    }
}