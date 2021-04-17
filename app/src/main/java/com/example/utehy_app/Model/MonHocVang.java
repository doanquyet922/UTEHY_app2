package com.example.utehy_app.Model;

public class MonHocVang {
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
}
