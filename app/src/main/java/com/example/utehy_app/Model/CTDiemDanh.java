package com.example.utehy_app.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

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

    @Override
    public String toString() {
        return "CTDiemDanh{" +
                "maBangDiemDanh='" + maBangDiemDanh + '\'' +
                ", maSV='" + maSV + '\'' +
                ", vangMat=" + vangMat +
                '}';
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
