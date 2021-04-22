package com.example.utehy_app.ThongBao;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.utehy_app.Model.MonHocVang;
import com.example.utehy_app.Model.ThongBao;
import com.example.utehy_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThongBaoAdapter extends BaseAdapter {
    Activity context;
    ArrayList<ThongBao> arrTB;
    DatabaseReference mData;

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
        TextView tvNguoiDang=row.findViewById(R.id.rowTB_tvTen);
        TextView tvLop=row.findViewById(R.id.rowTB_tvLop);
        TextView tvThoiGian=row.findViewById(R.id.rowTB_tvThoiGianGui);
        mData= FirebaseDatabase.getInstance().getReference();
        mData.child("SinhVien").child(arrTB.get(position).getMaSV()).child("hoTen").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvNguoiDang.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("BBB", "ThongBaoAdapter: "+error.getMessage());
            }
        });
        return row;
    }
}
