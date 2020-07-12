package control.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.PromotionManager;
import model.BeanAdmin;
import model.BeanPromotion;
import util.BusinessException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class PromotionController {
    @FXML
    private TableView<BeanPromotion> view_promotion;

    @FXML
    private TableColumn<BeanPromotion, Integer> col_promotion_id;

    @FXML
    private TableColumn<BeanPromotion, Integer> col_goods_id;

    @FXML
    private TableColumn<BeanPromotion, String> col_goods_name;

    @FXML
    private TableColumn<BeanPromotion, Double> col_price;

    @FXML
    private TableColumn<BeanPromotion, Integer> col_count;

    @FXML
    private TableColumn<BeanPromotion, Date> col_begin_time;

    @FXML
    private TableColumn<BeanPromotion, Date> col_end_time;

    @FXML
    private TextField text_add_id;

    @FXML
    private TextField text_price;

    @FXML
    private TextField text_count;

    @FXML
    private TextField text_select_id;

    @FXML
    private DatePicker date_begin;

    @FXML
    private DatePicker date_end;

    @FXML
    private AdminMenuController menuController;

    public ObservableList<BeanPromotion> promotions = FXCollections.observableArrayList();

    public void loadPromotion() {
        promotions = new PromotionManager().loadPromotion();
        col_promotion_id.setCellValueFactory(new PropertyValueFactory<>("promotion_id"));
        col_goods_id.setCellValueFactory(new PropertyValueFactory<>("goods_id"));
        col_goods_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("promotion_price"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("promotion_count"));
        col_begin_time.setCellValueFactory(new PropertyValueFactory<>("promotion_begin_date"));
        col_end_time.setCellValueFactory(new PropertyValueFactory<>("promotion_end_date"));
        view_promotion.getItems().setAll(promotions);
    }

    public void add() {
        BeanPromotion r = new BeanPromotion();
        if ("".equals(text_add_id.getText().trim())) throw new BusinessException("商品编号不能为空");
        if ("".equals(text_price.getText().trim())) throw new BusinessException("促销金额不能为空");
        if ("".equals(text_count.getText().trim())) throw new BusinessException("促销数量不能为空");
        if (date_begin.getValue() == null) throw new BusinessException("起始日期不能为空");
        if (date_end.getValue() == null) throw new BusinessException("结束日期不能为空");
        double price;
        try {
            price = Double.valueOf(text_price.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("适用金额必须为实数");
        }
        int count,goodId;
        try{
            count = Integer.valueOf(text_count.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("促销数量必须为正整数");
        }
        try {
            goodId = Integer.valueOf(text_add_id.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("商品编号必须为正整数");
        }
        r.setGoods_id(goodId);
        r.setPromotion_price(price);
        r.setPromotion_count(count);
        r.setPromotion_begin_date(dateConversion(date_begin.getValue()));
        r.setPromotion_end_date(dateConversion(date_end.getValue()));
        new PromotionManager().add(r);
        text_price.clear();
        text_count.clear();
        text_add_id.clear();
        loadPromotion();
    }

    public void select() {
        int id;
        try {
            id = Integer.valueOf(text_select_id.getText().trim());
        } catch (NumberFormatException e) {
            throw new BusinessException("请输入数字查询");
        }
        promotions = new PromotionManager().selectPromotion(id);
        col_promotion_id.setCellValueFactory(new PropertyValueFactory<>("promotion_id"));
        col_goods_id.setCellValueFactory(new PropertyValueFactory<>("goods_id"));
        col_goods_name.setCellValueFactory(new PropertyValueFactory<>("goods_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("promotion_price"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("promotion_count"));
        col_begin_time.setCellValueFactory(new PropertyValueFactory<>("promotion_begin_date"));
        col_end_time.setCellValueFactory(new PropertyValueFactory<>("promotion_end_date"));
        view_promotion.getItems().setAll(promotions);
        text_select_id.clear();
    }

    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_promotion.getSelectionModel().getSelectedIndex();
            new PromotionManager().delete(view_promotion.getItems().get(selectedIndex));
            view_promotion.getItems().remove(selectedIndex);
        }
    }


    @FXML
    public void initialize() {
        loadPromotion();
        menuController.text_name.setText("欢迎您，" + BeanAdmin.currentLoginAdmin.getAdmin_name() + "     您的员工号为：" + BeanAdmin.currentLoginAdmin.getAdmin_id());
    }

    private Date dateConversion(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }
}
