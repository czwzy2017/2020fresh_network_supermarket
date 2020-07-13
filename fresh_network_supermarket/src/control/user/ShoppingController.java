package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import manager.AddressManager;
import manager.OrderManager;
import model.*;
import util.BaseException;
import util.BusinessException;

import java.util.Optional;

public class ShoppingController {
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

    @FXML
    private Text text_sale;

    @FXML
    private Text text_price;

    @FXML
    private Text text_discount;

    @FXML
    private Text text_coupon;

    @FXML
    private ComboBox<String> box;

    public ObservableList<OrderDetail> orderDetails = FXCollections.observableArrayList();

    public void loadOrdersDetail(int id){
        orderDetails = new OrderManager().loadOrderDetail(id);
        col_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("goods_price"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("detail_count"));
        col_count.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_promotion.setCellValueFactory(new PropertyValueFactory<>("promotion_count"));
        col_discount_id.setCellValueFactory(new PropertyValueFactory<>("discount_id_string"));
        col_discount_detail.setCellValueFactory(new PropertyValueFactory<>("discount_detail"));
        view.getItems().setAll(orderDetails);
    }

    public void setPrice(){
        new OrderManager().setPrice();
        text_sale.setText(String.valueOf(BeanGoodsOrders.currentOrders.getOrder_final_price()));
        text_price.setText(String.valueOf(BeanGoodsOrders.currentOrders.getOrder_original_price()));
        text_discount.setText(BeanGoodsOrders.currentOrders.getDiscount());
        text_coupon.setText(BeanGoodsOrders.currentOrders.getCoupon());
    }

    public void buy(){
        new OrderManager().buy();
        BeanGoodsOrders.currentOrders=null;
        view.getItems().clear();
        text_price.setText("0");
        text_coupon.setText("无");
        text_discount.setText("无");
        text_sale.setText("0");
    }

    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view.getSelectionModel().getSelectedIndex();
            new OrderManager().delete(view.getItems().get(selectedIndex));
            view.getItems().remove(selectedIndex);
            setPrice();
        }
    }


    @FXML
    public void initialize() {
        if (BeanGoodsOrders.currentOrders!=null) {
            loadOrdersDetail(BeanGoodsOrders.currentOrders.getOrders_id());
            setPrice();
        }
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
        col_count.setOnEditCommit(goodsColEdit -> {
            if (goodsColEdit.getNewValue()<=0) throw new BusinessException("请输入大于0的数量");
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setDetail_count(goodsColEdit.getNewValue());
            try {
                new OrderManager().modify(goodsColEdit.getRowValue());
                loadOrdersDetail(BeanGoodsOrders.currentOrders.getOrders_id());
                setPrice();
            } catch (BaseException e) {
                outputError(e);
                loadOrdersDetail(BeanGoodsOrders.currentOrders.getOrders_id());
                setPrice();
            }
        });
        ObservableList<String> addressName =new AddressManager().loadAddressName();
        box.setItems(addressName);
        box.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> BeanGoodsOrders.currentOrders.setOrder_address(addressName.get(box.getSelectionModel().getSelectedIndex()))
        );
    }

    private void outputError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
