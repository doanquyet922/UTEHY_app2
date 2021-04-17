package com.example.utehy_app.Model;

public class CTDiemDanh {
    String maBangDiemDanh,maSV;
    int vangMat;

    public CTDiemDanh(String maBangDiemDanh, String maSV, int vangMat) {
        this.maBangDiemDanh = maBangDiemDanh;
        this.maSV = maSV;
        this.vangMat = vangMat;
    }

    public CTDiemDanh() {
    }

    public String getMaBangDiemDanh() {
        return maBangDiemDanh;
    }

    public void setMaBangDiemDanh(String maBangDiemDanh) {
        this.maBangDiemDanh = maBangDiemDanh;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public int getVangMat() {
        return vangMat;
    }

    public void setVangMat(int vangMat) {
        this.vangMat = vangMat;
    }
}
