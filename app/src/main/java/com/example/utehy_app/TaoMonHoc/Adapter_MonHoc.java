package com.example.utehy_app.TaoMonHoc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.utehy_app.Model.MonHoc;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class Adapter_MonHoc extends BaseAdapter {
    Activity context;
    ArrayList<MonHoc> list;

    public Adapter_MonHoc() {
    }

    public Adapter_MonHoc(Activity context, ArrayList<MonHoc> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        View row = inflater.inflate(R.layout.row_monhoc, null);

        TextView tvTenMH = row.findViewById(R.id.rowMH_tvTenMH);
        TextView tvSoTC = row.findViewById(R.id.rowMH_tvSoTC);

        MonHoc mh = list.get(position);

        tvTenMH.setText(mh.getTenMH());
        tvSoTC.setText(mh.getSoTC()+"");

        return row;
    }
}
