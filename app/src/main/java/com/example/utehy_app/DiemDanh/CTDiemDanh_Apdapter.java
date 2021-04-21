package com.example.utehy_app.DiemDanh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.utehy_app.Model.CTDiemDanh;
import com.example.utehy_app.Model.DiemDanh;
import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.Model.Row_CTDiemDanh;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class CTDiemDanh_Apdapter extends BaseAdapter {
    Activity context;
    ArrayList<Row_CTDiemDanh> arr;

    public CTDiemDanh_Apdapter() {
    }

    public CTDiemDanh_Apdapter(Activity context, ArrayList<Row_CTDiemDanh> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_row_ctdiemdanh, null);
        TextView tvNgayDiemDanh=row.findViewById(R.id.rowCTDiemDanh_tvNgayDiemDanh);
        TextView tvDiemDanh=row.findViewById(R.id.rowCTDiemDanh_tvDiemDanh);
        tvNgayDiemDanh.setText(arr.get(position).getNgayDiemDanh());
        if(arr.get(position).getVangMat()==0){
            tvDiemDanh.setText("Vắng");
            tvDiemDanh.setTextColor(Color.RED);
        }
        else {
            tvDiemDanh.setText("Có mặt");
            tvDiemDanh.setTextColor(Color.parseColor("#009688"));
        }
        return row;
    }
}
