package manager;

import model.BeanUser;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class UserManager {
    public void loadCurrentUser(int id){
        BeanUser r = new BeanUser();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user_infor where user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                r.setUser_id(rs.getInt(1));
                r.setUser_name(rs.getString(2));
                r.setUser_sex(rs.getString(3));
                r.setUser_pwd(rs.getString(4));
                r.setUser_tel(rs.getString(5));
                r.setUser_email(rs.getString(6));
                r.setUser_city(rs.getString(7));
                r.setUser_creat_time(rs.getTimestamp(8));
                r.setUser_vip(rs.getBoolean(9));
                r.setUser_vip_deadline(rs.getTimestamp(10));
            }
            BeanUser.currentLoginUser=r;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
    public BeanUser login(String tel, String pwd) {
        BeanUser r = new BeanUser();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user_infor where user_tel=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tel);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (!rs.getString(4).equals(pwd)) throw new BusinessException("密码错误");
            } else throw new BusinessException("用户不存在");
            r.setUser_id(rs.getInt(1));
            r.setUser_name(rs.getString(2));
            r.setUser_sex(rs.getString(3));
            r.setUser_pwd(pwd);
            r.setUser_tel(rs.getString(5));
            r.setUser_email(rs.getString(6));
            r.setUser_city(rs.getString(7));
            r.setUser_creat_time(rs.getTimestamp(8));
            r.setUser_vip_deadline(rs.getTimestamp(10));
            if (r.getUser_vip_deadline().before(new Timestamp(System.currentTimeMillis()))){
                sql = "update user_infor set user_vip=0 where user_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,r.getUser_id());
                pst.execute();
                r.setUser_vip(false);
            }else{
                sql = "update user_infor set user_vip=1 where user_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1,r.getUser_id());
                pst.execute();
                r.setUser_vip(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return r;
    }

    public int reg(String name, String sex, String pwd, String pwd2, String tel, String email, String city) throws BaseException {
        if ("".equals(name)) throw new BusinessException("姓名不能为空");
        if (!"男".equals(sex) && !"女".equals(sex)) throw new BusinessException("性别请输入男或女");
        if ("".equals(pwd)) throw new BusinessException("密码不能为空");
        if (!pwd.equals(pwd2)) throw new BusinessException("两次密码不一致");
        if ("".equals(tel)) throw new BusinessException("电话不能为空");
        if (tel.length() != 11) throw new BusinessException("请输入11位手机号");
        if ("".equals(city)) throw new BusinessException("城市不能为空");
        Connection conn = null;
        int id=0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user_infor where user_tel=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tel);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("手机号已被注册");
            pst.close();
            sql = "insert into user_infor(user_name, user_sex, user_pwd, user_tel, user_email, user_city, user_creat_time, user_vip,user_vip_deadline) values(?,?,?,?,?,?,?,0,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, sex);
            pst.setString(3, pwd);
            pst.setString(4, tel);
            pst.setString(5, email);
            pst.setString(6, city);
            pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.execute();
            pst.close();
            sql = "select user_id from user_infor where user_tel=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, tel);
            rs = pst.executeQuery();
            if (rs.next()) id=rs.getInt(1);
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return id;
    }

    public void modify(String name, String sex, String pwd, String pwd2, String tel, String email, String city){
        if ("".equals(name)) throw new BusinessException("姓名不能为空");
        if (!"男".equals(sex) && !"女".equals(sex)) throw new BusinessException("性别请输入男或女");
        if ("".equals(pwd)) throw new BusinessException("密码不能为空");
        if (!pwd.equals(pwd2)) throw new BusinessException("两次密码不一致");
        if ("".equals(tel)) throw new BusinessException("电话不能为空");
        if (tel.length() != 11) throw new BusinessException("请输入11位手机号");
        if ("".equals(city)) throw new BusinessException("城市不能为空");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user_infor where user_tel=? and user_id!=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tel);
            pst.setInt(2,BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("手机号已被注册");
            rs.close();
            pst.close();
            sql = "update user_infor set user_name=?, user_sex=?, user_pwd=?, user_tel=?, user_email=?, user_city=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, sex);
            pst.setString(3, pwd);
            pst.setString(4, tel);
            pst.setString(5, email);
            pst.setString(6, city);
            pst.setInt(7,BeanUser.currentLoginUser.getUser_id());
            pst.execute();
            pst.close();
            loadCurrentUser(BeanUser.currentLoginUser.getUser_id());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void vip(int month){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            Calendar calendar=Calendar.getInstance();
            String sql = "select user_vip,user_vip_deadline from user_infor where user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()){
                if (rs.getInt(1)==1){
                    calendar.setTime(rs.getTimestamp(2));
                    calendar.add(Calendar.MONTH,month);
                }else{
                    calendar.setTime(new Date());
                    calendar.add(Calendar.MONTH,month);
                }
            }
            rs.close();
            pst.close();
            java.util.Date date=calendar.getTime();
            sql = "update user_infor set user_vip=1,user_vip_deadline=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1,new Timestamp(date.getTime()));
            pst.setInt(2, BeanUser.currentLoginUser.getUser_id());
            pst.execute();
            pst.close();
            loadCurrentUser(BeanUser.currentLoginUser.getUser_id());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}
