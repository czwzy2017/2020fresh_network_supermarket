package model;

import java.util.Date;

public class BeanPromotion {
    String promotion;
    String goods_id;
    double promotion_price;
    double promotion_count;
    Date promotion_begin_date;
    Date promotion_end_date;

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public double getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(double promotion_price) {
        this.promotion_price = promotion_price;
    }

    public double getPromotion_count() {
        return promotion_count;
    }

    public void setPromotion_count(double promotion_count) {
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
