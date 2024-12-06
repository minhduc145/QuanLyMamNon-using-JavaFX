package hp.mnhp;

import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class test extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CBNV.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add(new CupertinoLight().getUserAgentStylesheet());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
//        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        String s = "1";
//        System.out.println(s.matches(".*\\d.*"));
//        System.out.println(s.matches(".*\\s.*"));

    }

}