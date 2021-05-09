package com.example.utehy_app.QuanLyLopHoc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.utehy_app.Model.Lop;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class Adapter_LopHoc extends BaseAdapter {
    Activity context;
    ArrayList<Lop> list;

    public Adapter_LopHoc() {
    }

    public Adapter_LopHoc(Activity context, ArrayList<Lop> list) {
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
        View row = inflater.inflate(R.layout.row_lophoc, null);

        TextView tvMaLop = row.findViewById(R.id.rowLop_tvMaLop);
        TextView tvTenLop = row.findViewById(R.id.rowLop_tvTenLop);

        Lop l = list.get(position);

        tvMaLop.setText(l.getMaLop());
        tvTenLop.setText(l.getTenLop());

        return row;
    }
}
