package com.example.utehy_app.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class MonHocVang_Adapter extends BaseAdapter {
    Activity context;
    ArrayList<MonHocVang> listMHV;
    @Override
    public int getCount() {
        return listMHV.size();
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
        View row = inflater.inflate(R.layout.custom_row_monhocvang, null);

        ProgressBar prgb = row.findViewById(R.id.rowMonHocVang_progressBar);
        TextView tvSoBuoi = row.findViewById(R.id.rowMonHocVang_tvSoBuoi);
        TextView tvTenMH = (TextView) row.findViewById(R.id.rowMonHocVang_tvTenMonHoc);

        MonHocVang mhv = listMHV.get(position);

        int soTiet = mhv.getSoTC()*20;
        int soTietDuocNghi = (soTiet*20)/100;
        int soBuoiDuocNghi = soTietDuocNghi/4;

        tvSoBuoi.setText(mhv.getSoBuoiNghi()+"/"+soBuoiDuocNghi);

        int value = (mhv.getSoBuoiNghi()/soBuoiDuocNghi)*100;
        prgb.setProgress(value);

        tvTenMH.setText(mhv.getTenMH());
        return row;
    }
}
