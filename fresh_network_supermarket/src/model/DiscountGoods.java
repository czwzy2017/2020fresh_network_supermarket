package model;

import java.util.Date;

public class DiscountGoods {
    private int discount_id;
    private int goods_id;
    private String goods_name;
    private Date discount_begin_date;
    private Date discount_end_date;

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
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
}
