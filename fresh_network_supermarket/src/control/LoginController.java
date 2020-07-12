package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.MainApp;
import model.BeanAdmin;
import model.BeanUser;
import manager.UserManager;
import manager.AdminManager;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField text_tel;

    @FXML
    private TextField text_user_pwd;

    @FXML
    private TextField text_id;

    @FXML
    private TextField text_admin_pwd;

    @FXML
    private TextField text_super_pwd;

    @FXML
    private Button button_user;

    @FXML
    private Button button_admin;

    @FXML
    private Button button_super;

    public void eventAdmin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/AdminLogin.fxml"));
        Stage primaryStage = (Stage) button_admin.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("员工登陆");
    }

    public void eventUser() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/UserLogin.fxml"));
        Stage primaryStage = (Stage) button_user.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("用户登录");
    }

    public void eventSuper() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/SuperLogin.fxml"));
        Stage primaryStage = (Stage) button_super.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("超级管理员登陆");
    }

    public void eventUserRegister() throws Exception {
        MainApp mainApp = new MainApp();
        mainApp.showUserRegister();
    }

    public void eventUserLogin() throws IOException {
        String tel = text_tel.getText();
        String pwd = text_user_pwd.getText();
        UserManager m = new UserManager();
        BeanUser.currentLoginUser = m.login(tel, pwd);
        Stage primaryStage = (Stage) text_tel.getScene().getWindow();
        primaryStage.close();
        new MainApp().showStore();
    }

    public void eventAdminLogin() throws IOException {
        String id = text_id.getText();
        String pwd = text_admin_pwd.getText();
        AdminManager m = new AdminManager();
        BeanAdmin.currentLoginAdmin = m.login(id, pwd);
        if ("000000".equals(pwd)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("您的密码为初始密码，请尽快前往“个人信息”-“修改密码”修改你的密码。");
            alert.showAndWait();
        }
        Stage primaryStage = (Stage) text_id.getScene().getWindow();
        primaryStage.close();
        MainApp mainApp = new MainApp();
        mainApp.showProcurement();
    }

    public void eventSuperLogin() throws IOException {
        String pwd = text_super_pwd.getText();
        AdminManager m = new AdminManager();
        BeanAdmin.currentLoginAdmin = m.login("0", pwd);
        Stage primaryStage = (Stage) text_super_pwd.getScene().getWindow();
        primaryStage.close();
        new MainApp().showSuper();
    }
}

