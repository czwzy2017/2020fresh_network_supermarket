package model;

import java.util.Date;

public class DiscountGoods {
    private String discount_id;
    private String goods_id;
    private Date discount_begin_date;
    private Date discount_end_date;

    public String getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(String discount_id) {
        this.discount_id = discount_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
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
