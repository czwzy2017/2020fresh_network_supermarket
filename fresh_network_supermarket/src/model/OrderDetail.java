package model;

public class OrderDetail {
    private int orders_id;
    private int discount_id;
    private String discount_id_string;
    private int goods_id;
    private int detail_count;
    private int promotion_count;
    private double goods_price;
    private double discount;
    private int discount_goods_count;
    private String discount_detail;

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

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

    public int getDetail_count() {
        return detail_count;
    }

    public void setDetail_count(int detail_count) {
        this.detail_count = detail_count;
    }

    public int getPromotion_count() {
        return promotion_count;
    }

    public void setPromotion_count(int promotion_count) {
        this.promotion_count = promotion_count;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getDiscount_goods_count() {
        return discount_goods_count;
    }

    public void setDiscount_goods_count(int discount_goods_count) {
        this.discount_goods_count = discount_goods_count;
    }

    public void setDiscount_detail(){
        if (discount_id!=0) this.discount_detail= "满"+discount_goods_count+"件打"+discount*10+"折";
    }

    public void setDiscount_id_string(){
        if (discount_id!=0) this.discount_id_string=String.valueOf(discount_id);
    }
}
