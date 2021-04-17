package com.example.utehy_app.Model;

public class LichHoc {
    String maLichHoc,maLop,ngayApDung,link;

    public LichHoc() {
    }

    public LichHoc(String maLichHoc, String maLop, String ngayApDung, String link) {
        this.maLichHoc = maLichHoc;
        this.maLop = maLop;
        this.ngayApDung = ngayApDung;
        this.link = link;
    }

    public String getMaLichHoc() {
        return maLichHoc;
    }

    public void setMaLichHoc(String maLichHoc) {
        this.maLichHoc = maLichHoc;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getNgayApDung() {
        return ngayApDung;
    }

    public void setNgayApDung(String ngayApDung) {
        this.ngayApDung = ngayApDung;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
