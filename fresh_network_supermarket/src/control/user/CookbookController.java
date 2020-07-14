package control.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import manager.CookbookManager;
import model.BeanAdmin;
import model.BeanCookbook;
import model.BeanUser;
import model.Commend;
import util.BaseException;

import java.io.IOException;

public class CookbookController {
    @FXML
    private TableView<BeanCookbook> view_cookbook;

    @FXML
    private TableColumn<BeanCookbook, Integer> col_cookbook_id;

    @FXML
    private TableColumn<BeanCookbook, String> col_cookbook_name;

    @FXML
    private TableView<Commend> view_goods;

    @FXML
    private TableColumn<Commend, Integer> col_goods_id;

    @FXML
    private TableColumn<Commend, String> col_goods_name;

    @FXML
    private TableColumn<Commend, String> col_commend_description;

    @FXML
    private Text text_id;

    @FXML
    private Text text_name;

    @FXML
    private Text text_ingredient;

    @FXML
    private Text text_step;

    @FXML
    private ImageView image;

    @FXML
    private UserMenuController menuController;

    public ObservableList<BeanCookbook> cookbooks = FXCollections.observableArrayList();

    public ObservableList<Commend> goods = FXCollections.observableArrayList();

    public void loadCookbook() {
        cookbooks = new CookbookManager().loadCookbook();
        col_cookbook_id.setCellValueFactory(new PropertyValueFactory<>("cookbook_id"));
        col_cookbook_name.setCellValueFactory(new PropertyValueFactory<>("cookbook_name"));
        view_cookbook.getItems().setAll(cookbooks);
    }

    public void showGoods(BeanCookbook cookbook) {
        if (cookbook != null) {
            goods = new CookbookManager().loadGoods(cookbook.getCookbook_id());
            col_goods_id.setCellValueFactory(new PropertyValueFactory<>("goods_id"));
            col_goods_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
            col_commend_description.setCellValueFactory(new PropertyValueFactory<>("commend_description"));
            col_commend_description.setCellFactory(TextFieldTableCell.forTableColumn());
            view_goods.getItems().setAll(goods);
        } else {
            view_goods.getItems().clear();
        }
    }

    public void showDetail(BeanCookbook cookbook) {
        if (cookbook != null) {
            text_id.setText(String.valueOf(cookbook.getCookbook_id()));
            text_name.setText(cookbook.getCookbook_name());
            text_ingredient.setText(cookbook.getCookbook_ingredient());
            text_step.setText(cookbook.getCookbook_step());
        } else {
            text_id.setText("");
            text_name.setText("");
            text_ingredient.setText("");
            text_step.setText("");
        }
    }

    @FXML
    public void initialize() {
        loadCookbook();
        showGoods(null);
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
        col_commend_description.setOnEditCommit(descriptionColEdit -> {
            descriptionColEdit.getTableView().getItems().get(descriptionColEdit.getTablePosition().getRow()).setCommend_description(descriptionColEdit.getNewValue().trim());
            try {
                Commend commend = descriptionColEdit.getRowValue();
                commend.setCookbook_id(view_cookbook.getItems().get(view_cookbook.getSelectionModel().getSelectedIndex()).getCookbook_id());
                new CookbookManager().modifyCommend(commend);
            } catch (BaseException e) {
                outputError(e);
                BeanCookbook cookbook = new BeanCookbook();
                cookbook.setCookbook_id(descriptionColEdit.getRowValue().getCookbook_id());
                showGoods(cookbook);
            }
        });

        view_cookbook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showGoods(newValue);
            showDetail(newValue);
        });

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

    private void outputError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
