package control.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.MainApp;
import manager.FreshManager;
import model.BeanAdmin;
import model.BeanFreshCategory;
import model.BeanGoods;
import util.BaseException;
import util.BusinessException;

import java.io.IOException;
import java.util.Optional;

public class FreshController {
    @FXML
    private TableView<BeanFreshCategory> view_category;

    @FXML
    private TableColumn<BeanFreshCategory, Integer> col_category_id;

    @FXML
    private TableColumn<BeanFreshCategory, String> col_category_name;

    @FXML
    private TableColumn<BeanFreshCategory, String> col_category_detail;

    @FXML
    private TableView<BeanGoods> view_goods;

    @FXML
    private TableColumn<BeanGoods, Integer> col_goods_id;

    @FXML
    private TableColumn<BeanGoods, String> col_goods_name;

    @FXML
    private TableColumn<BeanGoods, Double> col_goods_price;

    @FXML
    private TableColumn<BeanGoods, String> col_goods_vip;

    @FXML
    private TableColumn<BeanGoods, Integer> col_goods_count;

    @FXML
    private TableColumn<BeanGoods, String> col_goods_size;

    @FXML
    private TableColumn<BeanGoods, String> col_goods_detail;

    @FXML
    private TextField text_id;

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_detail;

    @FXML
    private TextField text_goods_id;

    @FXML
    private AdminMenuController menuController;

    public ObservableList<BeanFreshCategory> categories = FXCollections.observableArrayList();

    public ObservableList<BeanGoods> goods = FXCollections.observableArrayList();

    public void loadCategory() {
        categories = new FreshManager().loadCategory();
        col_category_id.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        col_category_name.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        col_category_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_category_detail.setCellValueFactory(new PropertyValueFactory<>("category_description"));
        col_category_detail.setCellFactory(TextFieldTableCell.forTableColumn());
        view_category.getItems().setAll(categories);
    }

    public void selectCategory() {
        int id;
        try{
            id=Integer.valueOf(text_id.getText());
        } catch (NumberFormatException e){
            throw new BusinessException("请输入数字查询");
        }
        BeanFreshCategory r = new FreshManager().selectCategory(id);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("类别信息");
        alert.setHeaderText(null);
        alert.setContentText("类别编号：" + r.getCategory_id() + "\n类别名称：" + r.getCategory_name() + "\n类别描述：" + r.getCategory_description());
        alert.showAndWait();
    }

    public void addCategory() {
        if ("".equals(text_name.getText())) throw new BusinessException("类别名字不能为空");
        String name = text_name.getText();
        String detail = text_detail.getText();
        new FreshManager().addCategory(name, detail);
        text_detail.clear();
        text_name.clear();
        loadCategory();
    }


