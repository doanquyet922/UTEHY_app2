package com.example.utehy_app.Model;

public class LichThi {
    String maLichThi,maLop,lick;

    public LichThi() {
    }

    public LichThi(String maLichThi, String maLop, String lick) {
        this.maLichThi = maLichThi;
        this.maLop = maLop;
        this.lick = lick;
    }

    public String getMaLichThi() {
        return maLichThi;
    }

    public void setMaLichThi(String maLichThi) {
        this.maLichThi = maLichThi;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getLick() {
        return lick;
    }

    public void setLick(String lick) {
        this.lick = lick;
    }
}
