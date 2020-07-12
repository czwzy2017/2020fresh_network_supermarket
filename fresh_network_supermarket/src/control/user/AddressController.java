package control.user;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.BeanDeliverAddress;
import model.BeanUser;

public class AddressController {
    @FXML
    private UserMenuController menuController;

    @FXML
    private TableView<BeanDeliverAddress> view;

    @FXML
    private TableColumn<BeanDeliverAddress,String> col_province;

    @FXML
    private TableColumn<BeanDeliverAddress,String> col_city;

    @FXML
    private TableColumn<BeanDeliverAddress,String> col_district;

    @FXML
    private TableColumn<BeanDeliverAddress,String> col_address;

    @FXML
    private TableColumn<BeanDeliverAddress,String> col_contact;

    @FXML
    private TableColumn<BeanDeliverAddress,String> col_tel;

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

    @FXML
    public void initialize() {
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
    }
}
