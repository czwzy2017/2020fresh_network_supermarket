package control.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.MainApp;
import model.BeanUser;

import java.io.IOException;

public class UserMenuController {
    @FXML
    public Text text_name;
    public void eventStore() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/user/Store.fxml"));
        Stage primaryStage = (Stage)text_name.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventShopping() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/user/Shopping.fxml"));
        Stage primaryStage = (Stage)text_name.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventCoupon() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/user/Coupon.fxml"));
        Stage primaryStage = (Stage)text_name.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventOrders() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/user/Orders.fxml"));
        Stage primaryStage = (Stage)text_name.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventAddress() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/user/Address.fxml"));
        Stage primaryStage = (Stage)text_name.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }

    public void eventVIP() throws IOException{
        new MainApp().showVIP();

    }

    public void eventInfo() throws IOException {
        new MainApp().showUserInfo();
    }
    public void eventExit() throws IOException{
        Stage primaryStage = (Stage) text_name.getScene().getWindow();
        BeanUser.currentLoginUser = null;
        primaryStage.close();
        new MainApp().showLogin();
    }
}
