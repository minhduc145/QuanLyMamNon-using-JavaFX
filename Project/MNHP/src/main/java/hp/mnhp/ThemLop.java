package hp.mnhp;

import DAO.DbHelper;
import Model.LopModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ThemLop extends Stage implements Initializable {
    public static Boolean result = true;
    @FXML
    AnchorPane ap;
    @FXML
    Button luubtn;
    @FXML
    TextField txt;
    @FXML
    Text malop;
    @FXML
    ComboBox<LoaiLop> cmbloai;
    String auto = "Mã lớp: ";
    String idlop1 = "";
    String idlop2 = "";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<LoaiLop> lst = new ArrayList<>() {{
            add(new LoaiLop("Trẻ", "Tre"));
            add(new LoaiLop("Bé", "Be"));
            add(new LoaiLop("Nhỡ", "Nho"));
            add(new LoaiLop("Lớn", "Lon"));
        }};
        cmbloai.getItems().addAll(lst);
        txt.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                idlop2 = txt.getText().toLowerCase().replace(" ", "");
//                if (!txt.getText().isEmpty()) {
//                    for (String s : txt.getText().split(" ")) {
//                        if (Character.isDigit(s.charAt(0))) {
//                            idlop2 += s;
//                        } else
//                            idlop2 = idlop2 + s.substring(0, 1).toLowerCase();
//                    }
//                } else idlop2 = ("");
                malop.setText(auto + idlop1 + idlop2);
            }
        });
        cmbloai.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LoaiLop>() {

            @Override
            public void changed(ObservableValue<? extends LoaiLop> observable, LoaiLop oldValue, LoaiLop newValue) {
                idlop1 = "";
                idlop1 += newValue.getMaLoaiLop();
                malop.setText(auto + idlop1 + idlop2);
            }
        });
        luubtn.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                themLopMoi(txt, cmbloai.getSelectionModel());
//
//                try {
//                    new LopDao().ThemLop(id.getText().toString(), txt.getText().toString());
//                    ap.getScene().getWindow().hide();
//                } catch (Exception e) {
//                    if (e.getMessage().contains("Violation of PRIMARY KEY")) {
//                        AlertMessage.erBox("Thử với tên khác", "Loi", "Lớp đã tồn tại (trùng mã lớp)");
//                    } else {
//                        AlertMessage.erBox("Thử với tên khác", "Loi", "Loi them");
//                    }
//                }
            }
        });

        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            System.out.println("Form đang đóng");
        });
    }

    /**********chon2************/
    private void themLopMoi(TextField tenInput, SingleSelectionModel<LoaiLop> luaChon) {
        LoaiLop loaiLop = luaChon.getSelectedItem();
        String TenLopMoi = tenInput.getText().trim();
        boolean viPham = false;
        if (loaiLop == null)
            viPham = AlertMessage.errorBox("Phải chọn loại lớp.");
        if (TenLopMoi.isEmpty())
            viPham = AlertMessage.errorBox("Không để trống Tên Lớp mới.");
        else if (TenLopMoi.length() < 2 || TenLopMoi.length() > 25)
            viPham = AlertMessage.errorBox("Kích thước Tên Lớp mới nằm trong [2;25] kí tự");
        else {
            boolean coChuaChuSo = false, coChuaChuCai = false;
            for (int i = 0; i < TenLopMoi.length(); i++) {
                if (Character.isDigit(TenLopMoi.charAt(i)))
                    coChuaChuSo = true;
                else if (Character.isLetter(TenLopMoi.charAt(i)))
                    coChuaChuCai = true;
                if (coChuaChuSo == true && coChuaChuCai == true) break;
            }
            if (coChuaChuCai == false || coChuaChuSo == false)
                viPham = AlertMessage.errorBox("Tên Lớp mới cần bao gồm ít nhất 1 ch cái và 1 ch số.");
        }
        if (viPham == true) {
            AlertMessage.errorBox("Thêm thất bại");
            return;
        }
        String idLopMoi = loaiLop.getMaLoaiLop() + TenLopMoi.replace(" ", "").toLowerCase();
        Connection cn = (DbHelper.getInstance()).getConnection();
        String SQL = String.format("INSERT INTO [dbo].[Lop] ([idLop],[TenLop]) VALUES ('%s','%s')", idLopMoi, TenLopMoi);
        try {
            cn.prepareStatement(SQL).executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Violation of PRIMARY KEY"))
                AlertMessage.errorBox("Tên Lớp cùng Loại đã tồn tại trong CSDL. Thêm thất bại.");
            return;
        }
        AlertMessage.infoBox("Đã thêm thành công lớp mới", null);
    }


    private void themLopMoi(String TenLopMoi) {
        boolean xacNhan = AlertMessage.isConfirmedBox(null, "Xác nhận", "Xác nhận lưu");
        if (xacNhan == true) {
            if (TenLopMoi.isBlank()) {
                AlertMessage.errorBox("Không để trống Tên Lớp mới. Thêm thất bại.");
                return;
            }
            TenLopMoi = TenLopMoi.trim();
            if (TenLopMoi.length() < 2) {
                AlertMessage.errorBox("Kích thước Tên Lớp mới phải lớn hơn 1 ký tự. Thêm thất bại.");
                return;
            } else if (TenLopMoi.length() > 25) {
                AlertMessage.errorBox("Kích thước Tên Lớp mới không quá 25 kí tự. Thêm thất bại.");
                return;
            }
            boolean coChuaChuSo = false, coChuaChuCai = false;
            for (int i = 0; i < TenLopMoi.length(); i++) {
                char x = TenLopMoi.charAt(i);
                if (Character.isDigit(x)) coChuaChuSo = true;
                else if (Character.isLetter(x)) coChuaChuCai = true;
                if (coChuaChuSo == true && coChuaChuCai == true) break;
            }
            if (coChuaChuCai == false || coChuaChuSo == false) {
                AlertMessage.errorBox("Tên Lớp mới cần bao gồm ít nhất 1 chữ cái và 1 chữ số. Thêm thất bại.");
                return;
            }
            String idLopMoi = TenLopMoi.replaceAll("\\s", ""); // id duoc tao tu dong tu ten lop moi
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "INSERT INTO [dbo].[Lop] ([idLop],[TenLop]) VALUES (?,?)";
            try {
                PreparedStatement stmt = cn.prepareStatement(SQL);
                stmt.setString(1, idLopMoi);
                stmt.setString(2, TenLopMoi);
                stmt.executeUpdate();
            } catch (SQLException e) {
                if (e.getMessage().contains("Violation of PRIMARY KEY"))
                    AlertMessage.errorBox("Tên Lớp cùng Loại đã tồn tại trong CSDL. Thêm thất bại.");
                return;
            }
            AlertMessage.infoBox("Đã thêm thành công lớp mới: " + TenLopMoi + " có id: " + idLopMoi, null);
        }
    }

    /**********chon2************/


}

class LoaiLop {
    private String TenLoaiLop;
    private String MaLoaiLop;

    public LoaiLop() {
    }

    public String getMaLoaiLop() {
        return MaLoaiLop;
    }

    public LoaiLop(String ten, String loai) {
        TenLoaiLop = ten;
        MaLoaiLop = loai;
    }

    @Override
    public String toString() {
        return TenLoaiLop;
    }
}