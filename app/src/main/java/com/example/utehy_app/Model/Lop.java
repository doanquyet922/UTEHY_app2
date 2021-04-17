package com.example.utehy_app.Model;

public class Lop {
    String maLop,tenLop,maKhoa,gvCN;

    public Lop(String maLop, String tenLop, String maKhoa, String gvCN) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.maKhoa = maKhoa;
        this.gvCN = gvCN;
    }

    public Lop() {
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getGvCN() {
        return gvCN;
    }

    public void setGvCN(String gvCN) {
        this.gvCN = gvCN;
    }
}
