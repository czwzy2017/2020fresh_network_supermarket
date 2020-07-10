package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.MainApp;
import manager.CookbookManager;
import model.BeanAdmin;
import model.BeanCookbook;
import model.Commend;
import util.BaseException;
import util.BusinessException;

import java.io.IOException;
import java.util.Optional;

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
    private TextField text_cookbook_id;

    @FXML
    private TextField text_goods_id;

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
    private AdminMenuController menuController;

    public ObservableList<BeanCookbook> cookbooks = FXCollections.observableArrayList();

    public ObservableList<Commend> goods = FXCollections.observableArrayList();

    public void loadCookbook() {
        cookbooks = new CookbookManager().loadCookbook();
        col_cookbook_id.setCellValueFactory(new PropertyValueFactory<>("cookbook_id"));
        col_cookbook_name.setCellValueFactory(new PropertyValueFactory<>("cookbook_name"));
        view_cookbook.getItems().setAll(cookbooks);
    }

    public void select() {
        int id;
        try {
            id = Integer.valueOf(text_cookbook_id.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("请输入数字查询");
        }
        BeanCookbook r = new CookbookManager().selectCookbook(id);
        showDetail(r);
        showGoods(r);
    }

    public void eventAdd() throws IOException {
        new MainApp().showAddCookbook();
        Stage primaryStage = (Stage) view_cookbook.getScene().getWindow();
        primaryStage.close();
    }

    public void eventModify() {

    }

    public void deleteCookbook() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_cookbook.getSelectionModel().getSelectedIndex();
            new CookbookManager().deleteCookbook(view_cookbook.getItems().get(selectedIndex));
            view_cookbook.getItems().remove(selectedIndex);
        }
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

    public void add() {
        if ("".equals(text_goods_id.getText())) throw new BusinessException("商品编号不能为空");
        int goods_id;
        try {
            goods_id=Integer.valueOf(text_goods_id.getText());
        }catch (NumberFormatException e){
            throw new BusinessException("商品编号必须为数字");
        }
        int cookbook_id=view_cookbook.getItems().get(view_cookbook.getSelectionModel().getSelectedIndex()).getCookbook_id();
        new CookbookManager().addGoods(goods_id,cookbook_id);
        showGoods(view_cookbook.getItems().get(view_cookbook.getSelectionModel().getSelectedIndex()));
    }

    public void deleteGoods() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex =view_goods.getSelectionModel().getSelectedIndex();
            Commend commend=view_goods.getItems().get(selectedIndex);
            commend.setCookbook_id(view_cookbook.getItems().get(view_cookbook.getSelectionModel().getSelectedIndex()).getCookbook_id());
            new CookbookManager().deleteGoods(commend);
            view_goods.getItems().remove(selectedIndex);
        }
    }

    @FXML
    public void initialize() {
       loadCookbook();
        showGoods(null);
        menuController.text_name.setText("欢迎您，" + BeanAdmin.currentLoginAdmin.getAdmin_name() + "     您的员工号为：" + BeanAdmin.currentLoginAdmin.getAdmin_id());
        col_commend_description.setOnEditCommit(descriptionColEdit -> {
            descriptionColEdit.getTableView().getItems().get(descriptionColEdit.getTablePosition().getRow()).setCommend_description(descriptionColEdit.getNewValue().trim());
            try {
                Commend commend=descriptionColEdit.getRowValue();
                commend.setCookbook_id(view_cookbook.getItems().get(view_cookbook.getSelectionModel().getSelectedIndex()).getCookbook_id());
                new CookbookManager().modifyCommend(commend);
            } catch (BaseException e) {
                outputError(e);
                BeanCookbook cookbook=new BeanCookbook();
                cookbook.setCookbook_id(descriptionColEdit.getRowValue().getCookbook_id());
                showGoods(cookbook);
            }
        });

        view_cookbook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showGoods(newValue);
            showDetail(newValue);
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
