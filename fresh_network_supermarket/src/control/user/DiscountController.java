package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.DiscountManager;
import model.*;
import util.BusinessException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class DiscountController {
    @FXML
    private TableView<BeanFullDiscount> view_discount;

    @FXML
    private TableColumn<BeanFullDiscount, Integer> col_discount_id;

    @FXML
    private TableColumn<BeanFullDiscount, String> col_discount_detail;

    @FXML
    private TableColumn<BeanFullDiscount, Integer> col_discount_count;

    @FXML
    private TableColumn<BeanFullDiscount, Double> col_discount;

    @FXML
    private TableColumn<BeanFullDiscount, Date> col_discount_begin;

    @FXML
    private TableColumn<BeanFullDiscount, Date> col_discount_end;

    @FXML
    private TableView<DiscountGoods> view_goods;

    @FXML
    private TableColumn<DiscountGoods, Integer> col_goods_id;

    @FXML
    private TableColumn<DiscountGoods, String> col_goods_name;

    @FXML
    private UserMenuController menuController;

    public ObservableList<BeanFullDiscount> fullDiscounts = FXCollections.observableArrayList();

    public ObservableList<DiscountGoods> goods = FXCollections.observableArrayList();

    public void loadFullDiscount() {
        fullDiscounts = new DiscountManager().loadCurrentDiscount();
        col_discount_id.setCellValueFactory(new PropertyValueFactory<>("discount_id"));
        col_discount_detail.setCellValueFactory(new PropertyValueFactory<>("discount_detail"));
        col_discount_count.setCellValueFactory(new PropertyValueFactory<>("discount_goods_count"));
        col_discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        col_discount_begin.setCellValueFactory(new PropertyValueFactory<>("discount_begin_date"));
        col_discount_end.setCellValueFactory(new PropertyValueFactory<>("discount_end_date"));
        view_discount.getItems().setAll(fullDiscounts);
    }

    public void showGoods(BeanFullDiscount discount) {
        if (discount != null) {
            goods = new DiscountManager().loadGoods(discount.getDiscount_id());
            col_goods_id.setCellValueFactory(new PropertyValueFactory<>("goods_id"));
            col_goods_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
            view_goods.getItems().setAll(goods);
        } else {
            view_goods.getItems().clear();
        }
    }

    @FXML
    public void initialize() {
        loadFullDiscount();
        showGoods(null);
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
        view_discount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGoods(newValue));
        view_goods.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (event.getClickCount() == 2) {
                    try {
                        int goodsId=(view_goods.getItems().get(view_goods.getSelectionModel().getSelectedIndex()).getGoods_id());
                        Stage primaryStage = (Stage)view_goods.getScene().getWindow();
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("../../fxml/user/Goods.fxml"));
                        primaryStage.setScene(new Scene((AnchorPane)loader.load()));
                        GoodsController controller=loader.<GoodsController>getController();
                        controller.show(goodsId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
