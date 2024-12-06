package hp.mnhp;

import DAO.CBNVDao;
import DAO.linhtinhDao;
import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyInfo implements Initializable {
    linhtinhDao linhtinh = new linhtinhDao();
    CBNVDao cbdao = new CBNVDao();
    List<NhanVien> ds = new ArrayList<>();
    List<String> link = new ArrayList<>();
    int lastIndex;


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
    ImageView img;
    @FXML
    Button reload, themnv, xoanv, suaBtn, luuBtn, huyBtn, sBtn;

    @FXML
    DatePicker date, bd;

    void setField(NhanVien cb) {

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
