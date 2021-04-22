package com.example.utehy_app.ManHinhChinh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utehy_app.Model.TinTucUTEHY;
import com.example.utehy_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_GridView_TinTuc extends BaseAdapter {
    Activity context;
    ArrayList<TinTucUTEHY>list;

    public Adapter_GridView_TinTuc() {
    }

    public Adapter_GridView_TinTuc(Activity context, ArrayList<TinTucUTEHY> list) {
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
        View row = inflater.inflate(R.layout.single_column_gridview, null);

        ImageView img = row.findViewById(R.id.rowTT_img);
        TextView tvTieuDe = row.findViewById(R.id.rowTT_tvTieuDe);
        TextView tvNgay = row.findViewById(R.id.rowTT_tvNgayDang);

        Picasso.get()
                .load(list.get(position).getLinkAnh())
                .resize(60, 80)
                .centerCrop()
                .into(img);

        String tieuDe = list.get(position).getTieuDe();
        String tieuDe_out ="";
        for(int i=0;i<tieuDe.length();++i){
            if(i==50){
                break;
            }else{
                tieuDe_out = tieuDe_out+tieuDe.charAt(i);
            }
        }
        tvTieuDe.setText(tieuDe_out+"...");
        tvNgay.setText(list.get(position).getNgayDang());


        return row;
    }
}
