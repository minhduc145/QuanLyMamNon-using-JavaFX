package hp.mnhp;

import DAO.DbHelper;
import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class HelloApplication extends Application {
    void handlerCloserq(WindowEvent event) {
        System.out.println("AAAAAAAAAAAA");
    }

    @Override
    public void start(Stage stage) throws IOException {
        File myObj = new File("db.txt");
        if (myObj.exists()) {
            Scanner myReader = null;
            try {
                myReader = new Scanner(myObj);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            String data = myReader.nextLine();
            DbHelper._db1 = data.trim();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setOnCloseRequest(event -> {
                handlerCloserq(event);
            });
            stage.show();

        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catchURL.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setOnCloseRequest(event -> {
                handlerCloserq(event);
            });
            stage.show();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}