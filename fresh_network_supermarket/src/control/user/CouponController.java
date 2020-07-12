package control.user;

import control.admin.AdminMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.CouponManager;
import model.BeanAdmin;
import model.BeanCoupon;
import model.BeanUser;
import model.UserCoupon;
import util.BusinessException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class CouponController {
    @FXML
    private UserMenuController menuController;
    @FXML
    private TableView<UserCoupon> view_coupon;

    @FXML
    private TableColumn<UserCoupon, Integer> col_id;

    @FXML
    private TableColumn<UserCoupon, String> col_detail;

    @FXML
    private TableColumn<UserCoupon, Double> col_minimum_price;

    @FXML
    private TableColumn<UserCoupon, Double> col_credit_price;

    @FXML
    private TableColumn<UserCoupon, Integer> col_count;

    @FXML
    private TableColumn<UserCoupon, Date> col_begin_time;

    @FXML
    private TableColumn<UserCoupon, Date> col_end_time;

    @FXML
    private TextField text_id;

    public ObservableList<UserCoupon> coupons = FXCollections.observableArrayList();

    public void loadCoupon() {
        coupons = new CouponManager().loadUserCoupon(BeanUser.currentLoginUser.getUser_id());
        col_id.setCellValueFactory(new PropertyValueFactory<>("coupon_id"));
        col_detail.setCellValueFactory(new PropertyValueFactory<>("coupon_detail"));
        col_minimum_price.setCellValueFactory(new PropertyValueFactory<>("coupon_minimum_price"));
        col_credit_price.setCellValueFactory(new PropertyValueFactory<>("coupon_credit_price"));
        col_begin_time.setCellValueFactory(new PropertyValueFactory<>("coupon_begin_date"));
        col_end_time.setCellValueFactory(new PropertyValueFactory<>("coupon_end_date"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("user_coupon_count"));
        view_coupon.getItems().setAll(coupons);
    }

    public void add() {
        int id;
        try{
            id=Integer.valueOf(text_id.getText().trim());
        }catch (NumberFormatException e){
            throw new BusinessException("请输入一个正整数");
        }
        new CouponManager().addUserCoupon(id);
        loadCoupon();
    }
    @FXML
    public void initialize() {
        loadCoupon();
        menuController.text_name.setText("欢迎您，" + BeanUser.currentLoginUser.getUser_name());
    }
}
