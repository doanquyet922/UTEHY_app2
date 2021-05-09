package com.example.utehy_app.QuanLyLopHoc;

public class Obj_LichHocTaoMoi {
    private String maLichHoc,maLop,ngayApDung;

    public Obj_LichHocTaoMoi() {
    }

    public Obj_LichHocTaoMoi(String maLichHoc, String maLop, String ngayApDung) {

        this.maLichHoc = maLichHoc;
        this.maLop = maLop;
        this.ngayApDung = ngayApDung;
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
}
