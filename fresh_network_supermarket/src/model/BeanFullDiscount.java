package model;

import java.util.Date;

public class BeanFullDiscount {
    private int discount_id;
    private  String discount_detail;
    private int discount_goods_count;
    private double discount;
    private Date discount_begin_date;
    private Date discount_end_date;

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    public String getDiscount_detail() {
        return discount_detail;
    }

    public void setDiscount_detail(String discount_detail) {
        this.discount_detail = discount_detail;
    }

    public int getDiscount_goods_count() {
        return discount_goods_count;
    }

    public void setDiscount_goods_count(int discount_goods_count) {
        this.discount_goods_count = discount_goods_count;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDiscount_begin_date() {
        return discount_begin_date;
    }

    public void setDiscount_begin_date(Date discount_begin_date) {
        this.discount_begin_date = discount_begin_date;
    }

    public Date getDiscount_end_date() {
        return discount_end_date;
    }

    public void setDiscount_end_date(Date discount_end_date) {
        this.discount_end_date = discount_end_date;
    }

    public String toString(){
        return "满"+discount_goods_count+"件打"+discount*10+"折";
    }
}
