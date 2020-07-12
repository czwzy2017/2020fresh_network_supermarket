package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BeanFullDiscount;
import model.DiscountGoods;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DiscountManager {
    public ObservableList<BeanFullDiscount> loadDiscount() {
        ObservableList<BeanFullDiscount> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from full_discount";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanFullDiscount r = new BeanFullDiscount();
                r.setDiscount_id(rs.getInt(1));
                r.setDiscount_detail(rs.getString(2));
                r.setDiscount_goods_count(rs.getInt(3));
                r.setDiscount(rs.getDouble(4));
                r.setDiscount_begin_date(rs.getTimestamp(5));
                r.setDiscount_end_date(rs.getTimestamp(6));
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


    public void addDiscount(BeanFullDiscount r) {
        Connection conn = null;
        if (r.getDiscount()>=1 || r.getDiscount()<=0) throw new BusinessException("折扣必须在0和1之间");
        if (r.getDiscount_goods_count()<=0) throw new BusinessException("适用商品数量必须大于0");
        if (r.getDiscount_end_date().before(r.getDiscount_begin_date())) throw new BusinessException("开始时间不得晚于结束时间");
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into full_discount(discount_detail, discount_goods_count, discount, discount_begin_date, discount_end_date) VALUE (?,?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, r.getDiscount_detail());
            pst.setInt(2, r.getDiscount_goods_count());
            pst.setDouble(3,r.getDiscount());
            pst.setTimestamp(4,new Timestamp(r.getDiscount_begin_date().getTime()));
            pst.setTimestamp(5,new Timestamp(r.getDiscount_end_date().getTime()));
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

    public void deleteDiscount(BeanFullDiscount r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from full_discount where discount_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getDiscount_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            throw new BusinessException("有商品参与该满折，不可删除。请先将参与该满折的产品全部删除后，再删除");
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

    public ObservableList<DiscountGoods> loadGoods(int id) {
        ObservableList<DiscountGoods> result = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select goods.goods_id,goods_name from discount_goods,goods where discount_id=? and goods.goods_id=discount_goods.goods_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DiscountGoods r=new DiscountGoods();
                r.setDiscount_id(id);
                r.setGoods_id(rs.getInt(1));
                r.setGoods_name(rs.getString(2));
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

    public void addGoods(DiscountGoods r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("商品不存在");
            pst.close();
            sql = "select * from discount_goods where goods_id=? and discount_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setInt(2,r.getDiscount_id());
            rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("商品已参加此满折");
            sql="select * from discount_goods where (discount_begin_date<? and discount_end_date>=? or discount_end_date>? and discount_begin_date<=?) and goods_id=?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1,new Timestamp(r.getDiscount_end_date().getTime()));
            pst.setTimestamp(2,new Timestamp(r.getDiscount_end_date().getTime()));
            pst.setTimestamp(3,new Timestamp(r.getDiscount_begin_date().getTime()));
            pst.setTimestamp(4,new Timestamp(r.getDiscount_begin_date().getTime()));
            pst.setInt(5,r.getGoods_id());
            rs =pst.executeQuery();
            if (rs.next()) throw new BusinessException("该商品在该时间段已有满折");
            sql = "insert into discount_goods(discount_id, goods_id, discount_begin_date, discount_end_date)    VALUE (?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getDiscount_id());
            pst.setInt(2, r.getGoods_id());
            pst.setTimestamp(3, new Timestamp(r.getDiscount_begin_date().getTime()));
            pst.setTimestamp(4, new Timestamp(r.getDiscount_end_date().getTime()));
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

    public void deleteGoods(DiscountGoods r) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from discount_goods where goods_id=? and discount_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getGoods_id());
            pst.setInt(2,r.getDiscount_id());
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

    public void deleteAll(BeanFullDiscount r){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from discount_goods where discount_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getDiscount_id());
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
