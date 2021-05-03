package com.example.utehy_app.QuanLyLichHoc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.utehy_app.R;

import java.util.ArrayList;

public class Adapter_QuanLyLichHoc extends BaseAdapter {
    Activity context;
    ArrayList<ThoiKhoaBieu> list;


    public Adapter_QuanLyLichHoc() {
    }

    public Adapter_QuanLyLichHoc(Activity context, ArrayList<ThoiKhoaBieu> list) {
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
        View row = inflater.inflate(R.layout.row_quanlylichhoc, null);

        TextView tvThu = row.findViewById(R.id.rowQLLH_tvThu);
        TextView tvSang = row.findViewById(R.id.rowQLLH_tvSang);
        TextView tvChieu = row.findViewById(R.id.rowQLLH_tvChieu);

        ThoiKhoaBieu tkb = list.get(position);

        tvThu.setText(getThu(tkb.getThu()));

//        String monHoc_sang = tkb.getSang().substring(0,tkb.getSang().indexOf("-"));
//        String monHoc_chieu = tkb.getChieu().substring(0,tkb.getChieu().indexOf("-"));

        tvSang.setText(tkb.getSang());
        tvChieu.setText(tkb.getChieu());

        return row;

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
