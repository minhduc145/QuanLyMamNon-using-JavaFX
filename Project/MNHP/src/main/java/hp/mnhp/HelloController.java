package hp.mnhp;

import DAO.DbHelper;
import atlantafx.base.theme.CupertinoLight;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    Parent root;
    @FXML
    Button login_loginBtn;
    @FXML
    TextField login_username;
    @FXML
    TextField login_password;

    @FXML
    protected void onClick() {

        String usn = login_username.getText().toString();
        String psw = login_password.getText().toString();


        if (DbHelper.getInstance().DangNhap(usn, psw)) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Main");
                stage.setScene(new Scene(root));
               // stage.setMaximized(true);
                stage.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            login_loginBtn.getScene().getWindow().hide();
        } else {
            try {
                root = FXMLLoader.load(getClass().getResource("loi.fxml"));
                Stage stage = new Stage();
                stage.setTitle("");
                stage.setScene(new Scene(root, 261, 130));
                stage.setResizable(false);
                stage.initStyle(StageStyle.UTILITY);
                stage.getScene().getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
