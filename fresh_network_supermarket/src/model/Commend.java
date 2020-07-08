package model;

public class Commend {
    private int goods_id;
    private int cookbook_id;
    private String commend_description;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getCookbook_id() {
        return cookbook_id;
    }

    public void setCookbook_id(int cookbook_id) {
        this.cookbook_id = cookbook_id;
    }

    public String getCommend_description() {
        return commend_description;
    }

    public void setCommend_description(String commend_description) {
        this.commend_description = commend_description;
    }
}
