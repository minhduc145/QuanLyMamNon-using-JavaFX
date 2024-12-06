package hp.mnhp;

import DAO.DbHelper;
import Model.LopModel;
import Model.hsModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ThemHSvaoLop implements Initializable {
    public static LopModel _lopht = null;
    List<hsModel> lst = new ArrayList<>();
    @FXML
    Button ok;
    @FXML
    TableView<hsModel> tab;
    @FXML
    TableColumn chk, tt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ok.setText(ok.getText() + " '" + _lopht.getTenLop() + "'");
        try {
            Connection cn = (DbHelper.getInstance()).getConnection();
            String SQL = "select * from tre where idLop is null";
            PreparedStatement stmt = cn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hsModel hs = new hsModel();
                hs.setHoten(rs.getString("hoten"));
                hs.setIdLop(rs.getString("idLop"));
                hs.setDangtheohoc(rs.getBoolean("dangtheohoc"));
                hs.setLanam(rs.getBoolean("lanam"));
                hs.setDiachi(rs.getString("diachi"));
                hs.setId(rs.getString("idtre"));
                hs.setNamnhaphoc(rs.getString("NamNhapHoc"));
                hs.setNoisinh(rs.getString("noisinh"));
                if (rs.getDate("ngaysinh") != null) {
                    LocalDate newDate2 = rs.getDate("ngaysinh").toLocalDate();
                    hs.setNgaysinh(newDate2);
                }
                lst.add(hs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chk.setCellValueFactory(new PropertyValueFactory<hsModel, CheckBox>("select"));
        tt.setCellValueFactory(new PropertyValueFactory<hsModel, String>("tt"));
        tab.getItems().setAll(lst);
        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                /**********chon2************/
                for (hsModel h : tab.getItems()) {
                    if (h.getSelect().isSelected()) {
                        try {
                            Connection cn = (DbHelper.getInstance()).getConnection();
                            String SQL = "UPDATE [dbo].[Tre]\n" +
                                    "   SET [idLop] = ?" +
                                    " WHERE idtre = ?";
                            PreparedStatement stmt = cn.prepareStatement(SQL);
                            stmt.setString(1, _lopht.getId());
                            stmt.setString(2, h.getId());
                            stmt.executeUpdate();
                            ok.getScene().getWindow().hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                /**********chon2************/

            }
        });
    }
}
