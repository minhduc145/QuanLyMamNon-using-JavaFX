package DAO;

import Model.CBNVModule;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CBNVDao {

    public List<CBNVModule> getDSCB() {
        List<CBNVModule> ds = new ArrayList<>();
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT CBNV.idCBNV, CBNV.HoTen, CBNV.idChucVu, CBNV.NoiSinh, CBNV.QueQuan, CBNV.DiaChiTT, CBNV.SDT, CBNV.Email, CBNV.NgayVaoLam, CBNV.SoCCCD, CBNV.idTinhTrang, CBNV.GioiTinhNam, CBNV.idLop, TaiKhoan.MatKhau, \n" + "                  TaiKhoan.idQuyen, CBNV.NgaySinh, CBNV.TrinhDoHocVan\n" + "FROM     CBNV LEFT OUTER JOIN\n" + "                  TaiKhoan ON CBNV.idCBNV = TaiKhoan.idCBNV\n";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CBNVModule cb = new CBNVModule();
                cb.setIdCBNV(rs.getString(1));
                cb.setHoten(rs.getString(2));
                cb.setIdChucVu(rs.getString(3));
                cb.setNoiSinh(rs.getString(4));
                cb.setQueQuan(rs.getString(5));
                cb.setDiaChiTT(rs.getString(6));
                cb.setSDT(rs.getString(7));
                cb.setEmail(rs.getString(8));
                LocalDate newDate = rs.getDate(9).toLocalDate();

                cb.setNgayVaoLam(newDate);
                cb.setSoCCCD(rs.getString(10));
                cb.setIdTinhTrang(rs.getString(11));
                cb.setGTNam(rs.getBoolean(12));
                cb.setIdLop(rs.getString(13));
                cb.setMatKhau(rs.getString(14));
                cb.setIdQuyen(rs.getString(15));
                LocalDate newDate2 = rs.getDate(16).toLocalDate();

                cb.setNgaySinh(newDate2);
                cb.setTDHV(rs.getString(17));

                ds.add(cb);
                System.out.println(cb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean updateCBNV(String id, String idQ, String mk, String hoten, String idchv, String noisinh, String QueQuan, String Dctt, String sdt, String em, LocalDate Nvl, String cc, String idtt, String gt, String idlop, LocalDate ns, String hv) {
        boolean i = false;
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "UPDATE [dbo].[CBNV]\n" + "   SET [HoTen] = ?\n" + "      ,[idChucVu] = ?\n" + "      ,[NoiSinh] = ?\n" + "      ,[QueQuan] = ?\n" + "      ,[DiaChiTT] = ?\n" + "      ,[SDT] = ?\n" + "      ,[Email] = ?\n" + "      ,[NgayVaoLam] = ?\n" + "      ,[SoCCCD] = ?\n" + "      ,[idTinhTrang] = ?\n" + "      ,[GioiTinhNam] = ?\n" + "      ,[NgaySinh] = ?" + "      ,[TrinhDoHocVan] = ?\n" + "      ,[idLop] = ?\n" + " WHERE idCBNV = ?";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setString(1, hoten);
            stmt.setString(2, idchv);
            stmt.setString(3, noisinh);
            stmt.setString(4, QueQuan);
            stmt.setString(5, Dctt);
            stmt.setString(6, sdt);
            stmt.setString(7, em);
            Date d = Date.valueOf(Nvl);
            stmt.setDate(8, d);
            stmt.setString(9, cc);
            stmt.setString(10, idtt);
            if (gt.equals("Nam")) stmt.setString(11, "1");
            stmt.setString(11, "0");
            stmt.setDate(12, Date.valueOf(ns));
            stmt.setString(13, hv);
            stmt.setString(14, idlop);
            stmt.setString(15, id);
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
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CBNVDao cb = new CBNVDao();
        for (CBNVModule c : cb.getDSCB()) {
            System.out.println(c.getIdLop());
        }
    }
}