package control.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.DiscountManager;
import model.*;
import util.BusinessException;

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
    private TextField text_count;

    @FXML
    private TextField text_discount;

    @FXML
    private TextArea text_detail;

    @FXML
    private DatePicker date_begin;

    @FXML
    private DatePicker date_end;

    @FXML
    private TextField text_goods_id;

    @FXML
    private AdminMenuController menuController;

    public ObservableList<BeanFullDiscount> fullDiscounts = FXCollections.observableArrayList();

    public ObservableList<DiscountGoods> goods = FXCollections.observableArrayList();

    public void loadFullDiscount() {
        fullDiscounts = new DiscountManager().loadDiscount();
        col_discount_id.setCellValueFactory(new PropertyValueFactory<>("discount_id"));
        col_discount_detail.setCellValueFactory(new PropertyValueFactory<>("discount_detail"));
        col_discount_count.setCellValueFactory(new PropertyValueFactory<>("discount_goods_count"));
        col_discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        col_discount_begin.setCellValueFactory(new PropertyValueFactory<>("discount_begin_date"));
        col_discount_end.setCellValueFactory(new PropertyValueFactory<>("discount_end_date"));
        view_discount.getItems().setAll(fullDiscounts);
    }

    public void addDiscount() {
        if ("".equals(text_count.getText().trim())) throw new BusinessException("适用商品数量不能为空");
        if ("".equals(text_discount.getText().trim())) throw new BusinessException("折扣不能为空");
        if (date_begin.getValue()==null) throw new BusinessException("起始日期不能为空");
        if (date_end.getValue()==null) throw new BusinessException("结束日期不能为空");
        int count;
        double discount;
        try{
            count=Integer.valueOf(text_count.getText().trim());
        }catch (NumberFormatException e){
            throw new BusinessException("商品数量必须为正整数");
        }
        try {
            discount=Double.valueOf(text_discount.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("折扣必须为0~1的实数");
        }
        BeanFullDiscount fullDiscount=new BeanFullDiscount();
        fullDiscount.setDiscount_goods_count(count);
        fullDiscount.setDiscount(discount);
        fullDiscount.setDiscount_detail(text_detail.getText());
        fullDiscount.setDiscount_begin_date(dateConversion(date_begin.getValue()));
        fullDiscount.setDiscount_end_date(dateConversion(date_end.getValue()));
        new DiscountManager().addDiscount(fullDiscount);
        text_count.clear();
        text_detail.clear();
        text_discount.clear();
        loadFullDiscount();
    }


    public void deleteDiscount() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_discount.getSelectionModel().getSelectedIndex();
            new DiscountManager().deleteDiscount(view_discount.getItems().get(selectedIndex));
            view_discount.getItems().remove(selectedIndex);
        }
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

    public void addGoods(){
        if ("".equals(text_goods_id.getText().trim())) throw new BusinessException("商品编号不能为空");
        int id;
        try{
            id=Integer.valueOf(text_goods_id.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("商品编号必须为整数");
        }
        DiscountGoods discountGoods=new DiscountGoods();
        BeanFullDiscount currentDiscount=view_discount.getItems().get(view_discount.getSelectionModel().getSelectedIndex());
        discountGoods.setDiscount_id(currentDiscount.getDiscount_id());
        discountGoods.setGoods_id(id);
        discountGoods.setDiscount_begin_date(currentDiscount.getDiscount_begin_date());
        discountGoods.setDiscount_end_date(currentDiscount.getDiscount_end_date());
        new DiscountManager().addGoods(discountGoods);
        showGoods(currentDiscount);
        text_goods_id.clear();
    }

    public void deleteGoods() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_goods.getSelectionModel().getSelectedIndex();
            new DiscountManager().deleteGoods(view_goods.getItems().get(selectedIndex));
            view_goods.getItems().remove(selectedIndex);
        }
    }

    public void deleteAll(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_discount.getSelectionModel().getSelectedIndex();
            new DiscountManager().deleteAll(view_discount.getItems().get(selectedIndex));
            showGoods(null);
        }
    }

    @FXML
    public void initialize() {
        loadFullDiscount();
        showGoods(null);
        menuController.text_name.setText("欢迎您，" + BeanAdmin.currentLoginAdmin.getAdmin_name() + "     您的员工号为：" + BeanAdmin.currentLoginAdmin.getAdmin_id());
        view_discount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGoods(newValue));
    }

    private Date dateConversion(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }
}
