package DAO;

import Model.NhanVien;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CBNVDao {

    public boolean xoaNV(String id) {
        boolean i = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Xác nhận xóa");
        alert.showAndWait();
        if (alert.getResult().getButtonData().isDefaultButton()) {
            try {
                Connection cn = (DbHelper.getInstance()).getConnection();
                String SQL = "delete from CBNV where idCBNV = ?";
                PreparedStatement stmt = cn.prepareStatement(SQL);
                stmt.setString(1, id);
                stmt.executeUpdate();
                i = true;
            } catch (Exception e) {
                i = false;
            }
            if (i == true) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Xoa Thanh cong");
                PickAFile.delIMG(id);
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Xoa khong Thanh cong");
                alert.showAndWait();
            }
        }
        return i;
    }
    public List<NhanVien> getGVchuacoLop() {
        List<NhanVien> ds = new ArrayList<>();
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT CBNV.idCBNV, CBNV.HoTen, CBNV.idChucVu, CBNV.NoiSinh,  CBNV.DiaChiTT, CBNV.SDT, CBNV.Email, CBNV.NgayVaoLam, CBNV.SoCCCD, CBNV.idTinhTrang, CBNV.GioiTinhNam, CBNV.idLop, TaiKhoan.MatKhau, \n" + "                  TaiKhoan.idQuyen, CBNV.NgaySinh, CBNV.TrinhDoHocVan, CBNV.HSL, CBNV.PCTN\n" + "FROM     CBNV LEFT OUTER JOIN\n" + "                  TaiKhoan ON CBNV.idCBNV = TaiKhoan.idCBNV\n";
            SQL+= "Where CBNV.idLop is NULL and CBNV.idChucVu = 'gv'";

            PreparedStatement stmt = cn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhanVien cb = new NhanVien();
                cb.setIdCBNV(rs.getString("idcbnv"));
                cb.setHoten(rs.getString(2));
                cb.setIdChucVu(rs.getString(3));
                cb.setNoiSinh(rs.getString(4));
                cb.setDiaChiTT(rs.getString(5));
                cb.setSDT(rs.getString(6));
                cb.setEmail(rs.getString(7));
                if (rs.getDate(8) != null) {
                    LocalDate newDate = rs.getDate(8).toLocalDate();
                    cb.setNgayVaoLam(newDate);
                }
                cb.setSoCCCD(rs.getString(9));
                cb.setIdTinhTrang(rs.getString(10));
                cb.setGTNam(rs.getBoolean(11));
                cb.setIdLop(rs.getString(12));
                cb.setMatKhau(rs.getString(13));
                cb.setIdQuyen(rs.getString(14));
                if (rs.getDate(15) != null) {
                    LocalDate newDate2 = rs.getDate(15).toLocalDate();
                    cb.setNgaySinh(newDate2);
                }
                cb.setTDHV(rs.getString(16));
                cb.setHsl(rs.getDouble(17));
                cb.setPctn(rs.getDouble(18));
                ds.add(cb);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<NhanVien> getDSCB() {
        List<NhanVien> ds = new ArrayList<>();
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT CBNV.idCBNV, CBNV.HoTen, CBNV.idChucVu, CBNV.NoiSinh,  CBNV.DiaChiTT, CBNV.SDT, CBNV.Email, CBNV.NgayVaoLam, CBNV.SoCCCD, CBNV.idTinhTrang, CBNV.GioiTinhNam, CBNV.idLop, TaiKhoan.MatKhau, \n" + "                  TaiKhoan.idQuyen, CBNV.NgaySinh, CBNV.TrinhDoHocVan, CBNV.HSL, CBNV.PCTN\n" + "FROM     CBNV LEFT OUTER JOIN\n" + "                  TaiKhoan ON CBNV.idCBNV = TaiKhoan.idCBNV\n";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhanVien cb = new NhanVien();
                cb.setIdCBNV(rs.getString("idcbnv"));
                cb.setHoten(rs.getString(2));
                cb.setIdChucVu(rs.getString(3));
                cb.setNoiSinh(rs.getString(4));
                cb.setDiaChiTT(rs.getString(5));
                cb.setSDT(rs.getString(6));
                cb.setEmail(rs.getString(7));
                if (rs.getDate(8) != null) {
                    LocalDate newDate = rs.getDate(8).toLocalDate();
                    cb.setNgayVaoLam(newDate);
                }
                cb.setSoCCCD(rs.getString(9));
                cb.setIdTinhTrang(rs.getString(10));
                cb.setGTNam(rs.getBoolean(11));
                cb.setIdLop(rs.getString(12));
                cb.setMatKhau(rs.getString(13));
                cb.setIdQuyen(rs.getString(14));
                if (rs.getDate(15) != null) {
                    LocalDate newDate2 = rs.getDate(15).toLocalDate();
                    cb.setNgaySinh(newDate2);
                }
                cb.setTDHV(rs.getString(16));
                cb.setHsl(rs.getDouble(17));
                cb.setPctn(rs.getDouble(18));
                ds.add(cb);
                System.out.println(cb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean updateCBNV(String id, String idQ, String mk, String hoten, String idchv, String noisinh, String tn, String hsl, String Dctt, String sdt, String em, LocalDate Nvl, String cc, String idtt, String gt, String idlop, LocalDate ns, String hv) {
        boolean i = false;
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "UPDATE [dbo].[cBNV]\n" + "   SET [HoTen] = ?\n" + "      ,[idChucVu] = ?\n" + "      ,[NoiSinh] = ?\n" + "      ,[HSL] = ?\n" + "      ,[DiaChiTT] = ?\n" + "      ,[SDT] = ?\n" + "      ,[Email] = ?\n" + "      ,[NgayVaoLam] = ?\n" + "      ,[SoCCCD] = ?\n" + "      ,[idTinhTrang] = ?\n" + "      ,[GioiTinhNam] = ?\n" + "      ,[NgaySinh] = ?" + "      ,[TrinhDoHocVan] = ?\n" + ",[idLop] = ?\n" + ",[PCTN] = ?\n" + " WHERE idCBNV = ?";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setString(1, hoten);
            stmt.setString(2, idchv);
            stmt.setString(3, noisinh);
            stmt.setString(4, hsl);
            stmt.setString(5, Dctt);
            stmt.setString(6, sdt);
            stmt.setString(7, em);
            Date d = null;
            if (Nvl != null) d = Date.valueOf(Nvl);
            stmt.setDate(8, d);
            stmt.setString(9, cc);
            stmt.setString(10, idtt);
            if (gt.equals("Nam")) stmt.setString(11, "1");
            else stmt.setString(11, "0");
            Date ds = null;
            if (ns != null) ds = Date.valueOf(ns);
            stmt.setDate(12, ds);
            stmt.setString(13, hv);
            stmt.setString(14, idlop);
            stmt.setString(15, tn);
            stmt.setString(16, id);
            stmt.executeUpdate();
//mk
            SQL = "UPDATE [dbo].[TaiKhoan]\n" + "   SET [MatKhau] = ?\n" + "      ,[idQuyen] = ?\n" + " WHERE idCBNV = ?";

            stmt = cn.prepareStatement(SQL);
            stmt.setString(1, mk);
            stmt.setString(2, idQ);
            stmt.setString(3, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        CBNVDao cb = new CBNVDao();
        for (NhanVien c : cb.getDSCB()) {
            System.out.println(c.getIdLop());
        }
    }
}
