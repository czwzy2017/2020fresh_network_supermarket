package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BeanGoodsProcurement;
import manager.ProcurementManager;
import util.BaseException;


public class AdminController {
    @FXML
    private TableView<BeanGoodsProcurement> view_procurement;

    @FXML
    private TableColumn<BeanGoodsProcurement, String> col_procurement_id;

    @FXML
    private TableColumn<BeanGoodsProcurement, String> col_procurement_count;

    @FXML
    private TableColumn<BeanGoodsProcurement, String> col_procurement_goods;

    @FXML
    private TableColumn<BeanGoodsProcurement, String> col_procurement_status;

    public ObservableList<BeanGoodsProcurement> procurements= FXCollections.observableArrayList();
    public void loadProcurement() {
        try {
            procurements = new ProcurementManager().loadProcurement();
        } catch (BaseException e) {
            outputError(e);
            return;
        }
        col_procurement_id.setCellValueFactory(new PropertyValueFactory<>("procurement_id"));
        col_procurement_count.setCellValueFactory(new PropertyValueFactory<>("procurement_count"));
        col_procurement_goods.setCellValueFactory(new PropertyValueFactory<>("goods_id"));
        col_procurement_status.setCellValueFactory(new PropertyValueFactory<>("procurement_status"));
        view_procurement.getItems().setAll(procurements);
    }

    @FXML
    public void initialize(){
        loadProcurement();
    }

    private void outputError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
