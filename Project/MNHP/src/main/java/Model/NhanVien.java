package Model;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Setter
@Builder
public class NhanVien {
    private double hsl = 0.0;
    private double pctn = 0.0;
    private Image img;
    private String idCBNV = "null";
    private String Hoten = "null";
    private String idChucVu = "null";
    private String NoiSinh = "null";
    private String DiaChiTT = "null";
    private String SDT = "null";
    private String Email = "null";
    private LocalDate NgayVaoLam;
    private LocalDate NgaySinh;
    private String soCCCD = "null";
    private String idTinhTrang = "null";
    private boolean GTNam;
    private String idLop = "null";
    private String MatKhau = "null";
    private String idQuyen = "null";
    private String TDHV = "null";
    private CheckBox select = new CheckBox();

    public NhanVien(double hsl, double pctn, Image img, String idCBNV, String hoten, String idChucVu, String noiSinh, String diaChiTT, String SDT, String email, LocalDate ngayVaoLam, LocalDate ngaySinh, String soCCCD, String idTinhTrang, boolean GTNam, String idLop, String matKhau, String idQuyen, String TDHV, CheckBox select) {
        this.hsl = hsl;
        this.pctn = pctn;
        this.img = img;
        this.idCBNV = idCBNV;
        Hoten = hoten;
        this.idChucVu = idChucVu;
        NoiSinh = noiSinh;
        DiaChiTT = diaChiTT;
        this.SDT = SDT;
        Email = email;
        NgayVaoLam = ngayVaoLam;
        NgaySinh = ngaySinh;
        this.soCCCD = soCCCD;
        this.idTinhTrang = idTinhTrang;
        this.GTNam = GTNam;
        this.idLop = idLop;
        MatKhau = matKhau;
        this.idQuyen = idQuyen;
        this.TDHV = TDHV;
        this.select = select;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(boolean i) {
        this.select.setSelected(i);
    }


    public double getPctn() {
        return pctn;
    }

    public void setPctn(double pctn) {
        this.pctn = pctn;
    }

    public double getHsl() {
        return hsl;
    }

    public void setHsl(double hsl) {
        this.hsl = hsl;
    }

    public String getTDHV() {
        return TDHV;
    }

    public void setTDHV(String TDHV) {
        this.TDHV = TDHV;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        NgayVaoLam = ngayVaoLam;
    }

    public LocalDate getNgayVaoLam() {
        return NgayVaoLam;
    }

    public String getIdCBNV() {
        return idCBNV;
    }

    public void setIdCBNV(String idCBNV) {
        this.idCBNV = idCBNV;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public String getIdChucVu() {
        return idChucVu;
    }

    public void setIdChucVu(String idChucVu) {
        this.idChucVu = idChucVu;
    }

    public String getNoiSinh() {
        return NoiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        NoiSinh = noiSinh;
    }

    public String getDiaChiTT() {
        return DiaChiTT;
    }

    public void setDiaChiTT(String diaChiTT) {
        DiaChiTT = diaChiTT;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getSoCCCD() {
        return soCCCD;
    }

    public void setSoCCCD(String soCCCD) {
        this.soCCCD = soCCCD;
    }

    public String getIdTinhTrang() {
        return idTinhTrang;
    }

    public void setIdTinhTrang(String idTinhTrang) {
        this.idTinhTrang = idTinhTrang;
    }

    public boolean isGTNam() {
        return GTNam;
    }

    public void setGTNam(boolean GTNam) {
        this.GTNam = GTNam;
    }

    public String getIdLop() {
        return idLop;
    }

    public void setIdLop(String idLop) {
        this.idLop = idLop;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getIdQuyen() {
        return idQuyen;

    }

    public void setIdQuyen(String idQuyen) {
        this.idQuyen = idQuyen;
    }

    public String getGioiTinh() {
        if (isGTNam())
            return "Nam";
        return "Nữ";
    }


    public NhanVien() {
        super();
    }

    public String getStringNgayVaoLam() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(NgayVaoLam);
        return date;
    }

    public LocalDate getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        NgaySinh = ngaySinh;
    }

    @Override
    public String toString() {
        return Hoten + " (" + idCBNV + ")";
    }
}
