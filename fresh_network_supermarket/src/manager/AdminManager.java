package manager;

import model.BeanAdmin;
import model.BeanGoodsProcurement;
import model.BeanUserInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminManager {
    public BeanAdmin login(String id, String pwd) throws BaseException {
        BeanAdmin r = new BeanAdmin();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from admin where admin_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (!rs.getString(3).equals(pwd)) throw new BusinessException("密码错误");
            } else throw new BusinessException("员工不存在");
            r.setAdmin_id(id);
            r.setAdmin_name(rs.getString(2));
            r.setAdmin_pwd(pwd);
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

    public void reg(String id, String name) throws BaseException {
        if ("".equals(id)) throw new BusinessException("员工编号不能为空");
        if ("".equals(name)) throw new BusinessException("员工姓名不能为空");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from admin where admin_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("员工编号已存在");
            rs.close();
            pst.close();
            sql = "insert into admin(admin_id, admin_name, admin_pwd) VALUE (?,?,'000000')";
            pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, name);
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
    }

    public void modifyPwd(String id, String pwd, String pwd2) throws BaseException {
        if (!pwd.equals(pwd2)) throw new BusinessException("两次密码不一致");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update admin set admin_pwd=? where admin_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pwd);
            pst.setString(2, id);
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
    }

    public void delete(String id,String name) throws BaseException{
        Connection conn = null;
        if ("0".equals(id)) throw new BusinessException("不允许删除超级管理员");
        try {
            conn = DBUtil.getConnection();
            String sql = "select admin_name from admin where admin_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (!rs.getString(1).equals(name)) throw new BusinessException("员工编号和姓名不对应");
            }
            rs.close();
            pst.close();
            sql = "delete from admin where admin_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, id);
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
    }

}
