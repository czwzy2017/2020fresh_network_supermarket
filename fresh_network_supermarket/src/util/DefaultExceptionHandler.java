package util;

import javafx.scene.control.Alert;

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        while (e.getCause()!= null) {
            e = e.getCause();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}