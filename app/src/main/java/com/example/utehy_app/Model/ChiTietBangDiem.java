package com.example.utehy_app.Model;

public class ChiTietBangDiem {
    String maBangDiemString,maMH,diemTBMon;

    public ChiTietBangDiem() {
    }

    public ChiTietBangDiem(String maBangDiemString, String maMH, String diemTBMon) {
        this.maBangDiemString = maBangDiemString;
        this.maMH = maMH;
        this.diemTBMon = diemTBMon;
    }

    public String getMaBangDiemString() {
        return maBangDiemString;
    }

    public void setMaBangDiemString(String maBangDiemString) {
        this.maBangDiemString = maBangDiemString;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getDiemTBMon() {
        return diemTBMon;
    }

    public void setDiemTBMon(String diemTBMon) {
        this.diemTBMon = diemTBMon;
    }
}
