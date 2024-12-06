package hp.mnhp;

import DAO.*;
import Model.*;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Styles;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HS implements Initializable {
    List<hsModel> ds = new ArrayList<>();
    List<danhhieuModel> tempdh = new ArrayList<>();
    hsDao hdao = new hsDao();
    linhtinhDao linhtinh = new linhtinhDao();
    int lastIndex;
    danhhieuModel temp;
    hsModel temp1;
    LopModel lastpick;
    @FXML
    Text gvcn;
    @FXML
    Button reloadbtn, xoabtn, suaBtn, luuBtn, huyBtn, sBtn, m1, p1, add2, del2;
    @FXML
    ListView<hsModel> list;
    @FXML
    TableView<danhhieuModel> listdh;
    @FXML
    TableView<phModel> phtable;
    @FXML
    TextField search, hoten, noisinh, dc, nnh, ename, inputten, inputvt, inputsdt, inputdc, inputnn;
    @FXML
    ComboBox<String> gt;
    @FXML
    ComboBox<String> tt;
    @FXML
    ComboBox<LopModel> lopds, cblop;
    @FXML
    ComboBox<danhhieuModel> dhds;
    @FXML
    DatePicker ngs;
    @FXML
    TableColumn col1, col2, phcol1, phcol2, phcol3, phcol4, phcol5;
    @FXML
    MenuItem themhsbtn;

    @FXML
    void setThemhsbtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BangTTHS.fxml"));
            Parent root = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            BangTTHS ctrl = loader.getController();
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("Người dùng đã chọn OK.");
                reload();
            } else {
                System.out.println("Người dùng đã chọn Cancel hoặc đóng dialog.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickxoa() {
        if (hdao.xoaHS(list.getItems().get(lastIndex).getId())) {
            lastIndex = -1;
            reload();
            picklop();
        }
    }

    @FXML
    void picklop() {
        if (cblop.getSelectionModel().getSelectedItem() != null && cblop.getSelectionModel().getSelectedItem().getId() != null) {
            ds = hdao.getdshs(cblop.getSelectionModel().getSelectedItem().getId());
            list.getItems().setAll(ds);
            search.setPromptText("Tìm trong " + ds.size() + " Học sinh");

        } else {
            ds = hdao.getdshs();
            list.getItems().setAll(ds);
            search.setPromptText("Tìm trong " + ds.size() + " Học sinh");


        }
    }

    void setTable1(List<danhhieuModel> ds) {
        if (list.getSelectionModel().getSelectedItem() != null) {
            col1.setCellValueFactory(new PropertyValueFactory<danhhieuModel, String>("nam"));
            col2.setCellValueFactory(new PropertyValueFactory<danhhieuModel, String>("danhhieu"));
            listdh.getItems().setAll(ds);
        }
    }

    void setTable2(List<phModel> ds) {
        if (list.getSelectionModel().getSelectedItem() != null) {

            phcol1.setCellValueFactory(new PropertyValueFactory<phModel, String>("hoten"));
            phcol2.setCellValueFactory(new PropertyValueFactory<phModel, String>("vaitro"));
            phcol3.setCellValueFactory(new PropertyValueFactory<phModel, String>("sdt"));
            phcol4.setCellValueFactory(new PropertyValueFactory<phModel, String>("diachi"));
            phcol5.setCellValueFactory(new PropertyValueFactory<phModel, String>("nghe"));
            phtable.getItems().setAll(ds);
        }
    }

    void setField(hsModel hs) {

        gvcn.setText(hs.getGvcnString());
        hoten.setText(hs.getHoten());
        noisinh.setText(hs.getNoisinh());
        dc.setText(hs.getDiachi());
        nnh.setText(hs.getNamnhaphoc());
        ngs.setValue(hs.getNgaysinh());
        suaBtn.setDisable(true);

        /**********chon2************/

        if (User.idQuyen.equals("0")) {
            suaBtn.setDisable(false);
            xoabtn.setDisable(false);

        } else {
            for (LopModel.GVCN e : hs.getGvcn()) {
                if (e != null && (User.idCBNV.equals(e.getId()))) {
                    if (!luuBtn.isVisible()) {
                        suaBtn.setVisible(true);
                        suaBtn.setDisable(false);
                        xoabtn.setDisable(false);
                        break;
                    }
                } else {
                    if (!luuBtn.isVisible()) {
                        suaBtn.setVisible(false);
                        suaBtn.setDisable(true);
                        xoabtn.setDisable(true);
                        break;
                    }
                }
            }
        }
        /**********chon2************/

        gt.getItems().setAll("Nam", "Nữ");
        if (hs.isLanam()) {
            gt.setValue("Nam");
        } else {
            gt.setValue("Nữ");
        }

        lopds.getItems().setAll(linhtinh.dsl);
        for (LopModel lop : linhtinh.dsl) {
            if (hs.getIdLop() != null && hs.getIdLop().equals(lop.getId())) {
                lopds.setValue(lop);
                break;
            }
            lopds.setValue(lopds.getItems().get(0));

        }


        tt.getItems().setAll("Đang theo học", "Không đang theo học");
        if (hs.isDangtheohoc() == true) {
            tt.setValue("Đang theo học");
        } else {
            tt.setValue("Không đang theo học");
        }
        setTable1(hs.getDanhhieu());
        dhds.getItems().setAll(linhtinh.dsdh);
        setTable2(hs.getPh());

    }

    void select() {
        hsModel hs;
        if (lastIndex == -1) hs = null;
        else hs = list.getItems().get(lastIndex);
        if (hs != null) {
            list.getSelectionModel().select(lastIndex);
            list.getFocusModel().focus(lastIndex);
            setField(hs);
            setTable1(hs.getDanhhieu());
            setTable2(hs.getPh());
        } else {
            hsModel hs1 = new hsModel();
            setField(hs1);
        }
    }

    void reload() {
        ds = hdao.getdshs();
        list.getItems().setAll(ds);
        search.setPromptText("Tìm trong " + ds.size() + " Học sinh");

    }

    @FXML
    void xoa2() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Xác nhận xóa");
        alert.showAndWait();
        if (alert.getResult().getButtonData().isDefaultButton()) {
            if (phtable.getItems().size() > 1) {
                phModel ph = phtable.getSelectionModel().getSelectedItem();
                try {
                    Connection cn = (DbHelper.getInstance()).getConnection();
                    String SQL = "delete from Phuhuynh where idTre = ? and vaitro = ?";
                    PreparedStatement stmt = cn.prepareStatement(SQL);
                    stmt.setString(1, ph.getId());
                    stmt.setString(2, ph.getVaitro().trim());
                    stmt.executeUpdate();
                } catch (Exception e) {

                }
                phtable.getItems().remove(ph);
                reload();
                select();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("HS cần có ít nhất 1 Phụ Huynh");
                alert.showAndWait();
            }
        }

    }

    @FXML
    void onReload() {
        reload();

    }

    @FXML
    void them2() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Xác nhận Thêm");
        alert.showAndWait();
        if (alert.getResult().getButtonData().isDefaultButton()) {
            if (!inputten.getText().isEmpty() && !inputvt.getText().isEmpty() && !inputsdt.getText().isEmpty() && !inputnn.getText().isEmpty() && !inputdc.getText().isEmpty()) {
                phModel ph = new phModel();
                ph.setId(list.getSelectionModel().getSelectedItem().getId());
                ph.setHoten(inputten.getText());
                ph.setVaitro(inputvt.getText());
                ph.setSdt(inputsdt.getText());
                ph.setNghe(inputnn.getText());
                ph.setDiachi(inputdc.getText());
                phcol1.setCellValueFactory(new PropertyValueFactory<phModel, String>("hoten"));
                phcol2.setCellValueFactory(new PropertyValueFactory<phModel, String>("vaitro"));
                phcol3.setCellValueFactory(new PropertyValueFactory<phModel, String>("sdt"));
                phcol4.setCellValueFactory(new PropertyValueFactory<phModel, String>("diachi"));
                phcol5.setCellValueFactory(new PropertyValueFactory<phModel, String>("nghe"));
                phtable.getItems().add(ph);
                try {
                    Connection cn = (DbHelper.getInstance()).getConnection();
                    String SQL = "insert into PhuHuynh\n" +
                            "values(?,?,?,?,?,?)";
                    PreparedStatement stmt = cn.prepareStatement(SQL);
                    stmt.setString(1, ph.getId());
                    stmt.setString(2, ph.getVaitro().trim());
                    stmt.setString(3, ph.getHoten().trim());
                    stmt.setString(4, ph.getDiachi());
                    stmt.setString(5, ph.getSdt());
                    stmt.setString(6, ph.getNghe());
                    stmt.executeUpdate();
                } catch (Exception e) {

                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Điền đầy đủ các trường");
                alert.showAndWait();
            }
            reload();
            select();
        }
    }

    @FXML
    void editbtnnotshow() {
        if (suaBtn != null) {
            suaBtn.setVisible(false);

        }
    }

    @FXML
    void editbtnshow() {
        if (suaBtn != null) {
            suaBtn.setVisible(true);

        }
    }

    @FXML
    void xoa1() {
        if (!listdh.getSelectionModel().isEmpty() && !listdh.getSelectionModel().getSelectedItems().isEmpty()) {
            hsModel hs = list.getSelectionModel().getSelectedItem();
            danhhieuModel e = listdh.getSelectionModel().getSelectedItem();
            temp = e;
            temp1 = hs;
            listdh.getItems().remove(e);
        }

    }

    @FXML
    void xoa1(hsModel hs, danhhieuModel e) throws Exception {


        Connection cn = (DbHelper.getInstance()).getConnection();
        String SQL = "delete from XepLoai where idTre = ? and year(NamHoc) = ?";
        PreparedStatement stmt = cn.prepareStatement(SQL);
        stmt.setString(1, hs.getId());
        stmt.setString(2, e.getNam());
        stmt.executeUpdate();


    }


    @FXML
    void them1() {
        if (!ename.getText().isEmpty() && !dhds.getSelectionModel().isEmpty()) {
            danhhieuModel dhm = new danhhieuModel();
            dhm.setId(list.getItems().get(lastIndex).getId());
            dhm.setIddh(dhds.getSelectionModel().getSelectedItem().getIddh());
            dhm.setDanhhieu(dhds.getSelectionModel().getSelectedItem().getDanhhieu());
            dhm.setNam(ename.getText());
            tempdh.add(dhm);
            col1.setCellValueFactory(new PropertyValueFactory<danhhieuModel, String>("nam"));
            col2.setSortable(false);
            col2.setCellValueFactory(new PropertyValueFactory<danhhieuModel, String>("danhhieu"));
            listdh.getItems().add(dhm);
        }
    }

    boolean them1(List<danhhieuModel> ds) throws Exception {
        boolean i = true;

        for (danhhieuModel d : ds) {
            for (char ch : d.getNam().toCharArray()) {
                if (Character.isLetter(ch)) {
                    throw new Exception();
                }
            }
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "insert Xeploai values(?,?,?)";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setString(1, d.getIddh());
            stmt.setString(2, d.getId());
            stmt.setString(3, d.getNam());
            if (stmt.executeUpdate() != 1) {
                return false;
            }

        }
        return i;
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

    void setEditable(boolean i) {
        hoten.setEditable(i);
        noisinh.setEditable(i);
        dc.setEditable(i);
        nnh.setEditable(i);
        ename.setVisible(i);
        dhds.setVisible(i);
        m1.setVisible(i);
        p1.setVisible(i);

    }

    @FXML
    void onClickhuyBtn() {
        list.setDisable(false);
        huyBtn.setVisible(false);
        luuBtn.setVisible(false);
        suaBtn.setVisible(true);
        setEditable(false);
        reload();
        select();
    }

    @FXML
    void onClickexit() {
        Platform.exit();
    }

    @FXML
    void onClickluuBtn() {
        list.setDisable(false);
        boolean ch = false;
        try {
            if (temp != null && temp1 != null) {
                xoa1(temp1, temp);
                temp1 = null;
                temp = null;
                ch = true;
            }
            if (!tempdh.isEmpty()) {
                if (them1(tempdh)) ch = true;
                else throw new Exception();
                tempdh.clear();
            }
            hsModel hs = list.getSelectionModel().getSelectedItem();
            ch = hdao.upHs(hs.getId(), hoten.getText(), lopds.getSelectionModel().getSelectedItem().getId(), dc.getText(), nnh.getText(), gt.getSelectionModel().getSelectedItem().equals("Nam"), tt.getSelectionModel().getSelectedItem().equals("Đang theo học"), ngs.getValue(), noisinh.getText());
            if (ch == true) {
                AlertMessage.infoBox(null, "Thông báo", "Cập nhật thành công");
                reload();
                select();
                setEditable(false);
                huyBtn.setVisible(false);
                luuBtn.setVisible(false);
                suaBtn.setVisible(true);
            }

        } catch (Exception e) {
            AlertMessage.erBox(null, "Thông báo", "Cập nhật không thành công");
            reload();
            select();
        }


    }

    @FXML
    void onClicksuaBtn() {
        list.setDisable(true);
        huyBtn.setVisible(true);
        luuBtn.setVisible(true);
        suaBtn.setVisible(false);
        setEditable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (User.idQuyen.equals("0") || User.idQuyen.equals("1") || User.idChucVu.equals("gv")) {
            themhsbtn.setDisable(false);
        }
        linhtinh.load();
        ngs.setConverter(linhtinh.datePickerFormatter(ngs));

        cblop.getItems().setAll(linhtinh.dsl);
        suaBtn.getStyleClass().add(Styles.ACCENT);

        huyBtn.getStyleClass().add(Styles.DANGER);
        luuBtn.getStyleClass().add(Styles.SUCCESS);
        ds = hdao.getdshs();
        search.setPromptText("Tìm trong " + ds.size() + " Học sinh");

        list.getItems().setAll(ds);
        search.getStyleClass().addAll(
                Styles.ROUNDED
        );
        sBtn.getStyleClass().addAll(
                Styles.ROUNDED, Styles.BUTTON_ICON
        );
        Image image = new Image(getClass().getResourceAsStream("UI/loupe.png"), sBtn.getWidth(), sBtn.getHeight(), false, true);
        sBtn.setGraphic(new imgFotBtn().getImg(sBtn, image, 20, 20));
        setEditable(false);
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                lastIndex = list.getSelectionModel().getSelectedIndex();
                select();
            }
        });
        sBtn.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (search.getText() != null || !search.getText().toString().isEmpty()) {
                    List<hsModel> dstk = new ArrayList<>();
                    for (hsModel scb : ds) {
                        if (scb.getHoten().toLowerCase().contains(search.getText().trim().toLowerCase())) {
                            dstk.add(scb);
                        }
                    }
                    list.getItems().setAll(dstk);
                } else {
                    list.getItems().setAll(ds);
                }


            }
        });

    }
}
