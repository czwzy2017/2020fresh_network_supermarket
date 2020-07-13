package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.OrderManager;
import model.BeanGoodsOrders;
import model.BeanUser;
import model.OrderDetail;

public class OrderDetailController {
    @FXML
    private UserMenuController menuController;

    @FXML
    private TableView<OrderDetail> view;

    @FXML
    private TableColumn<OrderDetail,String> col_name;

    @FXML
    private TableColumn<OrderDetail,Double> col_price;

    @FXML
    private TableColumn<OrderDetail,Integer> col_count;

    @FXML
    private TableColumn<OrderDetail,Integer> col_promotion;

    @FXML
    private TableColumn<OrderDetail,String> col_discount_id;

    @FXML
    private TableColumn<OrderDetail,String> col_discount_detail;

    public ObservableList<OrderDetail> orderDetails = FXCollections.observableArrayList();

    public void loadOrdersDetail(int id){
        orderDetails = new OrderManager().loadOrderDetail(id);
        col_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("goods_price"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("detail_count"));
        col_promotion.setCellValueFactory(new PropertyValueFactory<>("promotion_count"));
        col_discount_id.setCellValueFactory(new PropertyValueFactory<>("discount_id_string"));
        col_discount_detail.setCellValueFactory(new PropertyValueFactory<>("discount_detail"));
        view.getItems().setAll(orderDetails);
    }

    @FXML
    public void initialize() {
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());

    }
}
