package model;

public class BeanGoodsProcurement {
    private int procurement_id;
    private String admin_id;
    private String admin_name;
    private int goods_id;
    private String goods_name;
    private int procurement_count;
    private String procurement_status;


    public int getProcurement_id() {
        return procurement_id;
    }

    public void setProcurement_id(int procurement_id) {
        this.procurement_id = procurement_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
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


    public int getProcurement_count() {
        return procurement_count;
    }

    public void setProcurement_count(int procurement_count) {
        this.procurement_count = procurement_count;
    }

    public String getProcurement_status() {
        return procurement_status;
    }

    public void setProcurement_status(String procurement_status) {
        this.procurement_status = procurement_status;
    }
}


