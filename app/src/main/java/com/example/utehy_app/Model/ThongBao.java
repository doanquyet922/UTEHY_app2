package com.example.utehy_app.Model;

public class ThongBao {
    String maTB,maSV,tenSV,maLop,noiDung,ngayGui,linkAnh;

    public ThongBao() {
    }

    public ThongBao(String maTB, String maSV, String tenSV, String maLop, String noiDung, String ngayGui, String linkAnh) {
        this.maTB = maTB;
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.maLop = maLop;
        this.noiDung = noiDung;
        this.ngayGui = ngayGui;
        this.linkAnh = linkAnh;
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

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
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

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }
}
