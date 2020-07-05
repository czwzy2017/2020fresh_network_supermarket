package control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.registerMain;
import model.BeanAdmin;
import model.BeanUserInfo;
import manager.userManager;
import manager.adminManager;

import util.BaseException;

public class loginController {

    @FXML
    private TextField text_id;

    @FXML
    private TextField text_pwd;

    public void eventRegister() throws Exception {
        registerMain re = new registerMain();
        Stage stage = new Stage();
        re.start(stage);
    }

    public void eventUserLogin() throws Exception {
        String userId = text_id.getText();
        String pwd = text_pwd.getText();
        userManager m = new userManager();
        try {
            BeanUserInfo.currentLoginUser = m.login(userId, pwd);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("登陆成功");
            alert.showAndWait();
            Stage primaryStage = (Stage) text_id.getScene().getWindow();
            primaryStage.close();
        } catch (BaseException e1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setContentText(e1.getMessage());
            alert.showAndWait();
            return;
        }
    }

    public void eventAdminLogin() throws Exception {
        String userId = text_id.getText();
        String pwd = text_pwd.getText();
        adminManager m = new adminManager();
        try {
            BeanAdmin.currentLoginAdmin = m.login(userId, pwd);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("登陆成功");
            alert.showAndWait();
            Stage primaryStage = (Stage) text_id.getScene().getWindow();
            primaryStage.close();
        } catch (BaseException e1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setContentText(e1.getMessage());
            alert.showAndWait();
            return;
        }
    }

}
