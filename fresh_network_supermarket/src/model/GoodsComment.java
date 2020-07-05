package model;

import java.util.Date;

public class GoodsComment {
    private String goods_id;
    private String user_id;
    private String comment_detail;
    private Date comment_date;
    private  int comment_level;
    private String comment_image;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment_detail() {
        return comment_detail;
    }

    public void setComment_detail(String comment_detail) {
        this.comment_detail = comment_detail;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public int getComment_level() {
        return comment_level;
    }

    public void setComment_level(int comment_level) {
        this.comment_level = comment_level;
    }

    public String getComment_image() {
        return comment_image;
    }

    public void setComment_image(String comment_image) {
        this.comment_image = comment_image;
    }
}
