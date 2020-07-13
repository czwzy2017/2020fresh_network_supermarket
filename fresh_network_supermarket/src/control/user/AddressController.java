package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import manager.AddressManager;
import model.BeanDeliverAddress;
import model.BeanUser;
import util.BaseException;
import util.BusinessException;

import java.util.Optional;

public class AddressController {
    @FXML
    private UserMenuController menuController;

    @FXML
    private TableView<BeanDeliverAddress> view;

    @FXML
    private TableColumn<BeanDeliverAddress, String> col_province;

    @FXML
    private TableColumn<BeanDeliverAddress, String> col_city;

    @FXML
    private TableColumn<BeanDeliverAddress, String> col_district;

    @FXML
    private TableColumn<BeanDeliverAddress, String> col_address;

    @FXML
    private TableColumn<BeanDeliverAddress, String> col_contact;

    @FXML
    private TableColumn<BeanDeliverAddress, String> col_tel;

    @FXML
    private TextField text_province;

    @FXML
    private TextField text_city;

    @FXML
    private TextField text_district;

    @FXML
    private TextField text_address;

    @FXML
    private TextField text_contact;

    @FXML
    private TextField text_tel;

    public ObservableList<BeanDeliverAddress> addresses = FXCollections.observableArrayList();

    public void loadAddress() {
        addresses = new AddressManager().loadAddress();
        col_province.setCellValueFactory(new PropertyValueFactory<>("delivery_province"));
        col_province.setCellFactory(TextFieldTableCell.forTableColumn());
        col_city.setCellValueFactory(new PropertyValueFactory<>("delivery_city"));
        col_city.setCellFactory(TextFieldTableCell.forTableColumn());
        col_district.setCellValueFactory(new PropertyValueFactory<>("delivery_district"));
        col_district.setCellFactory(TextFieldTableCell.forTableColumn());
        col_address.setCellValueFactory(new PropertyValueFactory<>("delivery_address"));
        col_address.setCellFactory(TextFieldTableCell.forTableColumn());
        col_contact.setCellValueFactory(new PropertyValueFactory<>("delivery_contact"));
        col_contact.setCellFactory(TextFieldTableCell.forTableColumn());
        col_tel.setCellValueFactory(new PropertyValueFactory<>("delivery_tel"));
        col_tel.setCellFactory(TextFieldTableCell.forTableColumn());
        view.getItems().setAll(addresses);
    }

    public void add() {
        BeanDeliverAddress address = new BeanDeliverAddress();
        if ("".equals(text_province.getText().trim())) throw new BusinessException("省不能为空");
        if ("".equals(text_city.getText().trim())) throw new BusinessException("市不能为空");
        if ("".equals(text_district.getText().trim())) throw new BusinessException("区不能为空");
        if ("".equals(text_address.getText().trim())) throw new BusinessException("地址不能为空");
        if ("".equals(text_contact.getText().trim())) throw new BusinessException("联系人不能为空");
        if ("".equals(text_tel.getText().trim())) throw new BusinessException("手机号不能为空");
        String telRegex = "[1][3578]\\d{9}";
        if (!text_tel.getText().trim().matches(telRegex)) throw new BusinessException("请输入正确的手机号");
        address.setDelivery_province(text_province.getText().trim());
        address.setDelivery_city(text_city.getText().trim());
        address.setDelivery_district(text_district.getText().trim());
        address.setDelivery_address(text_address.getText().trim());
        address.setDelivery_contact(text_contact.getText().trim());
        address.setDelivery_tel(text_tel.getText().trim());
        new AddressManager().add(address);
        text_province.clear();
        text_city.clear();
        text_district.clear();
        text_address.clear();
        text_contact.clear();
        text_tel.clear();
        loadAddress();
    }

    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view.getSelectionModel().getSelectedIndex();
            new AddressManager().delete(view.getItems().get(selectedIndex));
            view.getItems().remove(selectedIndex);
        }
    }


    @FXML
    public void initialize() {
        loadAddress();
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
        col_province.setOnEditCommit(addressesColEdit -> {
            addressesColEdit.getTableView().getItems().get(addressesColEdit.getTablePosition().getRow()).setDelivery_province(addressesColEdit.getNewValue());
            try {
                new AddressManager().modify(addressesColEdit.getRowValue());
                loadAddress();
            } catch (BaseException e) {
                outputError(e);
                loadAddress();
            }
        });
        col_city.setOnEditCommit(addressesColEdit -> {
            addressesColEdit.getTableView().getItems().get(addressesColEdit.getTablePosition().getRow()).setDelivery_city(addressesColEdit.getNewValue());
            try {
                new AddressManager().modify(addressesColEdit.getRowValue());
                loadAddress();
            } catch (BaseException e) {
                outputError(e);
                loadAddress();
            }
        });
        col_district.setOnEditCommit(addressesColEdit -> {
            addressesColEdit.getTableView().getItems().get(addressesColEdit.getTablePosition().getRow()).setDelivery_district(addressesColEdit.getNewValue());
            try {
                new AddressManager().modify(addressesColEdit.getRowValue());
                loadAddress();
            } catch (BaseException e) {
                outputError(e);
                loadAddress();
            }
        });
        col_address.setOnEditCommit(addressesColEdit -> {
            addressesColEdit.getTableView().getItems().get(addressesColEdit.getTablePosition().getRow()).setDelivery_address(addressesColEdit.getNewValue());
            try {
                new AddressManager().modify(addressesColEdit.getRowValue());
                loadAddress();
            } catch (BaseException e) {
                outputError(e);
                loadAddress();
            }
        });
        col_contact.setOnEditCommit(addressesColEdit -> {
            addressesColEdit.getTableView().getItems().get(addressesColEdit.getTablePosition().getRow()).setDelivery_contact(addressesColEdit.getNewValue());
            try {
                new AddressManager().modify(addressesColEdit.getRowValue());
                loadAddress();
            } catch (BaseException e) {
                outputError(e);
                loadAddress();
            }
        });
        col_tel.setOnEditCommit(addressesColEdit -> {
            addressesColEdit.getTableView().getItems().get(addressesColEdit.getTablePosition().getRow()).setDelivery_tel(addressesColEdit.getNewValue());
            try {
                new AddressManager().modify(addressesColEdit.getRowValue());
                loadAddress();
            } catch (BaseException e) {
                outputError(e);
                loadAddress();
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
