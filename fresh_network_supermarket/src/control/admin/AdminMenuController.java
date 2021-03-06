package control.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.MainApp;
import manager.AdminManager;
import model.BeanAdmin;

import java.io.IOException;

public class AdminMenuController {
    @FXML
    public MenuBar menubar;
    @FXML
    public Text text_name;
    @FXML
    private TextField text_pwd;
    @FXML
    private TextField text_pwd2;

    public void eventProcurement() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/admin/Procurement.fxml"));
        Stage primaryStage = (Stage) menubar.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventFresh() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/admin/Fresh.fxml"));
        Stage primaryStage = (Stage) menubar.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventCookbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/admin/Cookbook.fxml"));
        Stage primaryStage = (Stage) menubar.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventCoupon() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/admin/Coupon.fxml"));
        Stage primaryStage = (Stage) menubar.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventDiscount() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/admin/Discount.fxml"));
        Stage primaryStage = (Stage) menubar.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventPromotion() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/admin/Promotion.fxml"));
        Stage primaryStage = (Stage) menubar.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventPwd() throws IOException {
        MainApp mainApp = new MainApp();
        mainApp.showAdminPwd();
    }

    public void eventExit() throws IOException{
        Stage primaryStage = (Stage) menubar.getScene().getWindow();
        BeanAdmin.currentLoginAdmin = null;
        primaryStage.close();
        new MainApp().showLogin();
    }


    public void pwd() {
        String pwd = text_pwd.getText();
        String pwd2 = text_pwd2.getText();
        AdminManager m = new AdminManager();
        m.modifyPwd(BeanAdmin.currentLoginAdmin.getAdmin_id(), pwd, pwd2);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText("修改成功");
        alert.showAndWait();
        Stage primaryStage = (Stage) text_pwd.getScene().getWindow();
        primaryStage.close();
    }
}
