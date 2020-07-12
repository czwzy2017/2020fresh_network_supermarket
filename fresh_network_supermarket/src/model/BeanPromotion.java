package model;

import java.util.Date;

public class BeanPromotion {
    private int promotion_id;
    private int goods_id;
    private String goods_name;
    private double promotion_price;
    private int promotion_count;
    private Date promotion_begin_date;
    private Date promotion_end_date;

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(double promotion_price) {
        this.promotion_price = promotion_price;
    }

    public int getPromotion_count() {
        return promotion_count;
    }

    public void setPromotion_count(int promotion_count) {
        this.promotion_count = promotion_count;
    }

    public Date getPromotion_begin_date() {
        return promotion_begin_date;
    }

    public void setPromotion_begin_date(Date promotion_begin_date) {
        this.promotion_begin_date = promotion_begin_date;
    }

    public Date getPromotion_end_date() {
        return promotion_end_date;
    }

    public void setPromotion_end_date(Date promotion_end_date) {
        this.promotion_end_date = promotion_end_date;
    }
}