    public void deleteCategory() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_category.getSelectionModel().getSelectedIndex();
            new FreshManager().deleteCategory(view_category.getItems().get(selectedIndex));
            view_category.getItems().remove(selectedIndex);
        }
    }

    public void showGoods(BeanFreshCategory category) {
        if (category != null) {
            goods = new FreshManager().loadGoods(category.getCategory_id());
            col_goods_id.setCellValueFactory(new PropertyValueFactory<>("goods_id"));
            col_goods_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
            col_goods_name.setCellFactory(TextFieldTableCell.forTableColumn());
            col_goods_price.setCellValueFactory(new PropertyValueFactory<>("goods_price"));
            col_goods_price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            col_goods_vip.setCellValueFactory(new PropertyValueFactory<>("vip_price_string"));
            col_goods_vip.setCellFactory(TextFieldTableCell.forTableColumn());
            col_goods_count.setCellValueFactory(new PropertyValueFactory<>("goods_count"));
            col_goods_count.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            col_goods_size.setCellValueFactory(new PropertyValueFactory<>("goods_size"));
            col_goods_size.setCellFactory(TextFieldTableCell.forTableColumn());
            col_goods_detail.setCellValueFactory(new PropertyValueFactory<>("goods_detail"));
            col_goods_detail.setCellFactory(TextFieldTableCell.forTableColumn());
            view_goods.getItems().setAll(goods);
        } else {
            view_goods.getItems().clear();
        }
    }

    public void selectGoods() {
        int id;
        try {
            id = Integer.valueOf(text_goods_id.getText());
        }catch (NumberFormatException e){
            throw new BusinessException("请输入数字查询");
        }
        BeanGoods r = new FreshManager().selectGoods(id);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("商品信息");
        alert.setHeaderText(null);
        alert.setContentText("商品编号：" + r.getGoods_id() + "\n类别名称：" + r.getCategory_name() + "\n商品名称：" + r.getGoods_name()
                + "\n商品价格：" + r.getGoods_price()+"\n会员价："+r.getVip_price_string()+"\n商品数量："+r.getGoods_count()+"\n商品规格："+r.getGoods_size()+"\n商品详情："+r.getGoods_detail());
        alert.showAndWait();
    }

    public void eventAdd() throws IOException {
        new MainApp().showAddGoods();
        Stage primaryStage = (Stage) view_goods.getScene().getWindow();
        primaryStage.close();
    }

    public void deleteGoods() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_goods.getSelectionModel().getSelectedIndex();
            new FreshManager().deleteGoods(view_goods.getItems().get(selectedIndex));
            view_goods.getItems().remove(selectedIndex);
        }
    }

    @FXML
    public void initialize() {
        loadCategory();
        showGoods(null);
        menuController.text_name.setText("欢迎您，" + BeanAdmin.currentLoginAdmin.getAdmin_name() + "     您的员工号为：" + BeanAdmin.currentLoginAdmin.getAdmin_id());

        col_category_name.setOnEditCommit(categoryNameColEdit -> {
            categoryNameColEdit.getTableView().getItems().get(categoryNameColEdit.getTablePosition().getRow()).setCategory_name(categoryNameColEdit.getNewValue().trim());
            try {
                new FreshManager().modifyCategory(categoryNameColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                loadCategory();
            }
        });


        col_category_detail.setOnEditCommit(categoryDetailColEdit -> {
            categoryDetailColEdit.getTableView().getItems().get(categoryDetailColEdit.getTablePosition().getRow()).setCategory_description(categoryDetailColEdit.getNewValue().trim());
            try {
                new FreshManager().modifyCategory(categoryDetailColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                loadCategory();
            }
        });

        col_goods_name.setOnEditCommit(goodsColEdit -> {
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setGoods_name(goodsColEdit.getNewValue().trim());
            try {
                new FreshManager().modifyGoods(goodsColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                BeanFreshCategory category=new BeanFreshCategory();
                category.setCategory_id(goodsColEdit.getRowValue().getCategory_id());
                showGoods(category);
            }
        });

        col_goods_price.setOnEditCommit(goodsColEdit -> {
            if (goodsColEdit.getNewValue()==null) throw new BusinessException("商品价格不能为空");
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setGoods_price(goodsColEdit.getNewValue());
            try {
                new FreshManager().modifyGoods(goodsColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                BeanFreshCategory category=new BeanFreshCategory();
                category.setCategory_id(goodsColEdit.getRowValue().getCategory_id());
                showGoods(category);
            }
        });

        col_goods_vip.setOnEditCommit(goodsColEdit -> {
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setVip_price_string(goodsColEdit.getNewValue().trim());
            try {
                new FreshManager().modifyGoods(goodsColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                BeanFreshCategory category=new BeanFreshCategory();
                category.setCategory_id(goodsColEdit.getRowValue().getCategory_id());
                showGoods(category);
            }
        });

        col_goods_count.setOnEditCommit(goodsColEdit -> {
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setGoods_count(goodsColEdit.getNewValue());
            try {
                new FreshManager().modifyGoods(goodsColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                BeanFreshCategory category=new BeanFreshCategory();
                category.setCategory_id(goodsColEdit.getRowValue().getCategory_id());
                showGoods(category);
            }
        });

        col_goods_size.setOnEditCommit(goodsColEdit -> {
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setGoods_size(goodsColEdit.getNewValue().trim());
            try {
                new FreshManager().modifyGoods(goodsColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                BeanFreshCategory category=new BeanFreshCategory();
                category.setCategory_id(goodsColEdit.getRowValue().getCategory_id());
                showGoods(category);
            }
        });

        col_goods_detail.setOnEditCommit(goodsColEdit -> {
            goodsColEdit.getTableView().getItems().get(goodsColEdit.getTablePosition().getRow()).setGoods_detail(goodsColEdit.getNewValue().trim());
            try {
                new FreshManager().modifyGoods(goodsColEdit.getRowValue());
            } catch (BaseException e) {
                outputError(e);
                BeanFreshCategory category=new BeanFreshCategory();
                category.setCategory_id(goodsColEdit.getRowValue().getCategory_id());
                showGoods(category);
            }
        });

        view_goods.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (event.getClickCount() == 2) {
                    try {
                        new MainApp().showComment(view_goods.getItems().get(view_goods.getSelectionModel().getSelectedIndex()).getGoods_id());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        view_category.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGoods(newValue));
    }

    private void outputError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
