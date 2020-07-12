package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.FreshManager;
import model.BeanFreshCategory;
import model.BeanGoods;
import model.BeanUser;

public class StoreController {
    @FXML
    private TableView<BeanGoods> view;

    @FXML
    private TableColumn<BeanGoods,String> col_name;

    @FXML
    private TableColumn<BeanGoods,Double> col_price;

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
            col_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
            col_price.setCellValueFactory(new PropertyValueFactory<>("goods_price"));
            col_vip.setCellValueFactory(new PropertyValueFactory<>("goods_vip_price"));
            col_count.setCellValueFactory(new PropertyValueFactory<>("goods_count"));
            view.getItems().setAll(goods);
        } else {
            view.getItems().clear();
        }
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
    }
}
