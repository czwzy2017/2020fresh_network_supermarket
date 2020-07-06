package model;

public class BeanGoodsProcurement {
    private String procurement_id;
    private String admin_id;
    private String goods_id;
    private int procurement_count;
    private String procurement_status;

    public String getProcurement_id() {
        return procurement_id;
    }

    public void setProcurement_id(String procurement_id) {
        this.procurement_id = procurement_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
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


