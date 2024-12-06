package hp.mnhp;

import DAO.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class CatchURL {
    @FXML
    TextField txt1;
    @FXML
    Button btn1;
    @FXML
    void onBtn(){
        if(!txt1.getText().isBlank()){
            DbHelper._db1 = txt1.getText().toString();
            try {
                FileWriter myWriter = new FileWriter("db.txt",false);
                myWriter.write(txt1.getText().toString());
                myWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String configFile = "jdbc:sqlserver://\\\\localhost:1433;"+DbHelper._db1+";databasename=MNHP;trustServerCertificate=true;";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String dbURL = configFile;
                Connection cn = DriverManager.getConnection(dbURL);
                cn = DriverManager.getConnection(dbURL);
                btn1.getScene().getWindow().hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
