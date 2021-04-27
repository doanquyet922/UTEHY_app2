package com.example.utehy_app.Model;

public class SuKien {
  String  maLop,ngay,noiDung;

    public SuKien(String maLop, String ngay, String noiDung) {
        this.maLop = maLop;
        this.ngay = ngay;
        this.noiDung = noiDung;
    }

    public SuKien() {
    }

    @Override
    public String toString() {
        return "SuKien{" +
                "maLop='" + maLop + '\'' +
                ", ngay='" + ngay + '\'' +
                ", noiDung='" + noiDung + '\'' +
                '}';
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
