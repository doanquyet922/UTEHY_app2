package com.example.utehy_app.TaoDiemDanh;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.Model.Obj_RowTaoDiemDanh;
import com.example.utehy_app.R;

import java.util.ArrayList;

public class Adapter_TaoDiemDanh extends BaseAdapter {
    Activity context;
    ArrayList<Obj_RowTaoDiemDanh>list;

    public Adapter_TaoDiemDanh() {
    }

    public Adapter_TaoDiemDanh(Activity context, ArrayList<Obj_RowTaoDiemDanh> list) {
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
        View row = inflater.inflate(R.layout.row_taodiemdanh, null);

        TextView tvTenSV = row.findViewById(R.id.rowTaoDiemDanh_tvTenSV);
        TextView tvMaSV = row.findViewById(R.id.rowTaoDiemDanh_tvMaSV);

        CheckBox ckb = (CheckBox) row.findViewById(R.id.rowTaoDiemDanh_checkbox);

        Obj_RowTaoDiemDanh obj = list.get(position);

        tvTenSV.setText(obj.getTenSV());
        tvMaSV.setText(obj.getMaSV());

        if(obj.isVangMat()==true){
            ckb.setChecked(false);
        }else{
            ckb.setChecked(true);
        }

        ckb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ckb.isChecked()==true){
                    obj.setVangMat(false);
                }else{
                    obj.setVangMat(true);
                }
            }
        });

        return row;
    }
}
