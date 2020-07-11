package control.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.CouponManager;
import model.BeanAdmin;
import model.BeanCoupon;
import util.BusinessException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class CouponController {
    @FXML
    private TableView<BeanCoupon> view_coupon;

    @FXML
    private TableColumn<BeanCoupon, Integer> col_id;

    @FXML
    private TableColumn<BeanCoupon, Integer> col_detail;

    @FXML
    private TableColumn<BeanCoupon, Integer> col_minimum_price;

    @FXML
    private TableColumn<BeanCoupon, String> col_credit_price;

    @FXML
    private TableColumn<BeanCoupon, Date> col_begin_time;

    @FXML
    private TableColumn<BeanCoupon, Date> col_end_time;

    @FXML
    private TextField text_detail;

    @FXML
    private TextField text_minimum_price;

    @FXML
    private TextField text_credit_price;

    @FXML
    private DatePicker date_begin;

    @FXML
    private DatePicker date_end;

    @FXML
    private AdminMenuController menuController;

    public ObservableList<BeanCoupon> coupons = FXCollections.observableArrayList();

    public void loadCoupon() {
        coupons = new CouponManager().loadCoupon();
        col_id.setCellValueFactory(new PropertyValueFactory<>("coupon_id"));
        col_detail.setCellValueFactory(new PropertyValueFactory<>("coupon_detail"));
        col_minimum_price.setCellValueFactory(new PropertyValueFactory<>("coupon_minimum_price"));
        col_credit_price.setCellValueFactory(new PropertyValueFactory<>("coupon_credit_price"));
        col_begin_time.setCellValueFactory(new PropertyValueFactory<>("coupon_begin_date"));
        col_end_time.setCellValueFactory(new PropertyValueFactory<>("coupon_end_date"));
        view_coupon.getItems().setAll(coupons);
    }

    public void add() throws IOException {
        BeanCoupon r=new BeanCoupon();
        r.setCoupon_detail(text_detail.getText());
        if ("".equals(text_minimum_price.getText().trim())) throw new BusinessException("适用金额不能为空");
        if ("".equals(text_credit_price.getText().trim())) throw new BusinessException("减免金额不能为空");
        if (date_begin.getValue()==null) throw new BusinessException("起始日期不能为空");
        if (date_end.getValue()==null) throw new BusinessException("结束日期不能为空");
        double minimum,credit;
        try {
            minimum=Double.valueOf(text_minimum_price.getText().trim());
            credit=Double.valueOf(text_credit_price.getText().trim());
        }catch (NumberFormatException e){
            throw new BusinessException("金额必须为实数");
        }
        r.setCoupon_minimum_price(minimum);
        r.setCoupon_credit_price(credit);
        r.setCoupon_begin_date(dateConversion(date_begin.getValue()));
        r.setCoupon_end_date(dateConversion(date_end.getValue()));
        new CouponManager().add(r);
        text_detail.clear();
        text_minimum_price.clear();
        text_credit_price.clear();
        loadCoupon();
    }


    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除");
        alert.setHeaderText(null);
        alert.setContentText("你确认要删除吗");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = view_coupon.getSelectionModel().getSelectedIndex();
            new CouponManager().delete(view_coupon.getItems().get(selectedIndex));
            view_coupon.getItems().remove(selectedIndex);
        }
    }


    @FXML
    public void initialize() {
        loadCoupon();
        menuController.text_name.setText("欢迎您，" + BeanAdmin.currentLoginAdmin.getAdmin_name() + "     您的员工号为：" + BeanAdmin.currentLoginAdmin.getAdmin_id());
    }

    public Date dateConversion(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }
}
