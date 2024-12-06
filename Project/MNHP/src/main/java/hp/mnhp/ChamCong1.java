package hp.mnhp;

import DAO.DbHelper;
import Model.ChamCongModel;
import DAO.chuyenNgay;
import Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ChamCong1 implements Initializable {
    List<ChamCongModel> dscb = new ArrayList<>();
    Integer thangchon = null;
    Integer ngaychon = null;
    Integer namchon = null;
    int sn = 0;
    @FXML
    Text a;
    @FXML
    Button suabtn, luubtn, huybtn;
    @FXML
    TableView<ChamCongModel> tab;
    @FXML
    ComboBox<datepicker> thang;
    @FXML
    ComboBox<Integer> nam;

    @FXML
    void onClickSuaBtn() {
        for (ChamCongModel c : dscb) {
            for (CheckBox temp : c.getDs()) {
                temp.setDisable(false);
            }
        }
        suabtn.setVisible(false);
        thang.setDisable(true);
        nam.setDisable(true);
        luubtn.setVisible(true);
        huybtn.setVisible(true);
    }

    @FXML
    void onClickHuyBtn() {
        napNgay(thangchon, namchon);
        tab.getItems().setAll(dscb);
        for (ChamCongModel c : dscb) {
            for (CheckBox temp : c.getDs()) {
                temp.setDisable(true);
            }
        }
        suabtn.setVisible(true);
        thang.setDisable(false);
        nam.setDisable(false);
        luubtn.setVisible(false);
        huybtn.setVisible(false);
    }

    @FXML
    void onClickLuuBtn() {
        luuDiLam();
        for (ChamCongModel c : dscb) {
            for (CheckBox temp : c.getDs()) {
                temp.setDisable(true);
            }
        }
        suabtn.setVisible(true);
        thang.setDisable(false);
        nam.setDisable(false);
        luubtn.setVisible(false);
        huybtn.setVisible(false);
    }

    void napNgay(int month, int year) {
        tab.getItems().clear();
        tab.getColumns().clear();
        TableColumn a1 = new TableColumn("ID");
        a1.setPrefWidth(50);
        TableColumn b1 = new TableColumn("Họ Tên");
        b1.setPrefWidth(150);
        a1.setCellValueFactory(new PropertyValueFactory<ChamCongModel, String>("id"));
        b1.setCellValueFactory(new PropertyValueFactory<ChamCongModel, String>("ten"));
        tab.getColumns().add(a1);
        tab.getColumns().add(b1);
        for (int i = 1; i <= chuyenNgay.getDays(month, year); i++) {
            TableColumn a = new TableColumn(String.valueOf(i));
            TableColumn b = new TableColumn(chuyenNgay.getThu(i, month, year));
            a.setPrefWidth(40);
            b.setPrefWidth(40);
            a.getColumns().add(b);
            a.setId("select" + i);
            b.setId("select" + i);
            tab.getColumns().add(a);
        }
        sn = 0;
        for (TableColumn s : tab.getColumns()) {
            if (s.getId() != null && s.getId().contains("select")) {
                TableColumn b = (TableColumn) s.getColumns().get(0);
                if (b.getText().equals("CN") || ((TableColumn) b).getText().equals("T7")) {
                    b.setStyle(".table-view .column-header,\n" +
                            ".table-view .column-header .filler,\n" +
                            ".table-view .column-header-background .filler {\n" +
                            "    -fx-background-color: red;\n" +
                            "}");
                } else {
                    sn++;
                    b.setCellValueFactory(new PropertyValueFactory<ChamCongModel, CheckBox>(s.getId()));
                }
            }
        }
        TableColumn a = new TableColumn("Tổng");
        a.setCellValueFactory(new PropertyValueFactory<ChamCongModel, String>("dem"));
        a.setPrefWidth(100);
        tab.getColumns().add(a);
        napCB();
        napDiLam(thangchon, namchon);
        demDiLam(sn);

    }

    List<ChamCongModel> napCB() {
        dscb = new ArrayList<>();
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "SELECT idCBNV, HoTen from CBNV where year(Ngayvaolam) <= ?";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            stmt.setInt(1, namchon);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChamCongModel cb = new ChamCongModel();
                cb.setId(rs.getString("idcbnv"));
                cb.setTen(rs.getString(2));
                dscb.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dscb;
    }

    void demDiLam(int sn) {
        for (ChamCongModel c : dscb) {
            int i = 0;
            for (CheckBox ch : c.getDs()) {
                if (ch.isSelected())
                    ++i;
            }
            c.setDem(i, sn);
        }
    }

    void napDiLam(Integer thang, Integer nam) {
        for (ChamCongModel c : dscb) {
            c.napDiLam(thang, nam);
        }
    }

    void luuDiLam() {
        for (ChamCongModel c : dscb) {
            try {
                Connection cn = (DbHelper.getInstance()).getConnection();
                String SQL = "DELETE FROM [dbo].[CongNV]\n" +
                        "      WHERE idCBNV = ? and month(Ngay) = ? and year(Ngay)=?";
                PreparedStatement stmt = cn.prepareStatement(SQL);
                stmt.setString(1, c.getId());
                stmt.setInt(2, thangchon);
                stmt.setInt(3, namchon);
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (CheckBox ch : c.getDs()) {
                if (ch.isSelected()) {
                    try {
                        Connection cn = (DbHelper.getInstance()).getConnection();
                        String SQL = "INSERT INTO NgayCong VALUES (?)";
                        PreparedStatement stmt = cn.prepareStatement(SQL);
                        stmt.setString(1, String.valueOf(namchon) + "-" + String.valueOf(thangchon) + "-" + String.valueOf(ch.getId()));
                        stmt.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Connection cn = (DbHelper.getInstance()).getConnection();
                        String SQL = "INSERT INTO [dbo].[CongNV]\n" +
                                "           ([Ngay]         \n" +
                                "           ,[idCBNV]\n" +
                                "           ,[diLam])\n" +
                                "     VALUES (?,?,?)";
                        PreparedStatement stmt = cn.prepareStatement(SQL);
                        stmt.setString(1, String.valueOf(namchon) + "-" + String.valueOf(thangchon) + "-" + String.valueOf(ch.getId()));
                        stmt.setBoolean(3, true);
                        stmt.setString(2, c.getId());
                        stmt.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        demDiLam(sn);
        tab.getItems().setAll(dscb);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (User.idQuyen.equals("0") || User.idQuyen.contains("TT")) {
            suabtn.setDisable(false);
        }
        Date curDate = new Date();
        LocalDate localDate = curDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int day = localDate.getDayOfMonth();
        String thu = chuyenNgay.getThu(day, month, year) + ", " + day + "/" + month + "/" + year;
        a.setText(a.getText() + ": " + thu);
        for (int i = 1; i <= 12; i++) {
            thang.getItems().add(new datepicker("Tháng " + i, i));
            if (i == month)
                thang.getSelectionModel().select(thang.getItems().get(i - 1));
        }
        int namvaosomnhat = 2000;
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "select top 1 year(Ngayvaolam) from CBNV order by year(NgayVaoLam) asc ";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                namvaosomnhat = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = namvaosomnhat; i <= localDate.getYear(); i++) {
            nam.getItems().add(i);
        }
        nam.getSelectionModel().select((Integer) localDate.getYear());
        thangchon = month;
        namchon = year;
        napNgay(month, year);
        tab.getItems().setAll(dscb);
        thang.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<datepicker>() {
            @Override
            public void changed(ObservableValue<? extends datepicker> observable, datepicker oldValue, datepicker newValue) {
                thangchon = newValue.getThang();
                napNgay(thangchon, namchon);
                tab.getItems().setAll(dscb);
            }
        });
        nam.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                namchon = newValue;
                napNgay(thangchon, namchon);
                tab.getItems().setAll(dscb);
            }
        });

    }

    class datepicker {
        private String tenThang;
        private Integer thang;

        public datepicker(String t, int thang) {
            tenThang = t;
            this.thang = thang;
        }

        public String getTenThang() {
            return tenThang;
        }

        public Integer getThang() {
            return thang;
        }

        public void setTenThang(String tenThang) {
            this.tenThang = tenThang;
        }

        public void setThang(Integer thang) {
            this.thang = thang;
        }

        @Override
        public String toString() {
            return tenThang;
        }
    }
}
