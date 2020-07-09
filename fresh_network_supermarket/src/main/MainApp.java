package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import util.DefaultExceptionHandler;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/UserLogin.fxml"));
        primaryStage.setTitle("生鲜网超");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

    public void showUserRegister() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/UserRegister.fxml"));
        Stage stage = new Stage();
        stage.setTitle("注册");
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

    public void showProcurement() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/Procurement.fxml"));
        Stage stage = new Stage();
        stage.setTitle("员工");
        stage.setScene(new Scene(root, 1024, 768));
        stage.show();
    }

    public void showAddProcurement() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/AddProcurement.fxml"));
        Stage stage = new Stage();
        stage.setTitle("采购");
        stage.setScene(new Scene(root, 320, 240));
        stage.show();
        stage.setOnCloseRequest(event -> {
            stage.close();
            try {
               showProcurement();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void showAdminPwd() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/AdminPwd.fxml"));
        Stage stage = new Stage();
        stage.setTitle("修改密码");
        stage.setScene(new Scene(root, 320, 240));
        stage.show();
    }

    public void showSuper() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/superAdmin/Super.fxml"));
        Stage stage = new Stage();
        stage.setTitle("超级管理员");
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
