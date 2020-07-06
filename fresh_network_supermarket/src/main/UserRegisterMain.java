package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserRegisterMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/userRegister/UserRegister.fxml"));
        primaryStage.setTitle("注册");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
