package DAO;

import Model.NhanVien;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PickAFile {
    public static void upIMG(NhanVien cb) {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        File[] f = dialog.getFiles();
        dialog.dispose();
        File dest = new File("D:\\Projects\\IntelliJ\\QuanLyMamNon\\Project\\MNHP\\src\\main\\resources\\hp\\mnhp\\IMG\\");
        try {
            FileUtils.copyFileToDirectory(f[0], dest);
            File old = new File("D:\\Projects\\IntelliJ\\QuanLyMamNon\\Project\\MNHP\\src\\main\\resources\\hp\\mnhp\\IMG\\" + f[0].getName());
            File newname = new File("D:\\Projects\\IntelliJ\\QuanLyMamNon\\Project\\MNHP\\src\\main\\resources\\hp\\mnhp\\IMG\\" + cb.getIdCBNV() + ".jpg");
            if (newname.exists()) {
                newname.delete();
            }
            old.renameTo(newname);
            System.out.println("xong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delIMG(String id) {
        File f = new File("D:\\Projects\\IntelliJ\\QuanLyMamNon\\Project\\MNHP\\src\\main\\resources\\hp\\mnhp\\IMG\\" + id + ".jpg");
        try {
            boolean i = f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isExisted(String id) {
        File f = new File("D:\\Projects\\IntelliJ\\QuanLyMamNon\\Project\\MNHP\\src\\main\\resources\\hp\\mnhp\\IMG\\" + id + ".jpg");
        return f.exists();
    }

    public static void main(String[] args) {
    }

}
