package model;

import java.util.Date;

public class BeanUser {
    public static BeanUser currentLoginUser = null;
    private int user_id;
    private String user_name;
    private String user_sex;
    private String user_pwd;
    private String user_tel;
    private String user_email;
    private String user_city;
    private Date user_creat_time;
    private boolean user_vip;
    private Date user_vip_deadline;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public Date getUser_creat_time() {
        return user_creat_time;
    }

    public void setUser_creat_time(Date user_creat_time) {
        this.user_creat_time = user_creat_time;
    }

    public boolean isUser_vip() {
        return user_vip;
    }

    public void setUser_vip(boolean user_vip) {
        this.user_vip = user_vip;
    }

    public Date getUser_vip_deadline() {
        return user_vip_deadline;
    }

    public void setUser_vip_deadline(Date user_vip_deadline) {
        this.user_vip_deadline = user_vip_deadline;
    }
}
