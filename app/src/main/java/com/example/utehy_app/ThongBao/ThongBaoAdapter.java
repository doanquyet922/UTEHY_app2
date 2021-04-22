package com.example.utehy_app.ThongBao;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.Model.ThongBao;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class ThongBaoAdapter extends BaseAdapter {
    Activity context;
    ArrayList<ThongBao> arrTB;

    public ThongBaoAdapter(Activity context, ArrayList<ThongBao> arrTB) {
        this.context = context;
        this.arrTB = arrTB;
    }

    @Override
    public int getCount() {
        return arrTB.size();
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
        View row = inflater.inflate(R.layout.row_thongbao, null);



        return row;
    }
}
