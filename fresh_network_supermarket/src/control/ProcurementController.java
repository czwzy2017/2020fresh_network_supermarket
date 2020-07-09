package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import main.MainApp;
import model.BeanAdmin;
import model.BeanGoodsProcurement;
import manager.ProcurementManager;
import util.BaseException;

import java.util.Optional;

public class ProcurementController {
    @FXML
    private TableView<BeanGoodsProcurement> view_procurement;

    @FXML
    private TableColumn<BeanGoodsProcurement, Integer> col_procurement_id;

    @FXML
    private TableColumn<BeanGoodsProcurement, Integer> col_procurement_count;

    @FXML
    private TableColumn<BeanGoodsProcurement, Integer> col_procurement_goods;

    @FXML
    private TableColumn<BeanGoodsProcurement, String> col_procurement_status;

    @FXML
    private TableColumn<BeanGoodsProcurement, String> col_procurement_name;

    @FXML
    private AdminMenuController menuController;

    public ObservableList<BeanGoodsProcurement> procurements = FXCollections.observableArrayList();

    public void loadProcurement() {
        procurements = new ProcurementManager().loadProcurement();
        col_procurement_id.setCellValueFactory(new PropertyValueFactory<>("procurement_id"));
        col_procurement_count.setCellValueFactory(new PropertyValueFactory<>("procurement_count"));
        col_procurement_count.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_procurement_goods.setCellValueFactory(new PropertyValueFactory<>("goods_id"));
        col_procurement_goods.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_procurement_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
        col_procurement_status.setCellValueFactory(new PropertyValueFactory<>("procurement_status"));
        col_procurement_status.setCellFactory(TextFieldTableCell.forTableColumn());
        view_procurement.getItems().setAll(procurements);
    }

    public void eventAdd() throws Exception {
        MainApp mainApp = new MainApp();
        mainApp.showAddProcurement();
        Stage primaryStage = (Stage) view_procurement.getScene().getWindow();
        primaryStage.close();
    }


    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_procurement.getSelectionModel().getSelectedIndex();
            new ProcurementManager().delete(view_procurement.getItems().get(selectedIndex));
            view_procurement.getItems().remove(selectedIndex);
        }
    }


    @FXML
    public void initialize() {
        loadProcurement();
        menuController.text_name.setText("欢迎您，" + BeanAdmin.currentLoginAdmin.getAdmin_name() + "     您的员工号为：" + BeanAdmin.currentLoginAdmin.getAdmin_id());
        col_procurement_goods.setOnEditCommit(goodsColEdit -> {
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setGoods_id(goodsColEdit.getNewValue());
            try {
                new ProcurementManager().modify(goodsColEdit.getRowValue(),0);
            } catch (BaseException e) {
                outputError(e);
                loadProcurement();
            }
        });


        col_procurement_count.setOnEditCommit(countColEdit -> {
            countColEdit.getTableView().getItems().get(countColEdit.getTablePosition().getRow()).setProcurement_count(countColEdit.getNewValue());
            try {
                new ProcurementManager().modify(countColEdit.getRowValue(),0);
            } catch (BaseException e) {
                outputError(e);
                loadProcurement();
            }
        });

        col_procurement_status.setOnEditCommit(statusColEdit -> {
            statusColEdit.getTableView().getItems().get(statusColEdit.getTablePosition().getRow()).setProcurement_status(statusColEdit.getNewValue());
            try {
                new ProcurementManager().modify(statusColEdit.getRowValue(),1);
            } catch (BaseException e) {
                outputError(e);
                loadProcurement();
            }
        });
    }

    private void outputError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
