package com.example.utehy_app.QuanLyLopHoc;

public class Obj_TKBTrongNgay {
    private String SANG,CHIEU;

    public Obj_TKBTrongNgay() {
    }

    public Obj_TKBTrongNgay(String SANG, String CHIEU) {
        this.SANG = SANG;
        this.CHIEU = CHIEU;
    }

    public String getSANG() {
        return SANG;
    }

    public void setSANG(String SANG) {
        this.SANG = SANG;
    }

    public String getCHIEU() {
        return CHIEU;
    }

    public void setCHIEU(String CHIEU) {
        this.CHIEU = CHIEU;
    }
}
