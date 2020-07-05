package model;

public class UserCoupon {
    String coupon_id;
    String user_id;
    int user_coupon_count;

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getUser_coupon_count() {
        return user_coupon_count;
    }

    public void setUser_coupon_count(int user_coupon_count) {
        this.user_coupon_count = user_coupon_count;
    }
}
