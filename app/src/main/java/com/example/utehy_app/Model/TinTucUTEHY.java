package com.example.utehy_app.Model;

public class TinTucUTEHY {
    private String linkAnh,linkBaiViet,tieuDe,ngayDang;

    public TinTucUTEHY() {
    }

    public TinTucUTEHY(String linkAnh, String linkBaiViet, String tieuDe, String ngayDang) {
        this.linkAnh = linkAnh;
        this.linkBaiViet = linkBaiViet;
        this.tieuDe = tieuDe;
        this.ngayDang = ngayDang;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public String getLinkBaiViet() {
        return linkBaiViet;
    }

    public void setLinkBaiViet(String linkBaiViet) {
        this.linkBaiViet = linkBaiViet;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    @Override
    public String toString() {
        return "TinTucUTEHY{" +
                "linkAnh='" + linkAnh + '\'' +
                ", linkBaiViet='" + linkBaiViet + '\'' +
                ", tieuDe='" + tieuDe + '\'' +
                ", ngayDang='" + ngayDang + '\'' +
                '}';
    }
}
