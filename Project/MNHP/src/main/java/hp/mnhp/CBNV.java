package hp.mnhp;

import DAO.*;
import DAO.CBNVDao;
import DAO.linhtinhDao;
import Model.User;
import Model.*;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.PrimerLight;
import atlantafx.base.theme.Styles;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class CBNV implements Initializable {
    linhtinhDao linhtinh = new linhtinhDao();
    CBNVDao cbdao = new CBNVDao();
    List<NhanVien> ds = new ArrayList<>();
    List<String> link = new ArrayList<>();
    int lastIndex;

    @FXML
    ImageView img;
    @FXML
    ListView<NhanVien> list;
    @FXML
    Text id, lopcntitle;
    @FXML
    TextField hsl, tdhv, search, hoten, tn, noisinh, dc, sdt, email, cccd, editpw;
    @FXML
    ComboBox<String> gt;
    @FXML
    ComboBox<QuyenModel> quyen;
    @FXML
    ComboBox<ChucVuModel> cv;
    @FXML
    ComboBox<TrangThaiModel> tt;
    @FXML
    ComboBox<LopModel> lopcn;
    @FXML
    Button reload, themnv, xoanv, suaBtn, luuBtn, huyBtn, sBtn;
    @FXML
    MenuItem cc, bl;
    @FXML
    DatePicker date, bd;


    @FXML
    void setReload() {
        ds = cbdao.getDSCB();
        list.getItems().setAll(ds);
        search.setPromptText("Tìm trong " + ds.size() + " CBNV.");
        for (NhanVien cb : ds) {
            File f = new File("D:\\Projects\\IntelliJ\\QuanLyMamNon\\Project\\MNHP\\src\\main\\resources\\hp\\mnhp\\IMG\\" + cb.getIdCBNV() + ".jpg");
            if (f.exists()) {
                cb.setImg(new Image(getClass().getResourceAsStream("IMG/" + cb.getIdCBNV() + ".jpg")));
            }
        }
    }

    void setEditable(boolean i) {
        hoten.setEditable(i);
        noisinh.setEditable(i);
        tn.setEditable(i);
        dc.setEditable(i);
        sdt.setEditable(i);
        email.setEditable(i);
        cccd.setEditable(i);
        date.setEditable(i);
        bd.setEditable(i);
        editpw.setEditable(i);
        tdhv.setEditable(i);
        hsl.setEditable(i);
    }

    @FXML
    void onClickhuyBtn() {
        huyBtn.setVisible(false);
        luuBtn.setVisible(false);
        suaBtn.setVisible(true);
        setEditable(false);
        list.setDisable(false);
    }

    @FXML
    void onClicklogout() {

        List<Window> open = Stage.getWindows();
        try {
            for (Window w : open) {
                Stage s = (Stage) w;
                s.close();
                for (Window w1 : open) {
                    Stage s1 = (Stage) w1;
                    s1.close();
                }
            }
        } catch (Exception e) {
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hello");
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void ChamCong() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("chamCong1.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Chấm công");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.getScene().getStylesheets().add(new PrimerLight().getUserAgentStylesheet());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void bangLuong() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("bangLuong.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Chấm công");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
//            stage.getScene().getStylesheets().add(new PrimerLight().getUserAgentStylesheet());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickexit() {
//        List<Window> open = (List<Window>) Stage.getWindows().stream();
//        for (Window w : open) {
        Platform.exit();
//        }
    }

    @FXML
    void onClickluuBtn() {

        NhanVien c = list.getSelectionModel().getSelectedItem();
        boolean i = cbdao.updateCBNV(c.getIdCBNV(), quyen.getSelectionModel().getSelectedItem().getId(), editpw.getText(), hoten.getText(), cv.getSelectionModel().getSelectedItem().getId(), noisinh.getText(), tn.getText(), hsl.getText(), dc.getText(), sdt.getText(), email.getText(), date.getValue(), cccd.getText(), tt.getSelectionModel().getSelectedItem().getId(), gt.getSelectionModel().getSelectedItem().toString(), lopcn.getSelectionModel().getSelectedItem().getId(), bd.getValue(), tdhv.getText());
        if (i) {
            ds = new CBNVDao().getDSCB();
            list.getItems().setAll(ds);
            setEditable(false);
            huyBtn.setVisible(false);
            luuBtn.setVisible(false);
            AlertMessage.infoBox(null, "Thông báo", "Cập nhật thành công");
            suaBtn.setVisible(true);
            select();
        } else {
            AlertMessage.erBox(null, "Thông báo", "Cập nhật không thành công");
            setEditable(true);
            huyBtn.setVisible(true);
            luuBtn.setVisible(true);
            select();
        }
        list.setDisable(false);


    }

    @FXML
    void onClicksuaBtn() {
        list.setDisable(true);
        huyBtn.setVisible(true);
        luuBtn.setVisible(true);
        suaBtn.setVisible(false);
        setEditable(true);
    }

    void setField(NhanVien cb) {
        if (!PickAFile.isExisted(cb.getIdCBNV())) {
            int random = 0 + (int) ((3 - 0 + 1) * Math.random());
            String url = link.get(random);
            Image a = new Image(getClass().getResourceAsStream(url));
            img.setImage(a);
        } else {
            img.setImage(cb.getImg());
        }

        if (cb != null && cb.getIdChucVu().equals("gv")) {
            lopcn.setVisible(true);
            lopcntitle.setVisible(true);
        } else {
            lopcn.setVisible(false);
            lopcntitle.setVisible(false);
        }

        if (cb != null && (User.idCBNV.equals(cb.getIdCBNV()) || User.idQuyen.equals("0"))) {

            if (!luuBtn.isVisible()) {
                suaBtn.setVisible(true);
                suaBtn.setDisable(false);
            }
            editpw.setText(cb.getMatKhau());
        } else {
            suaBtn.setDisable(true);
            editpw.setText(null);
        }
        if (cb.isGTNam()) {
            gt.setValue("Nam");
        } else {
            gt.setValue("Nữ");
        }

        for (QuyenModel q : linhtinh.dsq) {
            if (cb.getIdQuyen() != null && cb.getIdQuyen().equals(q.getId())) {
                quyen.setValue(q);
                break;
            }
        }

//                lopcn.setText("Khong");

        for (LopModel lop : linhtinh.dsl) {

            if (cb.getIdLop() != null && cb.getIdLop().equals(lop.getId())) {
                lopcn.setValue(lop);
                break;
            }
            lopcn.setValue(lopcn.getItems().get(0));

        }

        for (TrangThaiModel trt : linhtinh.dstt) {
            if (cb.getIdTinhTrang() != null && cb.getIdTinhTrang().equals(trt.getId())) {
                tt.setValue(trt);
                break;
            }
        }

        for (ChucVuModel c : linhtinh.dschv) {
            if (cb.getIdChucVu() != null && cb.getIdChucVu().equals(c.getId())) {
                cv.setValue(c);
                break;
            }
        }
        hsl.setText(Double.toString(cb.getHsl()));
        tdhv.setText(cb.getTDHV());
        date.setValue(cb.getNgayVaoLam());
        bd.setValue(cb.getNgaySinh());
        id.setText(cb.getIdCBNV());
        hoten.setText(cb.getHoten());
        noisinh.setText(cb.getNoiSinh());
        tn.setText(Double.toString(cb.getPctn()));
        dc.setText(cb.getDiaChiTT());
        sdt.setText(cb.getSDT());
        email.setText(cb.getEmail());
        cccd.setText(cb.getSoCCCD());
    }

    void select() {
        if (lastIndex == -1) lastIndex = 0;
        NhanVien cb = list.getItems().get(lastIndex);
        if (cb != null) {
            list.getSelectionModel().select(lastIndex);
            list.getFocusModel().focus(lastIndex);
            setField(cb);
        } else {
            NhanVien cb1 = new NhanVien();
            setField(cb1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setConverter(linhtinh.datePickerFormatter(date));
        search.getStyleClass().addAll(Styles.ROUNDED);
        sBtn.getStyleClass().addAll(Styles.ROUNDED, Styles.BUTTON_ICON);
        Image image = new Image(getClass().getResourceAsStream("UI/loupe.png"), sBtn.getWidth(), sBtn.getHeight(), false, true);
        sBtn.setGraphic(new imgFotBtn().getImg(sBtn, image, 20, 20));
        ds = cbdao.getDSCB();
        search.setPromptText("Tìm trong " + ds.size() + " CBNV.");
        huyBtn.getStyleClass().add(Styles.DANGER);
        luuBtn.getStyleClass().add(Styles.SUCCESS);
        suaBtn.setVisible(false);
        linhtinh.load();
        gt.getItems().add("Nam");
        gt.getItems().add("Nữ");
        quyen.getItems().setAll(linhtinh.dsq);
        tt.getItems().setAll(linhtinh.dstt);
        cv.getItems().setAll(linhtinh.dschv);
        lopcn.getItems().setAll(linhtinh.dsl);
        list.getItems().setAll(ds);
        ListViewSkin<NhanVien> skin = new ListViewSkin<>(list);
        link.add("UI/teacher.png");
        link.add("UI/teacher1.png");
        link.add("UI/teacher2.png");
        link.add("UI/teacher3.png");
        if (User.idQuyen.equals("0") || User.idQuyen.equals("1")) {
            themnv.setDisable(false);
            xoanv.setDisable(false);
            cv.setDisable(false);
            quyen.setDisable(false);
            lopcn.setDisable(false);
        }

        for (NhanVien cb : ds) {
            File f = new File("D:\\Projects\\IntelliJ\\QuanLyMamNon\\Project\\MNHP\\src\\main\\resources\\hp\\mnhp\\IMG\\" + cb.getIdCBNV() + ".jpg");
            if (f.exists()) {
                cb.setImg(new Image(getClass().getResourceAsStream("IMG/" + cb.getIdCBNV() + ".jpg")));
            }
        }

        list.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                lastIndex = list.getSelectionModel().getSelectedIndex();
                select();

            }
        });

        sBtn.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                List<NhanVien> dstk = TimKiembangTen(search.getText().toString(), ds);
                if (dstk != null) {
                    list.getItems().setAll(dstk);
                } else list.getItems().setAll(ds);
            }
        });

        xoanv.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                boolean i = cbdao.xoaNV(list.getSelectionModel().getSelectedItem().getIdCBNV());
                if (i) {
                    list.getItems().remove(list.getSelectionModel().getSelectedItem());
                    lastIndex = 0;
                    select();
                }
            }
        });
        themnv.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("themnV.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Them CBGV, NV");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        img.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (User.idQuyen.equals("0") || User.idCBNV.equals(list.getSelectionModel().getSelectedItem().getIdCBNV())) {
                    NhanVien cb = list.getSelectionModel().getSelectedItem();
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
                    cb.setImg(new Image(f[0].toURI().toString()));
                    img.setImage(cb.getImg());
                }
            }
        });
    }

    /**********chon1************/
