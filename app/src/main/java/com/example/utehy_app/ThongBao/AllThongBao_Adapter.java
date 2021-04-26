package com.example.utehy_app.ThongBao;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.Model.ThongBao;
import com.example.utehy_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllThongBao_Adapter extends BaseAdapter {
    Activity context;
    ArrayList<ThongBao> list;

    public AllThongBao_Adapter() {
    }

    public AllThongBao_Adapter(Activity context, ArrayList<ThongBao> list) {
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
        View row = inflater.inflate(R.layout.custom_thongbao, null);

        TextView tvNguoiDang = row.findViewById(R.id.customTB_tvTenNguoiDang);
        TextView tvNgay = row.findViewById(R.id.customTB_tvNgayDang);


        TextView tvND = row.findViewById(R.id.customTB_tvNoiDungTB);

        ImageView img = row.findViewById(R.id.customTB_imgAnh);

        ThongBao tb = list.get(position);

        tvNguoiDang.setText(tb.getTenSV());
        tvNgay.setText(tb.getNgayGui());
        tvND.setText(tb.getNoiDung());
        if(tb.getLinkAnh().length()==0){
            img.setVisibility(View.GONE);
        }else{
            Picasso.get()
                    .load(list.get(position).getLinkAnh())
                    .into(img);
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
            }
        });

        return row;
    }
}
