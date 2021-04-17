package com.example.utehy_app.Model;

public class Khoa {
    String maKhoa,tenKhoa,tenTruongKhoa;

    public Khoa() {
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public Khoa(String maKhoa, String tenKhoa, String tenTruongKhoa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.tenTruongKhoa = tenTruongKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public String getTenTruongKhoa() {
        return tenTruongKhoa;
    }

    public void setTenTruongKhoa(String tenTruongKhoa) {
        this.tenTruongKhoa = tenTruongKhoa;
    }
}
