package control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manager.userManager;
import model.BeanUserInfo;
import util.BaseException;


public class registerController {

    @FXML
    private TextField text_pwd1;

    @FXML
    private TextField text_pwd2;

    @FXML
    private Button button_register;

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

    public void eventRegister(){
        String name = text_name.getText();
        String sex = text_sex.getText();
        String pwd1 = text_pwd1.getText();
        String pwd2 = text_pwd2.getText();
        String tel = text_tel.getText();
        String email = text_email.getText();
        String city = text_city.getText();
        userManager m = new userManager();
        try {
            String id=m.reg(name,sex,pwd1,pwd2,tel,email,city);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("��ʾ");
            alert.setHeaderText(null);
            alert.setContentText("ע��ɹ�");
            alert.showAndWait();
            Stage primaryStage = (Stage) text_name.getScene().getWindow();
            primaryStage.close();
        } catch (BaseException e1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("����");
            alert.setContentText(e1.getMessage());
            alert.showAndWait();
            return;
        }
    }
}
