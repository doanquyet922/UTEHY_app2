package com.example.utehy_app.Model;

public class ThuTrongTuan {
    String Thu,Sang,Chieu;

    public ThuTrongTuan(String thu, String sang, String chieu) {
        Thu = thu;
        Sang = sang;
        Chieu = chieu;
    }

    @Override
    public String toString() {
        return "ThuTrongTuan{" +
                "Thu='" + Thu + '\'' +
                ", Sang='" + Sang + '\'' +
                ", Chieu='" + Chieu + '\'' +
                '}';
    }

    public ThuTrongTuan() {
    }

    public String getThu() {
        return Thu;
    }

    public void setThu(String thu) {
        Thu = thu;
    }

    public String getSang() {
        return Sang;
    }

    public void setSang(String sang) {
        Sang = sang;
    }

    public String getChieu() {
        return Chieu;
    }

    public void setChieu(String chieu) {
        Chieu = chieu;
    }
}
