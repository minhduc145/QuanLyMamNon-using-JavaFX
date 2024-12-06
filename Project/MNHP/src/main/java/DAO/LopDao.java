package DAO;

import Model.LopModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LopDao {
    List<LopModel.GVCN> dsGVCN(String idlop) {
        List<LopModel.GVCN> dsGVCN = new ArrayList<>();
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT idCBNV,HoTen from CBNV where idLop = ?";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setString(1, idlop);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsGVCN.add(new LopModel.GVCN(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsGVCN;
    }

    public void updateLop(String idcu, String idmoi, String ten) throws Exception {
        Connection cn = (DbHelper.getInstance()).getConnection();
        String SQL = "UPDATE Lop\n" +
                "   SET [idLop] = ?, TenLop = ?\n" +
                " WHERE idLop = ?";
        PreparedStatement stmt = cn.prepareStatement(SQL);
        stmt.setString(1, idmoi);
        stmt.setString(2, ten);
        stmt.setString(3, idcu);
        stmt.executeUpdate();
    }

    public void xoaLop(LopModel l) throws Exception {
        Connection cn = (DbHelper.getInstance()).getConnection();
        String SQL = "DELETE FROM [dbo].[Lop]\n" +
                "      WHERE idLop = ?";
        PreparedStatement stmt = cn.prepareStatement(SQL);
        stmt.setString(1, l.getId());
        stmt.executeUpdate();
    }

    public void ThemLop(String id, String txt) throws Exception {
        Connection cn = (DbHelper.getInstance()).getConnection();
        String SQL = "INSERT INTO [dbo].[Lop]\n" +
                "           ([idLop]\n" +
                "           ,[TenLop])\n" +
                "     VALUES\n" +
                "           (?,?)";
        PreparedStatement stmt = cn.prepareStatement(SQL);
        stmt.setString(1, id);
        stmt.setString(2, txt);
        stmt.executeUpdate();
    }

    public List<LopModel> getDSLop() {
        List<LopModel> ds = new ArrayList<>();
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT Lop.idLop, Lop.TenLop,  COUNT(Tre.idTre) AS SoTre\n" +
                    "FROM     Lop left JOIN\n" +
                    "                  Tre ON Lop.idLop = Tre.idLop \n" +
                    "Group by Lop.idLop, Lop.TenLop\n";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LopModel lop = new LopModel(rs.getString(1), rs.getString(2));
                lop.setSotre(rs.getInt(3));
                lop.setDsGVCN(dsGVCN(lop.getId()));
                ds.add(lop);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ds;

    }

}