//    private List<CBNVModule> TimbangHoten(String keyWord, List<CBNVModule> ds) {
//        List<CBNVModule> dstk = new ArrayList<>();
//        if (!keyWord.isEmpty()) {
//            for (CBNVModule cb : ds) {
//                if (cb.getHoten().toLowerCase().contains(keyWord.trim().toLowerCase())) {
//                    dstk.add(cb);
//                }
//            }
//        } else {
//            dstk = ds;
//        }
//        return dstk;
//    }
    private List<NhanVien> TimbangHoten(String keyWord, List<NhanVien> ds) {
        if (keyWord.isBlank()) {
            AlertMessage.errorBox("Không để trống input");
            return null;
        }
        if (keyWord.matches(".*\\d.*")) {
            AlertMessage.errorBox("Họ Tên không thể chứa số");
            return null;
        }

        keyWord = keyWord.trim().toLowerCase();
        List<NhanVien> dstk = new ArrayList<>();
        for (NhanVien nv : ds)
            if (nv.getHoten().toLowerCase().contains(keyWord)) dstk.add(nv);
        return dstk;
    }


    /**********chon1************/

//    private void TimKiembangHoten(String keyWord) {
//        List<CBNVModule> dstk = new ArrayList<>();
//        boolean laHopLe = true;
//        if (keyWord.matches(".*\\d.*")) {
//            AlertMessage.errorBox("Họ Tên không thể chứa số");
//            laHopLe = false;
//        }
//        if (keyWord.isBlank() == false && laHopLe == true) {
//            for (CBNVModule scb : ds) {
//                if (scb.getHoten().toLowerCase().contains(keyWord.trim().toLowerCase()))
//                    dstk.add(scb);
//            }
//        }
//        list.getItems().setAll(dstk);
//    }
    private List<NhanVien> TimKiembangTen(String keyWord, List<NhanVien> ds) {
        boolean laHopLe = true;
        if (keyWord.isBlank()) {
            AlertMessage.errorBox("Không để trống");
            laHopLe = false;
        } else {
            keyWord = keyWord.trim().toLowerCase();
            if (keyWord.matches(".*\\d.*")) {
                AlertMessage.errorBox("Tên không thể chứa số");
                laHopLe = false;
            }
            if (keyWord.length() > 10) {
                AlertMessage.errorBox("Tên nhập không quá 10 kí tự");
                laHopLe = false;
            }
            for (int i = 0; i < keyWord.length(); i++) {
                char ch = keyWord.charAt(i);
                if (Character.isDigit(ch) == false && Character.isLetter(ch) == false && Character.isWhitespace(ch) == false) {
                    AlertMessage.errorBox("Tên không thể chứa ký tự đặc biệt");
                    laHopLe = false;
                    break;
                }
            }
        }
        if (laHopLe == true) {
            List<NhanVien> dstk = new ArrayList<>();
            for (int i = 0; i < ds.size(); i++) {
                NhanVien nv = ds.get(i);
                String[] HoTenNV = nv.getHoten().split(" ");
                String TenNV = (HoTenNV[HoTenNV.length - 1]);
                if (TenNV.toLowerCase().contains(keyWord)) {
                    dstk.add(nv);
                }
            }
            AlertMessage.infoBox("Tìm kiếm xong", null);
            return dstk;
        } else {
            AlertMessage.errorBox("Tìm kiếm thất bại");
            return null;
        }
    }

    /**********chon1************/

    private void loadIMG(String id) {
        String url = "/IMG/" + id + ".jpg";
        Image a = new Image(getClass().getResourceAsStream(url));
        img.setImage(a);
    }
}
