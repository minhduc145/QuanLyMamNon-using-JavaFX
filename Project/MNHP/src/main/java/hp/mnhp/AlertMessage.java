package hp.mnhp;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMessage {
    public Alert a;

    public static void infoBox(String infoMessage, String titleBar) {
        /* By specifying a null headerMessage String, we cause the dialog to
           not have a header */
        infoBox(null, titleBar, infoMessage);
    }

    public static void cfBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.showAndWait();
    }

    public static boolean isConfirmedBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.showAndWait();
        if (alert.getResult().getButtonData().isDefaultButton()) return true;
        else return false;
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.showAndWait();
    }


    static boolean errorBox(String infoMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(null);
        alert.setTitle(null);
        alert.setHeaderText(infoMessage);
        alert.showAndWait();
        return true;
    }

    public static void erBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(infoMessage);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.showAndWait();
    }
}