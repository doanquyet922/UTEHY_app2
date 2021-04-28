package com.example.utehy_app.QuanTriAll;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utehy_app.R;

import java.util.ArrayList;

public class Adapter_QuanLyTaiKhoan extends BaseAdapter {
    Activity context;
    ArrayList<Obj_TaiKhoanQuanLy> list;

    public Adapter_QuanLyTaiKhoan() {
    }

    public Adapter_QuanLyTaiKhoan(Activity context, ArrayList<Obj_TaiKhoanQuanLy> list) {
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
        View row = inflater.inflate(R.layout.row_qltk, null);

        ImageView img = row.findViewById(R.id.rowTK_img);
        TextView tvHoTen = row.findViewById(R.id.rowTK_tvHoTen);
        TextView tvMaLop = row.findViewById(R.id.rowTK_tvMaLop);
        TextView tvChucVu = row.findViewById(R.id.rowTK_tvChucVu);

        Obj_TaiKhoanQuanLy obj = list.get(position);

        if(obj.getGioiTinh().toLowerCase().equals("nam")){
            img.setImageResource(R.drawable.ic_svnam);
        }else{
            img.setImageResource(R.drawable.ic_svnu);
        }

        tvHoTen.setText(obj.getHoTen());
        tvMaLop.setText(obj.getMaLop());
        if(obj.getLoaiTK().equals("qtv")){
            tvChucVu.setText("Cán bộ lớp");
        }else{
            tvChucVu.setText("Thành viên lớp");
        }

        return row;
    }
}
