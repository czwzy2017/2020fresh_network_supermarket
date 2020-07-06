package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manager.AdminManager;
import util.BaseException;


public class SuperController {
    @FXML
    private Label label_home;

    @FXML
    private Button button_home;

    @FXML
    private TextField text_r_id;

    @FXML
    private TextField text_r_name;

    @FXML
    private TextField text_d_id;

    @FXML
    private TextField text_d_name;

    @FXML
    private TextField text_pwd;

    @FXML
    private TextField text_pwd2;

    public void eventPwd() throws Exception{
        Stage primaryStage = (Stage) label_home.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/SuperPwd.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    public void eventRegister() throws Exception {
        Stage primaryStage = (Stage) label_home.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/AdminRegister.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    public void eventDelete() throws Exception{
        Stage primaryStage = (Stage) label_home.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/AdminDelete.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    public void eventHome() throws Exception {
        Stage primaryStage = (Stage) button_home.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Super.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    public void Register() throws Exception{
        String id = text_r_id.getText();
        String name = text_r_name.getText();
        AdminManager m = new AdminManager();
        try {
            m.reg(id, name);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("注册成功");
            alert.showAndWait();
            eventHome();
        } catch (BaseException e) {
            outputError(e);
        }
    }

    public void Delete() throws Exception{
        String id=text_d_id.getText();
        String name=text_d_name.getText();
        AdminManager m=new AdminManager();
        try {
            m.delete(id,name);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("删除成功");
            alert.showAndWait();
            eventHome();
        }catch (BaseException e){
            outputError(e);
        }
    }

    public void Pwd() throws Exception{
        String pwd=text_pwd.getText();
        String pwd2=text_pwd2.getText();
        AdminManager m=new AdminManager();
        try{
            m.modifyPwd("0",pwd,pwd2);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("修改成功");
            alert.showAndWait();
            eventHome();
        } catch (BaseException e) {
            outputError(e);
        }
    }

    private void outputError(Exception e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
