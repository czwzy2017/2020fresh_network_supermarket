package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.OrderManager;
import model.BeanGoodsOrders;
import model.BeanUser;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class OrdersController {
    @FXML
    private UserMenuController menuController;

    @FXML
    private TableView<BeanGoodsOrders> view;

    @FXML
    private TableColumn<BeanGoodsOrders,Integer> col_id;

    @FXML
    private TableColumn<BeanGoodsOrders,Double> col_original;

    @FXML
    private TableColumn<BeanGoodsOrders,Double> col_final;

    @FXML
    private TableColumn<BeanGoodsOrders, Date> col_time;

    @FXML
    private TableColumn<BeanGoodsOrders, Date> col_real_time;

    @FXML
    private TableColumn<BeanGoodsOrders,String> col_address;

    @FXML
    private TableColumn<BeanGoodsOrders,String> col_status;

    public ObservableList<BeanGoodsOrders> orders = FXCollections.observableArrayList();

    private void loadOrders(){
        orders = new OrderManager().loadOrder();
        col_id.setCellValueFactory(new PropertyValueFactory<>("orders_id"));
        col_original.setCellValueFactory(new PropertyValueFactory<>("order_original_price"));
        col_final.setCellValueFactory(new PropertyValueFactory<>("order_final_price"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("order_time"));
        col_real_time.setCellValueFactory(new PropertyValueFactory<>("order_real_time"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("order_address"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("order_status"));
        view.getItems().setAll(orders);
    }

    public void receipt(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认收货");
        alert.setHeaderText(null);
        alert.setContentText("您确认要收货吗，请保证商品已收到");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view.getSelectionModel().getSelectedIndex();
            new OrderManager().receipt(view.getItems().get(selectedIndex).getOrders_id());
            loadOrders();
        }
    }

    public void refund(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("退货");
        alert.setHeaderText(null);
        alert.setContentText("您确认要退货吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view.getSelectionModel().getSelectedIndex();
            new OrderManager().refund(view.getItems().get(selectedIndex));
            loadOrders();
        }
    }

    @FXML
    public void initialize() {
        loadOrders();
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
        view.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (event.getClickCount() == 2) {
                    try {
                        int orderId=(view.getItems().get(view.getSelectionModel().getSelectedIndex()).getOrders_id());
                        Stage primaryStage = (Stage)view.getScene().getWindow();
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("../../fxml/user/OrderDetail.fxml"));
                        primaryStage.setScene(new Scene((AnchorPane)loader.load()));
                        OrderDetailController controller=loader.<OrderDetailController>getController();
                        controller.loadOrdersDetail(orderId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
