package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import main.MainApp;
import manager.FreshManager;
import model.BeanFreshCategory;
import model.BeanGoods;
import model.BeanUser;

import java.io.IOException;

public class StoreController {
    @FXML
    private TableView<BeanGoods> view;

    @FXML
    private TableColumn<BeanGoods,String> col_name;

    @FXML
    private TableColumn<BeanGoods,Double> col_price;

    @FXML
    private TableColumn<BeanGoods,String> col_promotion;

    @FXML
    private TableColumn<BeanGoods,Integer> col_promotion_count;

    @FXML
    private TableColumn<BeanGoods,Double> col_vip;

    @FXML
    private TableColumn<BeanGoods,Integer> col_count;

    @FXML
    private UserMenuController menuController;

    @FXML
    private ComboBox<String> box;

    public ObservableList<BeanGoods> goods = FXCollections.observableArrayList();

    public void showGoods(BeanFreshCategory category){
        if (category != null) {
            goods = new FreshManager().loadGoods(category.getCategory_id());
        } else {
            goods = new FreshManager().loadAllGoods();
        }
        col_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("goods_price"));
        col_promotion.setCellValueFactory(new PropertyValueFactory<>("goods_promotion"));
        col_promotion_count.setCellValueFactory(new PropertyValueFactory<>("promotion_count"));
        col_vip.setCellValueFactory(new PropertyValueFactory<>("goods_vip_price"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("goods_count"));
        view.getItems().setAll(goods);
    }

    @FXML
    public void initialize() {
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
        ObservableList<String> freshCategoriesName =new FreshManager().loadCategoryName();
        ObservableList<BeanFreshCategory> freshCategories =new FreshManager().loadCategory();
        box.setItems(freshCategoriesName);
        box.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> showGoods(freshCategories.get(box.getSelectionModel().getSelectedIndex()))
        );
        showGoods(null);
        view.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (event.getClickCount() == 2) {
                    try {
                        int goodsId=(view.getItems().get(view.getSelectionModel().getSelectedIndex()).getGoods_id());
                        Stage primaryStage = (Stage)view.getScene().getWindow();
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
