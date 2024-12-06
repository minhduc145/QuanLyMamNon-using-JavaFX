package hp.mnhp;

import DAO.*;
import Model.NhanVien;
import Model.LopModel;
import Model.User;
import Model.hsModel;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Styles;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LopChitiet implements Initializable {
    Integer i = null;
    List<hsModel> hsTemp = new ArrayList<>();
    LopModel lastObj = null;
    LopDao ldao = new LopDao();
    hsDao hdao = new hsDao();
    CBNVDao nvdao = new CBNVDao();
    List<LopModel> dslop = ldao.getDSLop();
    List<NhanVien> dsGVchuaCoLop = new ArrayList<>();


    @FXML
    AnchorPane ap;
    @FXML
    ListView<LopModel> list;
    @FXML
    Button btnMoThem, btnOK, chs, addtoclass, delfromclass, themlop, xoalop, suaBtn, luuBtn, huyBtn, sBtn, reload;
    @FXML
    TextField txttenlop, search;
    @FXML
    Text idlop, tenlop, sl1, sl2;
    @FXML
    TabPane t;
    @FXML
    TableView<hsModel> hstab;
    @FXML
    TableColumn gvch, cotid, ch, stths, hths, nshs, gths, sttgv, htgv, tkgv, nsgv;
    @FXML
    TableView<NhanVien> gvtab;

    void setGVCN(String idlop, TableView<NhanVien> GVCNTable) {
        List<NhanVien> dsChon = new ArrayList<>();
        List<NhanVien> dsKhongChon = new ArrayList<>();
        List<NhanVien> tableList = GVCNTable.getItems();
        // lọc gv được chọn và không được chọn
        for (int i = 0; i < tableList.size(); i++) {
            NhanVien nv = tableList.get(i);
            if (nv.getSelect().isSelected()) dsChon.add(nv);
            else dsKhongChon.add(nv);
        }
        /// kiểm tra số lượng giáo viên được chọn
        if (dsChon.size() < 1) {
            AlertMessage.errorBox("Lỗi! Cần có ít nhất 1 GVCN");
        } else if (dsChon.size() > 3) {
            AlertMessage.errorBox("Lỗi! tối đa không quá 3 GVCN");
        } else {
            Connection cn = (DbHelper.getInstance()).getConnection();
            try {
                for (int i = 0; i < dsKhongChon.size(); i++) { /// bỏ gv không dc chọn ra khỏi lớp
                    NhanVien nv = dsKhongChon.get(i);
                    String SQL = String.format("update CBNV set idLop = NULL where idCBNV = '%s'", nv.getIdCBNV());
                    cn.prepareStatement(SQL).executeUpdate();
                }
                for (int i = 0; i < dsChon.size(); i++) { /// đặt gv dc chọn chủ nhiệm lớp
                    NhanVien nv = dsChon.get(i);
                    String SQL = String.format("update CBNV set idLop = '%s' where idCBNV = '%s'", idlop, nv.getIdCBNV());
                    cn.prepareStatement(SQL).executeUpdate();
                }
            } catch (Exception e) {
                AlertMessage.errorBox("Lỗi không xđ: " + e.getMessage());
                return;
            }
            AlertMessage.infoBox("Phân lớp cho GVCN thành công", "Xong");

        }
    }


    void setThemGVCN(boolean i) {
        btnMoThem.setVisible(!i);
        btnOK.setVisible(i);
        suaBtn.setVisible(!i);
        gvch.setVisible(i);
        list.setDisable(i);
    }

    @FXML
    void onclickMoThem() {
        i = list.getSelectionModel().getSelectedIndex();
        setThemGVCN(true);
        dsGVchuaCoLop = nvdao.getGVchuacoLop();
        gvtab.getItems().addAll(dsGVchuaCoLop);
    }

    @FXML
    void onclickOKbtn() {
        setThemGVCN(false);
        setGVCN(list.getItems().get(i).getId(), gvtab);
        dsGVchuaCoLop = nvdao.getGVchuacoLop();
        setReload();
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
    void onClickexit() {
        Platform.exit();
    }

    @FXML
    void search() {
        if (search.getText() != null || !search.getText().toString().isEmpty()) {
            List<LopModel> dstk = new ArrayList<>();
            for (LopModel scb : dslop) {
                if (scb.getTenLop().toLowerCase().contains(search.getText().trim().toLowerCase())) {
                    dstk.add(scb);
                }
            }
            list.getItems().setAll(dstk);
        } else {
            list.getItems().setAll(dslop);
        }
    }

    @FXML
    void setReload() {
        dslop = ldao.getDSLop();
        list.getItems().setAll(dslop);
        search.setPromptText("Tìm trong " + dslop.size() + " Lớp học");
        if (i != null) {
            list.getSelectionModel().select(i);
        }
        i = null;
        hstab.getItems().setAll(getdshs());
    }

    @FXML
    void onClickSuaBtn() {
        i = list.getSelectionModel().getSelectedIndex();

        chs.setVisible(true);
        list.setDisable(true);
        tenlop.setVisible(false);
        txttenlop.setVisible(true);
        suaBtn.setVisible(false);
        luuBtn.setVisible(true);
        huyBtn.setVisible(true);
        delfromclass.setVisible(true);
        addtoclass.setVisible(true);
        ch.setVisible(true);
    }

    @FXML
    void onClickLuuBtn() {
        if (AlertMessage.isConfirmedBox(null, "Xác nhận lưu", "Xác nhận lưu")) {
            try {
                ldao.updateLop(list.getSelectionModel().getSelectedItem().getId(), idlop.getText().toString(), txttenlop.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage().contains("Violation of PRIMARY KEY")) {
                    AlertMessage.erBox("Thử với tên khác", "Loi", "Lớp đã tồn tại (trùng mã lớp)");
                } else {
                    AlertMessage.erBox("Thử với tên khác", "Loi", "Loi them");
                }
            }
            if (hsTemp != null) try {
                for (hsModel hs : hsTemp) {
                    if (hs.getSelect().isSelected()) {
                        hdao.setLopchoHS(hs.getId(), null);
                    }
                }
            } catch (Exception e) {
                AlertMessage.erBox(null, "Loi", "Lỗi xóa");
            }
        }
        chs.setVisible(false);
        setThemGVCN(false);

        list.setDisable(false);
        tenlop.setVisible(true);
        txttenlop.setVisible(false);
        suaBtn.setVisible(true);
        luuBtn.setVisible(false);
        huyBtn.setVisible(false);
        ch.setVisible(false);
        delfromclass.setVisible(false);
        addtoclass.setVisible(false);
        setReload();
    }

    @FXML
    void onClickHuyBtn() {
        setReload();
        setThemGVCN(false);
        chs.setVisible(false);
        list.setDisable(false);
        tenlop.setVisible(true);
        txttenlop.setVisible(false);
        suaBtn.setVisible(true);
        luuBtn.setVisible(false);
        huyBtn.setVisible(false);
        ch.setVisible(false);
        delfromclass.setVisible(false);
        addtoclass.setVisible(false);
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<NhanVien, NhanVien> unfriendCol = new TableColumn<>("");
        gvtab.getColumns().add(unfriendCol);
        chs.setVisible(false);
        if (User.idQuyen.equals("0") || User.idQuyen.equals("1")) {
            chs.setDisable(false);
            suaBtn.setDisable(false);
            themlop.setDisable(false);
            xoalop.setDisable(false);
            btnMoThem.setDisable(false);
        }
        ap.sceneProperty().addListener((obs, oldScene, newScene) -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(e -> {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("DSLop.fxml"));
                        Stage stage1 = new Stage();
                        stage1.setTitle("Quản lý Lớp");
                        stage1.setScene(new Scene(root));
                        stage1.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
                        stage1.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ap.getScene().getWindow().hide();
                });
            });
        });
        t.getStyleClass().add(Styles.BORDERED);
        search.getStyleClass().addAll(Styles.ROUNDED);
        Image image = new Image(getClass().getResourceAsStream("UI/loupe.png"), sBtn.getWidth(), sBtn.getHeight(), false, true);
        sBtn.setGraphic(new imgFotBtn().getImg(sBtn, image, 20, 20));
        sBtn.getStyleClass().addAll(Styles.ROUNDED, Styles.BUTTON_ICON);
        search.setPromptText("Tìm trong " + dslop.size() + " Lớp học");
        huyBtn.getStyleClass().add(Styles.DANGER);
        luuBtn.getStyleClass().add(Styles.SUCCESS);
        list.getItems().setAll(dslop);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LopModel>() {
            @Override
            public void changed(ObservableValue<? extends LopModel> observable, LopModel oldValue, LopModel newValue) {


                LopModel lop = list.getSelectionModel().getSelectedItem();

                if (lop != null) {
                    dsGVchuaCoLop = nvdao.getGVchuacoLop();

                    if (!User.idQuyen.equals("0")) {
                        suaBtn.setDisable(true);
                        for (LopModel.GVCN cb : lop.getDsGVCN()) {
                            if (User.idCBNV.equals(cb.getId()) || User.idQuyen.equals("0")) {
                                suaBtn.setDisable(false);
                                break;
                            }
                        }
                    }
                    txttenlop.setText(lop.getTenLop());
                    idlop.setText(lop.getId());
                    tenlop.setText(lop.getTenLop());
                    sl1.setText(String.valueOf(lop.getSotre()));
                    sl2.setText(String.valueOf(lop.getDsGVCN().size()));

                    List<hsModel> hsl = getdshs();
                    stths.setSortable(false);
                    stths.setCellFactory(new LineNumbersCellFactory<>());
                    hths.setCellValueFactory(new PropertyValueFactory<hsModel, String>("hoten"));
                    nshs.setCellValueFactory(new PropertyValueFactory<hsModel, String>("ngaysinh"));
                    gths.setCellValueFactory(new PropertyValueFactory<hsModel, String>("lanam"));
                    ch.setCellValueFactory(new PropertyValueFactory<hsModel, CheckBox>("select"));
                    cotid.setCellValueFactory(new PropertyValueFactory<hsModel, String>("id"));
                    hstab.getItems().setAll(hsl);
                    List<NhanVien> dsgv = new ArrayList<>();
                    try {
                        Connection cn = (DbHelper.getInstance()).getConnection();
                        String SQL = "SELECT *\n" + "FROM     CBNV\n" + "where idLop = ?";
                        PreparedStatement stmt = cn.prepareStatement(SQL);
                        stmt.setString(1, lop.getId());
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            NhanVien h = new NhanVien();
                            h.setHoten(rs.getString("hoten"));
                            h.setIdCBNV(rs.getString("idcbnv"));
                            if (rs.getDate("ngaysinh") != null) {
                                LocalDate newDate2 = rs.getDate("ngaysinh").toLocalDate();
                                h.setNgaySinh(newDate2);
                            }
                            h.setSelect(true);
                            dsgv.add(h);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sttgv.setSortable(false);
                    sttgv.setCellFactory(new LineNumbersCellFactory<>());
                    htgv.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("hoten"));
                    nsgv.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("NgaySinh"));
                    tkgv.setCellValueFactory(new PropertyValueFactory<NhanVien, String>("idCBNV"));
                    gvch.setCellValueFactory(new PropertyValueFactory<NhanVien, CheckBox>("select"));
                    gvtab.getItems().setAll(dsgv);
                }
            }


        });
        themlop.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("themLop.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Thêm Lớp");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
                    stage.showAndWait();
                    if(ThemLop.result == true){
                        setReload();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        xoalop.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (AlertMessage.isConfirmedBox(null, "Xác nhận xóa lớp ", "Xác nhận xóa lớp " + list.getSelectionModel().getSelectedItem().getTenLop())) {
                    LopModel l = list.getSelectionModel().getSelectedItem();
                    try {
                        ldao.xoaLop(l);
                        list.getItems().remove(l);
                        search.setPromptText("Tìm trong " + list.getItems().size() + " Lớp học");
                    } catch (Exception e) {

                    }
                }

            }
        });

        suaBtn.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {

            }
        });
        txttenlop.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!txttenlop.getText().isEmpty()) {
                    idlop.setText(txttenlop.getText().toString().replaceAll("\\s", "")); // id duoc tao tu dong tu ten lop moi
                } else idlop.setText(null);
            }
        });
        delfromclass.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                for (hsModel hs : hstab.getItems()) {
                    if (hs.getSelect().isSelected()) {
                        hsTemp.add(hs);
                    }
                }
                for (hsModel hs : hsTemp) {
                    hstab.getItems().remove(hs);
                }
            }
        });
        addtoclass.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                ThemHSvaoLop._lopht = list.getSelectionModel().getSelectedItem();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ThemHSvaoLop.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Thêm HS");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chs.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                List<hsModel> hsl = new ArrayList<>();
                chuyenLop.setLopht(list.getSelectionModel().getSelectedItem());
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("chuyenLop.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Chuyển Lớp HS");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private List<hsModel> getdshs() {
        List<hsModel> hsl = new ArrayList<>();
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT Tre.*\n" + "FROM     Tre\n" + "where idLop = ?";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setString(1, list.getSelectionModel().getSelectedItem().getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hsModel h = new hsModel();
                h.setHoten(rs.getString("hoten"));
                h.setId(rs.getString("idtre"));
                h.setLanam(rs.getBoolean("lanam"));
                if (rs.getDate("ngaysinh") != null) {
                    LocalDate newDate2 = rs.getDate("ngaysinh").toLocalDate();
                    h.setNgaysinh(newDate2);
                }
                hsl.add(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hsl;
    }
}

