package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import manager.AdminManager;
import manager.ProcurementManager;
import model.BeanAdmin;
import model.BeanGoodsProcurement;

import java.util.Optional;


public class SuperController {
    @FXML
    private TableView<BeanAdmin> view_admin;
    @FXML
    private TableColumn<BeanAdmin, String> col_id;
    @FXML
    private TableColumn<BeanAdmin, String> col_name;
    @FXML
    private TableColumn<BeanAdmin, String> col_pwd;
    @FXML
    private TextField text_r_name;
    @FXML
    private TextField text_r_id;

    public ObservableList<BeanAdmin> admins = FXCollections.observableArrayList();
    public void loadAdmin(){
        admins = new AdminManager().loadAdmin();
        col_id.setCellValueFactory(new PropertyValueFactory<>("admin_id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("admin_name"));
        col_pwd.setCellValueFactory(new PropertyValueFactory<>("admin_pwd"));
        view_admin.getItems().setAll(admins);
    }
    public void Register(){
        String id = text_r_id.getText();
        String name = text_r_name.getText();
        AdminManager m = new AdminManager();
        m.reg(id, name);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText("注册成功");
        alert.showAndWait();
        text_r_id.clear();
        text_r_name.clear();
        loadAdmin();
    }

    public void Delete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_admin.getSelectionModel().getSelectedIndex();
            new AdminManager().delete(view_admin.getItems().get(selectedIndex));
            view_admin.getItems().remove(selectedIndex);
        }
    }

    @FXML
    public void initialize() {
        loadAdmin();
    }
}
