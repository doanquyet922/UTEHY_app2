package com.example.utehy_app.QuanTriAll;

public class Obj_TaiKhoanQuanLy {
    private String maSV,maLop,hoTen,gioiTinh,loaiTK;

    public Obj_TaiKhoanQuanLy() {
    }

    public Obj_TaiKhoanQuanLy(String maSV, String maLop, String hoTen, String gioiTinh, String loaiTK) {
        this.maSV = maSV;
        this.maLop = maLop;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.loaiTK = loaiTK;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getLoaiTK() {
        return loaiTK;
    }

    public void setLoaiTK(String loaiTK) {
        this.loaiTK = loaiTK;
    }

    @Override
    public String toString() {
        return "Obj_TaiKhoanQuanLy{" +
                "maSV='" + maSV + '\'' +
                ", maLop='" + maLop + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", loaiTK='" + loaiTK + '\'' +
                '}';
    }
}
