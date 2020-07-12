package main;

import control.admin.CommentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import util.DefaultExceptionHandler;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/UserLogin.fxml"));
        primaryStage.setTitle("生鲜网超");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/UserLogin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("生鲜网超");
        stage.setScene(new Scene(root, 640, 480));
        stage.setResizable(false);
        stage.show();
    }

    public void showUserRegister() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login/UserRegister.fxml"));
        Stage stage = new Stage();
        stage.setTitle("注册");
        stage.setScene(new Scene(root, 640, 480));
        stage.setResizable(false);
        stage.show();
    }

    public void showProcurement() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/Procurement.fxml"));
        Stage stage = new Stage();
        stage.setTitle("员工");
        stage.setScene(new Scene(root, 1024, 768));
        stage.setResizable(false);
        stage.show();
    }

    public void showAddProcurement() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/AddProcurement.fxml"));
        Stage stage = new Stage();
        stage.setTitle("采购");
        stage.setScene(new Scene(root, 320, 240));
        stage.setResizable(false);
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

    public void showFresh() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/Fresh.fxml"));
        Stage stage = new Stage();
        stage.setTitle("员工");
        stage.setScene(new Scene(root, 1024, 768));
        stage.setResizable(false);
        stage.show();
    }

    public void showAddGoods() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/AddGoods.fxml"));
        Stage stage = new Stage();
        stage.setTitle("添加商品");
        stage.setScene(new Scene(root, 640, 480));
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> {
            stage.close();
            try {
                showFresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showComment(int id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/admin/GoodsComment.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) loader.load()));
        stage.setResizable(false);
        CommentController controller = loader.<CommentController>getController();
        controller.initData(id);
        stage.show();
    }

    public void showCookbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/Cookbook.fxml"));
        Stage stage = new Stage();
        stage.setTitle("员工");
        stage.setScene(new Scene(root, 1024, 768));
        stage.setResizable(false);
        stage.show();
    }

    public void showAddCookbook() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/AddCookbook.fxml"));
        Stage stage = new Stage();
        stage.setTitle("添加菜谱");
        stage.setScene(new Scene(root, 640, 480));
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> {
            stage.close();
            try {
                showCookbook();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showAdminPwd() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/admin/AdminPwd.fxml"));
        Stage stage = new Stage();
        stage.setTitle("修改密码");
        stage.setScene(new Scene(root, 320, 240));
        stage.setResizable(false);
        stage.show();
    }

    public void showStore() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/user/Store.fxml"));
        Stage stage = new Stage();
        stage.setTitle("用户");
        stage.setScene(new Scene(root, 1024, 768));
        stage.setResizable(false);
        stage.show();
    }

    public void showUserInfo() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/user/UserInfo.fxml"));
        Stage stage = new Stage();
        stage.setTitle("个人信息");
        stage.setScene(new Scene(root, 640, 480));
        stage.setResizable(false);
        stage.show();

    }

    public void showVIP() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/user/VIP.fxml"));
        Stage stage = new Stage();
        stage.setTitle("VIP信息");
        stage.setScene(new Scene(root, 320, 240));
        stage.setResizable(false);
        stage.show();

    }

    public void showSuper() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/superAdmin/Super.fxml"));
        Stage stage = new Stage();
        stage.setTitle("超级管理员");
        stage.setScene(new Scene(root, 640, 480));
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
