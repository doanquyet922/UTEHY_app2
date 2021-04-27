package com.example.utehy_app.Model;

public class Obj_RowTaoDiemDanh {
    private String maSV,tenSV;
    private boolean isVangMat;

    public Obj_RowTaoDiemDanh() {
        isVangMat = true;
    }

    public Obj_RowTaoDiemDanh(String maSV, String tenSV, boolean isVangMat) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.isVangMat = isVangMat;
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

    public boolean isVangMat() {
        return isVangMat;
    }

    public void setVangMat(boolean vangMat) {
        isVangMat = vangMat;
    }

}
