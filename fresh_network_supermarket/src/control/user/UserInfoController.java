package control.user;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manager.UserManager;
import model.BeanUser;

public class UserInfoController {
    @FXML
    private TextField text_pwd1;

    @FXML
    private TextField text_pwd2;

    @FXML
    private TextField text_sex;

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_tel;

    @FXML
    private TextField text_city;

    @FXML
    private TextField text_email;

    public void modify() {
        String name = text_name.getText();
        String sex = text_sex.getText();
        String pwd1 = text_pwd1.getText();
        String pwd2 = text_pwd2.getText();
        String tel = text_tel.getText();
        String email = text_email.getText();
        String city = text_city.getText();
        new UserManager().modify(name, sex, pwd1, pwd2, tel, email, city);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText("修改成功");
        alert.showAndWait();
        Stage primaryStage = (Stage) text_name.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    public void initialize() {
        text_pwd1.setText(BeanUser.currentLoginUser.getUser_pwd());
        text_pwd2.setText(BeanUser.currentLoginUser.getUser_pwd());
        text_sex.setText(BeanUser.currentLoginUser.getUser_sex());
        text_name.setText(BeanUser.currentLoginUser.getUser_name());
        text_tel.setText(BeanUser.currentLoginUser.getUser_tel());
        text_city.setText(BeanUser.currentLoginUser.getUser_city());
        text_email.setText(BeanUser.currentLoginUser.getUser_email());
    }

}
