package com.example.utehy_app.Model;

public class ThongBao {
    String maTB,maSV,maLop,noiDung,ngayGui;

    public ThongBao(String maTB, String maSV, String maLop, String noiDung, String ngayGui) {
        this.maTB = maTB;
        this.maSV = maSV;
        this.maLop = maLop;
        this.noiDung = noiDung;
        this.ngayGui = ngayGui;
    }

    public ThongBao() {
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(String ngayGui) {
        this.ngayGui = ngayGui;
    }
}
