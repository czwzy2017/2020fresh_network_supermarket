package manager;

import model.BeanUserInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class UserManager {
    public BeanUserInfo login(String tel, String pwd) throws BaseException {
        BeanUserInfo r = new BeanUserInfo();
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
            r.setUser_id(rs.getString(1));
            r.setUser_name(rs.getString(2));
            r.setUser_sex(rs.getString(3));
            r.setUser_pwd(pwd);
            r.setUser_tel(rs.getString(5));
            r.setUser_email(rs.getString(6));
            r.setUser_city(rs.getString(7));
            r.setUser_creat_time(rs.getTimestamp(8));
            r.setUser_vip(rs.getBoolean(9));
            System.out.println(r.isUser_vip());
            r.setUser_vip_deadline(rs.getTimestamp(10));
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

    public String reg(String name, String sex, String pwd, String pwd2, String tel, String email, String city) throws BaseException {
        if ("".equals(name)) throw new BusinessException("姓名不能为空");
        if (!"男".equals(sex) && !"女".equals(sex)) throw new BusinessException("性别请输入男或女");
        if ("".equals(pwd)) throw new BusinessException("密码不能为空");
        if (!pwd.equals(pwd2)) throw new BusinessException("两次密码不一致");
        if ("".equals(tel)) throw new BusinessException("电话不能为空");
        if (tel.length() != 11) throw new BusinessException("请输入11位手机号");
        if ("".equals(city)) throw new BusinessException("城市不能为空");
        Connection conn = null;
        String id = "";
        try {
            conn = DBUtil.getConnection();
            String sql = "select COUNT(*) from user_infor";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = String.valueOf(rs.getInt(1));
            }
            st.close();
            sql = "select * from user_infor where user_tel=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tel);
            rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("手机号已被注册");
            rs.close();
            pst.close();
            sql = "insert into user_infor(user_id, user_name, user_sex, user_pwd, user_tel, user_email, user_city, user_creat_time, user_vip) values(?,?,?,?,?,?,?,?,0)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, name);
            pst.setString(3, sex);
            pst.setString(4, pwd);
            pst.setString(5, tel);
            pst.setString(6, email);
            pst.setString(7, city);
            pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.execute();
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

}
