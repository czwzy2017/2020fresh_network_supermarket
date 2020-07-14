package model;

public class BeanGoods {
    private int goods_id;
    private int category_id;
    private String category_name;
    private String goods_name;
    private double goods_price;
    private String goods_promotion;
    private int promotion_count;
    private double goods_vip_price;
    private String vip_price_string;
    private int goods_count;
    private String goods_size;
    private String goods_detail;


    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_price() {
        return  ((int)(goods_price*100+0.5)/100.0);
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_promotion() {
        return goods_promotion;
    }

    public void setGoods_promotion(String goods_promotion) {
        this.goods_promotion = goods_promotion;
    }

    public int getPromotion_count() {
        return promotion_count;
    }

    public void setPromotion_count(int promotion_count) {
        this.promotion_count = promotion_count;
    }

    public double getGoods_vip_price() {
        return  ((int)(goods_vip_price*100+0.5)/100.0);
    }

    public void setGoods_vip_price(double goods_vip_price) {
        this.goods_vip_price = goods_vip_price;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public String getGoods_size() {
        return goods_size;
    }

    public void setGoods_size(String goods_size) {
        this.goods_size = goods_size;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getVip_price_string() {
        return vip_price_string;
    }

    public void setVip_price_string() {
        if (goods_vip_price!=0) this.vip_price_string =String.valueOf(goods_vip_price);
    }

    public void setVip_price_string(String vip_price_string){
        this.vip_price_string=vip_price_string;
    }
}
