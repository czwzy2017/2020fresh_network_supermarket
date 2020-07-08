package model;

public class BeanDeliverAddress {
    private int delivery_id;
    private int user_id;
    private String delivery_province;
    private String delivery_city;
    private String delivery_district;
    private String delivery_address;
    private String delivery_contact;
    private String delivery_tel;

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDelivery_province() {
        return delivery_province;
    }

    public void setDelivery_province(String delivery_province) {
        this.delivery_province = delivery_province;
    }

    public String getDelivery_city() {
        return delivery_city;
    }

    public void setDelivery_city(String delivery_city) {
        this.delivery_city = delivery_city;
    }

    public String getDelivery_district() {
        return delivery_district;
    }

    public void setDelivery_district(String delivery_district) {
        this.delivery_district = delivery_district;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getDelivery_contact() {
        return delivery_contact;
    }

    public void setDelivery_contact(String delivery_contact) {
        this.delivery_contact = delivery_contact;
    }

    public String getDelivery_tel() {
        return delivery_tel;
    }

    public void setDelivery_tel(String delivery_tel) {
        this.delivery_tel = delivery_tel;
    }
}
