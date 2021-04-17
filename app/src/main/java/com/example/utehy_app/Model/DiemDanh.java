package com.example.utehy_app.Model;

public class DiemDanh {
    String maBangDiemDanh,maLop,maMH,gvDay,ngay;

    public DiemDanh(String maBangDiemDanh, String maLop, String maMH, String gvDay, String ngay) {
        this.maBangDiemDanh = maBangDiemDanh;
        this.maLop = maLop;
        this.maMH = maMH;
        this.gvDay = gvDay;
        this.ngay = ngay;
    }

    public DiemDanh() {
    }

    public String getMaBangDiemDanh() {
        return maBangDiemDanh;
    }

    public void setMaBangDiemDanh(String maBangDiemDanh) {
        this.maBangDiemDanh = maBangDiemDanh;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getGvDay() {
        return gvDay;
    }

    public void setGvDay(String gvDay) {
        this.gvDay = gvDay;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
