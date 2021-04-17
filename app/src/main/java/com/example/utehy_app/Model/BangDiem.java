package com.example.utehy_app.Model;

public class BangDiem {
    String maBangDiem,maSV,kiHoc,diemTB;

    public BangDiem(String maBangDiem, String maSV, String kiHoc, String diemTB) {
        this.maBangDiem = maBangDiem;
        this.maSV = maSV;
        this.kiHoc = kiHoc;
        this.diemTB = diemTB;
    }

    public BangDiem() {
    }

    public String getMaBangDiem() {
        return maBangDiem;
    }

    public void setMaBangDiem(String maBangDiem) {
        this.maBangDiem = maBangDiem;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getKiHoc() {
        return kiHoc;
    }

    public void setKiHoc(String kiHoc) {
        this.kiHoc = kiHoc;
    }

    public String getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(String diemTB) {
        this.diemTB = diemTB;
    }
}
