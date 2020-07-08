package model;

import java.util.Date;

public class BeanGoodsOrders {
    private int orders_id;
    private int coupon_id;
    private int user_id;
    private int delivery_id;
    private double order_original_price;
    private double oder_final_price;
    private Date order_time;
    private String order_status;

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

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
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
