package model;

import java.util.Date;

public class BeanGoodsOrders {
    String orders_id;
    String coupon_id;
    String user_id;
    String delivery_id;
    double order_original_price;
    double oder_final_price;
    Date order_time;
    String order_status;

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public double getOrder_original_price() {
        return order_original_price;
    }

    public void setOrder_original_price(double order_original_price) {
        this.order_original_price = order_original_price;
    }

    public double getOder_final_price() {
        return oder_final_price;
    }

    public void setOder_final_price(double oder_final_price) {
        this.oder_final_price = oder_final_price;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
