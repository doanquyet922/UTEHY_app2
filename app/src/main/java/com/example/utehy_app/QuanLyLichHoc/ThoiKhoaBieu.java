package com.example.utehy_app.QuanLyLichHoc;

public class ThoiKhoaBieu {
    private String thu,sang,chieu;

    public ThoiKhoaBieu() {
    }

    @Override
    public String toString() {
        return "ThoiKhoaBieu{" +
                "thu='" + thu + '\'' +
                ", sang='" + sang + '\'' +
                ", chieu='" + chieu + '\'' +
                '}';
    }

    public ThoiKhoaBieu(String thu, String sang, String chieu) {
        this.thu = thu;
        this.sang = sang;
        this.chieu = chieu;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getSang() {
        return sang;
    }

    public void setSang(String sang) {
        this.sang = sang;
    }

    public String getChieu() {
        return chieu;
    }

    public void setChieu(String chieu) {
        this.chieu = chieu;
    }
}
