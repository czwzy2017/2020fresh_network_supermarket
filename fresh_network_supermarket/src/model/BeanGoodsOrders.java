package model;

import java.util.Date;

public class BeanGoodsOrders {
    public static BeanGoodsOrders currentOrders=null;
    private int orders_id;
    private int coupon_id;
    private String coupon;
    private int user_id;
    private String order_address;
    private double order_original_price;
    private double order_final_price;
    private Date order_time;
    private Date order_real_time;
    private String order_status;
    private String discount;

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getOrder_original_price() {
        return order_original_price;
    }

    public void setOrder_original_price(double order_original_price) {
        this.order_original_price = order_original_price;
    }

    public double getOrder_final_price() {
        return order_final_price;
    }

    public void setOrder_final_price(double order_final_price) {
        this.order_final_price = order_final_price;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Date getOrder_real_time() {
        return order_real_time;
    }

    public void setOrder_real_time(Date order_real_time) {
        this.order_real_time = order_real_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
