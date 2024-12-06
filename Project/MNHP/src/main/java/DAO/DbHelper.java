package DAO;

import Model.User;
import atlantafx.base.theme.CupertinoLight;
import hp.mnhp.AlertMessage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class DbHelper {
    private static DbHelper instance = null;
    public static String _db1 = null;
    private Connection cn = null;

    private DbHelper() {

        try {
            String configFile = "jdbc:sqlserver://\\\\localhost:1433;"+_db1+";databasename=MNHP;trustServerCertificate=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = configFile;
            cn = DriverManager.getConnection(dbURL);
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DbHelper getInstance() {
        if (instance == null) {
            instance = new DbHelper();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.cn;
    }



    public boolean DangNhap(String usn, String psw) {
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT * FROM TaiKhoan where idCBNV = ? and MatKhau = ?";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setString(1, usn);
            stmt.setString(2, psw);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                getUserInfo(usn);
                System.out.println(rs.getString(1));
                return true;
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //    public void getUserInfo(String usn) {
//        try {
//            Connection cn = (DbHelper.getInstance()).getConnection();
//            String SQL = "SELECT CBNV.idCBNV, idQuyen, HoTen FROM CBNV left join TaiKhoan on TaiKhoan.idCBNV = CBNV.idCBNV where CBNV.idCBNV = ?";
//            PreparedStatement stmt = cn.prepareStatement(SQL);
//            stmt.setString(1, usn);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                User.idCBNV = rs.getString(1);
//                User.idQuyen = rs.getString(2);
//                User.Hoten = rs.getString(3);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    public void getUserInfo(String usn) {
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT idLop, CBNV.idCBNV, idQuyen, HoTen, idchucvu FROM CBNV left join TaiKhoan on TaiKhoan.idCBNV = CBNV.idCBNV where CBNV.idCBNV = ?";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setString(1, usn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User.idCBNV = rs.getString("idcbnv");
                User.idQuyen = rs.getString("idquyen");
                User.Hoten = rs.getString("hoten");
                User.idChucVu = rs.getString("idchucvu");
                User.idLop = rs.getString("idlop");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
