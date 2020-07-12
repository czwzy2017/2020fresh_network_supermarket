package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanDeliverAddress;
import model.BeanUser;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class AddressManager {
    public ObservableList<BeanDeliverAddress> loadAddress() {
        ObservableList<BeanDeliverAddress> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from deliver_address";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanDeliverAddress r=new BeanDeliverAddress();
                r.setDelivery_id(rs.getInt(1));
                r.setDelivery_province(rs.getString(3));
                r.setDelivery_city(rs.getString(4));
                r.setDelivery_district(rs.getString(5));
                r.setDelivery_address(rs.getString(6));
                r.setDelivery_contact(rs.getString(7));
                r.setDelivery_tel(rs.getString(8));
                result.add(r);
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
        return result;
    }

    public void add(BeanDeliverAddress r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into deliver_address(user_id, delivery_province, delivery_city, delivery_district, delivery_address, delivery_contact, delivery_tel) value (?,?,?,?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, BeanUser.currentLoginUser.getUser_id());
            pst.setString(2, r.getDelivery_province());
            pst.setString(3, r.getDelivery_city());
            pst.setString(4, r.getDelivery_district());
            pst.setString(5,r.getDelivery_address());
            pst.setString(6, r.getDelivery_contact());
            pst.setString(7,r.getDelivery_tel());
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

    public void delete(BeanDeliverAddress r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from deliver_address where delivery_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getDelivery_id());
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

    public void modify(BeanDeliverAddress r) {
        Connection conn = null;
        if ("".equals(r.getDelivery_province().trim())) throw new BusinessException("省不能为空");
        if ("".equals(r.getDelivery_city().trim())) throw new BusinessException("市不能为空");
        if ("".equals(r.getDelivery_district().trim())) throw new BusinessException("区不能为空");
        if ("".equals(r.getDelivery_address().trim())) throw new BusinessException("地址不能为空");
        if ("".equals(r.getDelivery_contact().trim())) throw new BusinessException("联系人不能为空");
        if ("".equals(r.getDelivery_tel().trim())) throw new BusinessException("手机号不能为空");
        String telRegex = "[1][3578]\\d{9}";
        if (!r.getDelivery_tel().trim().matches(telRegex)) throw new BusinessException("请输入正确的手机号");
        try {
            conn = DBUtil.getConnection();
            String sql = "update deliver_address set delivery_province=?,delivery_city=?,delivery_district=?,delivery_address=?,delivery_contact=?,delivery_tel=? where delivery_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, r.getDelivery_province());
            pst.setString(2, r.getDelivery_city());
            pst.setString(3, r.getDelivery_district());
            pst.setString(4, r.getDelivery_address());
            pst.setString(5, r.getDelivery_contact());
            pst.setString(6, r.getDelivery_tel());
            pst.setInt(7,r.getDelivery_id());
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
