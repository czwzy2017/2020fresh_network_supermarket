package control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class registerController {

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
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("提示");
        alert.headerTextProperty().set("注册成功");
        alert.show();
        Stage primaryStage=(Stage)button_register.getScene().getWindow();
        primaryStage.close();

    }
}
