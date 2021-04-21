package com.example.utehy_app.Model;

public class Row_CTDiemDanh {
    String ngayDiemDanh;
    int vangMat;

    public Row_CTDiemDanh(String ngayDiemDanh, int vangMat) {
        this.ngayDiemDanh = ngayDiemDanh;
        this.vangMat = vangMat;
    }

    public Row_CTDiemDanh() {
    }

    public String getNgayDiemDanh() {
        return ngayDiemDanh;
    }

    public void setNgayDiemDanh(String ngayDiemDanh) {
        this.ngayDiemDanh = ngayDiemDanh;
    }

    public int getVangMat() {
        return vangMat;
    }

    public void setVangMat(int vangMat) {
        this.vangMat = vangMat;
    }
}
