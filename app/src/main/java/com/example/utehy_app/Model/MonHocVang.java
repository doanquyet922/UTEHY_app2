package com.example.utehy_app.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MonHocVang implements Parcelable {
    private String maSV,maMH,tenMH;
    private int soTC,soBuoiNghi;

    public MonHocVang() {
    }

    public MonHocVang(String maSV, String maMH, String tenMH, int soTC, int soBuoiNghi) {
        this.maSV = maSV;
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTC = soTC;
        this.soBuoiNghi = soBuoiNghi;
    }

    protected MonHocVang(Parcel in) {
        maSV = in.readString();
        maMH = in.readString();
        tenMH = in.readString();
        soTC = in.readInt();
        soBuoiNghi = in.readInt();
    }

    public static final Creator<MonHocVang> CREATOR = new Creator<MonHocVang>() {
        @Override
        public MonHocVang createFromParcel(Parcel in) {
            return new MonHocVang(in);
        }

        @Override
        public MonHocVang[] newArray(int size) {
            return new MonHocVang[size];
        }
    };

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public int getSoTC() {
        return soTC;
    }

    public void setSoTC(int soTC) {
        this.soTC = soTC;
    }

    public int getSoBuoiNghi() {
        return soBuoiNghi;
    }

    public void setSoBuoiNghi(int soBuoiNghi) {
        this.soBuoiNghi = soBuoiNghi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maSV);
        dest.writeString(maMH);
        dest.writeString(tenMH);
        dest.writeInt(soTC);
        dest.writeInt(soBuoiNghi);
    }
}
