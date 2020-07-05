package model;

import java.util.Date;

public class BeanCoupon {
    private String coupon_id;
    private String coupon_detail;
    private double coupon_minimum_price;
    private double coupon_credit_price;
    private Date coupon_begin_date;
    private Date coupon_end_date;

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_detail() {
        return coupon_detail;
    }

    public void setCoupon_detail(String coupon_detail) {
        this.coupon_detail = coupon_detail;
    }

    public double getCoupon_minimum_price() {
        return coupon_minimum_price;
    }

    public void setCoupon_minimum_price(double coupon_minimum_price) {
        this.coupon_minimum_price = coupon_minimum_price;
    }

    public double getCoupon_credit_price() {
        return coupon_credit_price;
    }

    public void setCoupon_credit_price(double coupon_credit_price) {
        this.coupon_credit_price = coupon_credit_price;
    }

    public Date getCoupon_begin_date() {
        return coupon_begin_date;
    }

    public void setCoupon_begin_date(Date coupon_begin_date) {
        this.coupon_begin_date = coupon_begin_date;
    }

    public Date getCoupon_end_date() {
        return coupon_end_date;
    }

    public void setCoupon_end_date(Date coupon_end_date) {
        this.coupon_end_date = coupon_end_date;
    }
}
