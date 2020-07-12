package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanCoupon;
import model.BeanUser;
import model.UserCoupon;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CouponManager {
    public ObservableList<BeanCoupon> loadCoupon() {
        ObservableList<BeanCoupon> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanCoupon r = new BeanCoupon();
                r.setCoupon_id(rs.getInt(1));
                r.setCoupon_detail(rs.getString(2));
                r.setCoupon_minimum_price(rs.getDouble(3));
                r.setCoupon_credit_price(rs.getDouble(4));
                r.setCoupon_begin_date(rs.getTimestamp(5));
                r.setCoupon_end_date(rs.getTimestamp(6));
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

    public ObservableList<UserCoupon> loadUserCoupon(int id) {
        ObservableList<UserCoupon> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select coupon.coupon_id, coupon.coupon_detail, coupon.coupon_minimum_price, coupon.coupon_credit_price, coupon.coupon_begin_date, coupon.coupon_end_date ,user_id,user_coupon_count from coupon,user_coupon where coupon.coupon_id=user_coupon.coupon_id and user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserCoupon r = new UserCoupon();
                r.setCoupon_id(rs.getInt(1));
                r.setCoupon_detail(rs.getString(2));
                r.setCoupon_minimum_price(rs.getDouble(3));
                r.setCoupon_credit_price(rs.getDouble(4));
                r.setCoupon_begin_date(rs.getTimestamp(5));
                r.setCoupon_end_date(rs.getTimestamp(6));
                r.setUser_id(rs.getInt(7));
                r.setUser_coupon_count(rs.getInt(8));
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

    public void add(BeanCoupon r) {
        if (r.getCoupon_minimum_price()<=0 || r.getCoupon_credit_price()<=0) throw new BusinessException("价格不能小于0");
        if (r.getCoupon_credit_price()>r.getCoupon_minimum_price()) throw new BusinessException("适用金额不能小于减免金额");
        if (r.getCoupon_end_date().before(r.getCoupon_begin_date())) throw new BusinessException("结束日期不能早于开始日期");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into coupon(coupon_detail, coupon_minimum_price, coupon_credit_price, coupon_begin_date, coupon_end_date) VALUE (?,?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, r.getCoupon_detail());
            pst.setDouble(2, r.getCoupon_minimum_price());
            pst.setDouble(3, r.getCoupon_credit_price());
            pst.setTimestamp(4, new Timestamp(r.getCoupon_begin_date().getTime()));
            pst.setTimestamp(5,new Timestamp(r.getCoupon_end_date().getTime()));
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

    public void addUserCoupon(int id) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql ="select * from coupon where coupon_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs=pst.executeQuery();
            if (!rs.next()) throw new BusinessException("优惠券不存在");
            pst.close();
            sql="select *from user_coupon where coupon_id=? and user_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.setInt(2, BeanUser.currentLoginUser.getUser_id());
            rs=pst.executeQuery();
            if (rs.next()){
                sql = "update user_coupon set user_coupon_count=user_coupon_count+1 where coupon_id=? and user_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, id);
                pst.setInt(2, BeanUser.currentLoginUser.getUser_id());
                pst.execute();
                pst.close();
            }else {
                sql = "insert into user_coupon(coupon_id, user_id, user_coupon_count)  VALUE (?,?,1)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, id);
                pst.setInt(2, BeanUser.currentLoginUser.getUser_id());
                pst.execute();
                pst.close();
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
    }

    public void delete(BeanCoupon r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from coupon where coupon_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getCoupon_id());
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
