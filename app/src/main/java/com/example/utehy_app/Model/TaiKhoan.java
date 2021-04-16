package com.example.utehy_app.Model;

public class TaiKhoan {
    String MaSV,MatKhau,LoaiTK;

    public TaiKhoan() {
    }

    public TaiKhoan(String maSV, String loaiTK, String matKhau) {
        MaSV = maSV;
        MatKhau = matKhau;
        LoaiTK = loaiTK;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "MaSV='" + MaSV + '\'' +
                ", MatKhau='" + MatKhau + '\'' +
                ", LoaiTK='" + LoaiTK + '\'' +
                '}';
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getLoaiTK() {
        return LoaiTK;
    }

    public void setLoaiTK(String loaiTK) {
        LoaiTK = loaiTK;
    }
}
