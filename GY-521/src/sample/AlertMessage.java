package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertMessage extends Alert {

    public AlertMessage(AlertType alertType) {
        super(alertType);
    }
}
